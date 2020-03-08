package com.test.admindod.providers;

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

public class Available_Providers extends Fragment {
    RecyclerView rcview;
    ProgressBar pbar;
    Adapter_Provider adapter;
    ArrayList<row_Providers> rows=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.provider_details, container, false);
        rcview=view.findViewById(R.id.providers_list_view);
        pbar=view.findViewById(R.id.providers_progress_bar);
        LinearLayoutManager layoutManager=new LinearLayoutManager(container.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcview.setLayoutManager(layoutManager);
        adapter=new Adapter_Provider(rows,container.getContext());
        rcview.setAdapter(adapter);
        getServiceProviders();
        return view;
    }

    private void getServiceProviders() {
        pbar.setVisibility(View.VISIBLE);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("PROVIDERS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rows.clear();
                if(dataSnapshot.exists()&&dataSnapshot.getChildrenCount()!=0&&dataSnapshot!=null){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        row_Providers row=snapshot.getValue(row_Providers.class);
                        row.setNo(snapshot.getKey());
                        row.setViewType(1);
                        rows.add(row);
                    }
                }
                if(rows.size()==0){
                    row_Providers row=new row_Providers();
                    row.setViewType(0);
                    rows.add(row);
                }
                pbar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                pbar.setVisibility(View.GONE);
            }
        });
    }
}
