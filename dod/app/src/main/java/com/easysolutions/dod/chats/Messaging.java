package com.easysolutions.dod.chats;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.easysolutions.dod.R;
import com.easysolutions.dod.notification.APIClient;
import com.easysolutions.dod.ui.main.Orders;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
    String proName,cusNo,proNo,orderNo,cusName;
    EditText text;
    DatabaseReference databaseReference;
    Calendar calendar;
    RecyclerView listView;
    MessageAdapter adapter;
    ArrayList<MsgDetails> mesgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        Intent intent=getIntent();
        cusName=intent.getStringExtra("cusName");
        proName=intent.getStringExtra("proName");
        cusNo=intent.getStringExtra("cusNo");
        proNo=intent.getStringExtra("proNo");
        orderNo=intent.getStringExtra("orderNo");
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(proName);

        databaseReference= FirebaseDatabase.getInstance().getReference();
        calendar=Calendar.getInstance();
        listView=findViewById(R.id.msg_listview);
        mesgs=new ArrayList<>();

        adapter=new MessageAdapter(this,mesgs);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setStackFromEnd(true);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
        listView.scrollToPosition(mesgs.size()-1);
        getMessages();
    }

    private void getMessages() {
        databaseReference.child("Chat").child(orderNo).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.getValue()==null){
                    return;
                }
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
                mesgs.add(msg);
                adapter.notifyItemInserted(mesgs.size() - 1);
                listView.scrollToPosition(mesgs.size() - 1);
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
        text=findViewById(R.id.msg_input);
        if(text.getText().toString().length()<=0){
            return;
        }
        String msg=text.getText().toString();
        int h,m,s;
        h=calendar.get(Calendar.HOUR_OF_DAY);
        m=calendar.get(Calendar.MINUTE);
        s=calendar.get(Calendar.SECOND);
        String patch = h>=12?" PM\n":" AM\n";
        h=(h>=12?h-12:h);
        String hh=(h<=9?"0"+h:""+h);
        String mm=(m<=9?"0"+m:""+m);
        String ss=(s<=9?"0"+s:""+s);
        int y,M,d;
        y=calendar.get(Calendar.YEAR);
        M=calendar.get(Calendar.MONTH);
        d=calendar.get(Calendar.DAY_OF_MONTH);
        String date=""+y+M+d;
        String Time=hh+":"+mm+":"+ss+patch+d+"/"+M+"/"+y;
        String msgid=System.currentTimeMillis()+date;
        MsgDetails msgDetails=new MsgDetails(msgid,"cus",msg,Time);
        Log.d("9272277", "sendMessage: "+orderNo);
        databaseReference.child("Chat").child(orderNo).child(msgid).setValue(msgDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    notifyProviders(msg);
                    Log.d("927277", "OnSuccess: ");
                }else{
                    Toast.makeText(Messaging.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    Log.d("927277", "onComplete: "+task.getException());
                }
            }
        });
        text.setText("");
        listView.scrollToPosition(mesgs.size()-1);
    }

    private void notifyProviders(String msg) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Log.d("927277", "notifyProviders: "+proNo);
        reference.child("PROVIDERS").child(proNo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(!dataSnapshot.child("token").exists()&&dataSnapshot.child("token").getValue()==null){
                        Log.d("927277", "tken is null: ");
                        return;
                    }
                    String token = dataSnapshot.child("token").getValue().toString();
                    String proID=dataSnapshot.getKey();
                    String name=dataSnapshot.child("name").getValue().toString();
                    JsonObject payload = buildNotificationPayload(token,proID,name,msg);
                    APIClient.getApiService().sendNotification(payload).enqueue(
                            new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(Messaging.this, "Notification send successful",
                                                Toast.LENGTH_LONG).show();
                                        Log.d("927277", "Notification sent: "+response.toString());
                                    }else{
                                        Log.d("927277", "Error responce: "+response.toString());
                                    }
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Log.d("927277", "Error failed: ");
                                }
                            });
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    private JsonObject buildNotificationPayload(String token,String proID,String proname,String msg) {
        // compose notification json payload
        JsonObject payload = new JsonObject();
        payload.addProperty("to", token);
        // compose data payload here
        JsonObject data = new JsonObject();
        data.addProperty("title", cusName);
        data.addProperty("subtext","New Message");
        data.addProperty("message", msg);
        data.addProperty("proname",proname);
        data.addProperty("proNo",proID);
        data.addProperty("cusName",cusName);
        data.addProperty("cusNo",cusNo);
        data.addProperty("orderNo",orderNo);
        data.addProperty("intent","1");
        data.addProperty("icon",R.drawable.icon);
        // add data payload
        payload.add("data", data);
        return payload;
    }
}
