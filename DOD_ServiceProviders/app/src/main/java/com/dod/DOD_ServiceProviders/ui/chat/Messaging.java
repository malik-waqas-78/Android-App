package com.dod.DOD_ServiceProviders.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dod.DOD_ServiceProviders.R;
import com.dod.DOD_ServiceProviders.notification.APIClient;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Messaging extends AppCompatActivity {
    String cusName, cusNo, proNo, orderNo,proName;
    EditText text;
    DatabaseReference databaseReference;
    Calendar calendar;
    RecyclerView listView;
    MessageAdapter adapter;
    ArrayList<MsgDetails> msgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        Intent intent = getIntent();
        cusName = intent.getStringExtra("cusName");
        cusNo = intent.getStringExtra("cusNo");
        proNo = intent.getStringExtra("proNo");
        proName=intent.getStringExtra("proName");
        orderNo = intent.getStringExtra("orderNo");
        getSupportActionBar().setTitle(cusName);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        calendar = Calendar.getInstance();
        msgs = new ArrayList<>();
        adapter = new MessageAdapter(getApplicationContext(), msgs);
        listView = findViewById(R.id.msg_listview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setStackFromEnd(true);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
        listView.scrollToPosition(msgs.size() - 1);
        getMessages();

    }

    private void getMessages() {
        databaseReference.child("Chat").child(orderNo).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MsgDetails msg = dataSnapshot.getValue(MsgDetails.class);
                if (msg.getMsgBy().equals("cus")) {
                    msg.setViewType(0);
                } else {
                    msg.setViewType(1);
                }
                Log.d("92727", "msg: " + msg.getMsgText());
                Log.d("92727", "msg: " + msg.getMsgTime());
                Log.d("92727", "msg: " + msg.getMsgId());
                Log.d("92727", "msg: " + msg.getMsgBy());
                msgs.add(msg);
                adapter.notifyItemInserted(msgs.size() - 1);
                listView.scrollToPosition(msgs.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void sendMessage(View view) {
        text = findViewById(R.id.msg_input);
        if (text.getText().toString().length() <= 0) {
            return;
        }
        String msg = text.getText().toString();
        int h, m, s;
        h = calendar.get(Calendar.HOUR_OF_DAY);
        m = calendar.get(Calendar.MINUTE);
        s = calendar.get(Calendar.SECOND);
        String patch = h >= 12 ? " PM\n" : " AM\n";
        h = (h >= 12 ? h - 12 : h);
        String hh = (h <= 9 ? "0" + h : "" + h);
        String mm = (m <= 9 ? "0" + m : "" + m);
        String ss = (s <= 9 ? "0" + s : "" + s);
        int y, M, d;
        y = calendar.get(Calendar.YEAR);
        M = calendar.get(Calendar.MONTH);
        d = calendar.get(Calendar.DAY_OF_MONTH);
        String date = "" + y + M + d;
        String Time = hh + ":" + mm + ":" + ss + patch + d + "/" + M + "/" + y;
        String msgid = System.currentTimeMillis() + date;
        MsgDetails msgDetails = new MsgDetails(msgid, "pro", msg, Time);
        databaseReference.child("Chat").child(orderNo).child(msgid).setValue(msgDetails);
        notifyCustomers(proName,proNo,cusName,cusNo,orderNo,msg);
        text.setText("");
    }
    private void notifyCustomers(String proName,String proNo,String cusName,String cusNo,String orderNo,String msg) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Log.d("927277", "notifyCustomers: "+cusNo);
        reference.child("USERS").child(cusNo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("927277", "onDataChange: "+dataSnapshot.getValue());
                if(!dataSnapshot.child("token").exists()&&dataSnapshot.child("token").getValue()==null) {
                    return;
                }

                String token = dataSnapshot.child("token").getValue().toString();
                JsonObject payload = buildNotificationPayload(token,cusNo,cusName,proNo,proName,orderNo,msg);
                APIClient.getApiService().sendNotification(payload).enqueue(
                        new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(Messaging.this, "Notification send successful",
                                            Toast.LENGTH_LONG).show();
                                }
                                Log.d("927277", "onResponse: "+response.body()+response.message());
                                Toast.makeText(Messaging.this, "Notification send successful"+response.body(),
                                        Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                            }
                        });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    private JsonObject buildNotificationPayload(String token,String cusNo,String cusName,String proNo,String  proName
    ,String orderNo,String msg) {
        // compose notification json payload
        JsonObject payload = new JsonObject();
        payload.addProperty("to", token);
        // compose data payload here
        JsonObject data = new JsonObject();
        data.addProperty("title", proName);
        data.addProperty("message", msg);
        data.addProperty("cusName",cusName);
        data.addProperty("cusNo",cusNo);
        data.addProperty("proName",proName);
        data.addProperty("proNo",proNo);
        data.addProperty("orderNo",orderNo);
        data.addProperty("intent","1");
        // add data payload
        payload.add("data", data);
        return payload;
    }
}
