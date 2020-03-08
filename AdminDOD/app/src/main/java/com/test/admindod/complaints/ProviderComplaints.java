package com.test.admindod.complaints;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.admindod.R;

import java.util.ArrayList;

public class ProviderComplaints extends Fragment {
    RecyclerView listview;
    Adapter_Complaints adapter;
    ProgressBar pbar;
    ArrayList<ComplaiintsViewModal> complaints=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.complaint_provider,container,false);
        listview=view.findViewById(R.id.rcview);
        pbar=view.findViewById(R.id.pbar);
        pbar.setVisibility(View.GONE);
        LinearLayoutManager layoutManager=new LinearLayoutManager(container.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(layoutManager);
        adapter=new Adapter_Complaints(container.getContext(),complaints);
        listview.setAdapter(adapter);
        getComplaints();
        return view;
    }

    private void getComplaints() {
        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("COMPLAINTS").child("complaint").orderByChild("complaintBy").
                equalTo("pro").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pbar.setVisibility(View.VISIBLE);
                complaints.clear();
                if(dataSnapshot.exists()&&databaseReference!=null&&dataSnapshot.getChildrenCount()!=0){

                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        ComplaiintsViewModal complaint=snapshot.getValue(ComplaiintsViewModal.class);
                        complaint.setViewType("1");
                        complaints.add(complaint);
                    }
                }
                if(complaints.size()==0){
                    ComplaiintsViewModal complaint=new ComplaiintsViewModal();
                    complaint.setViewType("0");
                    complaints.add(complaint);
                }
                pbar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
