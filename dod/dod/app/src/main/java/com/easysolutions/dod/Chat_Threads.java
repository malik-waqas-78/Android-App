package com.easysolutions.dod;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Chat_Threads extends AppCompatActivity {
    ArrayList<List_Row> listRows;
    chatAdatpter adapter;
    String phoneNumber;
    ListView listView;
    LinearLayout emptychats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat__threads);
        Intent intent=getIntent();
        phoneNumber=intent.getStringExtra("phoneNumber");
        listRows=new ArrayList<>();
        listView=findViewById(R.id.chat_list_view);
        emptychats=findViewById(R.id.layout_empty);
        adapter=new chatAdatpter(getApplicationContext(),listRows,R.layout.chat_threed);
        listView.setAdapter(adapter);
        getchatThreads();
    }
    private void getchatThreads() {
        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("ORDERS");
        databaseReference.child("List").orderByChild("CusNo").equalTo(phoneNumber)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listRows.clear();
                        listView.setVisibility(View.GONE);
                        emptychats.setVisibility(View.VISIBLE);
                        if(dataSnapshot.getChildrenCount()==0){
                            //Toast.makeText(getContext(), "no child", Toast.LENGTH_SHORT).show();
                            return;
                        }else{
                            for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                                List_Row row = snapshot.getValue(List_Row.class);
                                if(row.getStatus().equals("accepted")){
                                    row.setId(snapshot.getKey());
                                    listRows.add(row);
                                }
                                else{
                                    continue;
                                }

                            }
                            //Toast.makeText(getContext(), "count : "+dataSnapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();
                        }
                        if(listRows.size()==0){
                            listView.setVisibility(View.GONE);
                            emptychats.setVisibility(View.VISIBLE);
                        }else{
                            listView.setVisibility(View.VISIBLE);
                            emptychats.setVisibility(View.GONE);
                        }
                        //Toast.makeText(getContext(), "ended", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
