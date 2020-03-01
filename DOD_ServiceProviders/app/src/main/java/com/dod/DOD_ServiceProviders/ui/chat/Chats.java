package com.dod.DOD_ServiceProviders.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.dod.DOD_ServiceProviders.Dashboard;
import com.dod.DOD_ServiceProviders.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Chats extends Fragment {

    ArrayList<List_Row> listRows;
    chatAdatpter adapter;
    ListView listView;
    LinearLayout emptychats;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);
        listRows = new ArrayList<>();
        listView = view.findViewById(R.id.chat_list_view);
        emptychats=view.findViewById(R.id.layout_empty);
        adapter = new chatAdatpter(getContext(), listRows, R.layout.chat_threed);
        listView.setAdapter(adapter);
        getchatThreads();
        return view;
    }

    private void getchatThreads() {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ORDERS");
        databaseReference.child("List").orderByChild("ProNo").equalTo(Dashboard.phoneNumber)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listRows.clear();
                        listView.setVisibility(View.GONE);
                        emptychats.setVisibility(View.VISIBLE);
                        if (dataSnapshot.getChildrenCount() == 0) {
                            //Toast.makeText(getContext(), "no child", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                List_Row row = snapshot.getValue(List_Row.class);
                                if (row.getStatus().equals("accepted")) {
                                    row.setId(snapshot.getKey());
                                    listRows.add(row);
                                } else {
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