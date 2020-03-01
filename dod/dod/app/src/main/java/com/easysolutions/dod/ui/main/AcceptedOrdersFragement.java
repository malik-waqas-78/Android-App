package com.easysolutions.dod.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.easysolutions.dod.Order_Conveyance;
import com.easysolutions.dod.Order_Photocopy;
import com.easysolutions.dod.Order_Print;
import com.easysolutions.dod.Orders;
import com.easysolutions.dod.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AcceptedOrdersFragement extends Fragment {
    ArrayList<Object> aorders = new ArrayList<>();
    ArrayList<String> aType = new ArrayList<>();
    RecyclerView listView;
    Accepted_Adapter adapter;
    ProgressBar pbar;
    DatabaseReference databaseReference;
    public  static  String TAG="92727";
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_acceptedorders, container, false);
        listView = root.findViewById(R.id.accp_listview);
        pbar = root.findViewById(R.id.accp_progressbar);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        adapter = new Accepted_Adapter(aorders, aType, container.getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
        getOrders();
        return root;
    }

    private void getOrders() {
        pbar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.INVISIBLE);
        getPendingOrders();

    }

    private void getPendingOrders() {
        //Log.d(TAG, "getPendingOrders: "+Orders.PHONENUMBE);
        databaseReference.child("ORDERS").child("accepted").orderByChild("cusNo").equalTo(Orders.PHONENUMBE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                aorders.clear();
                aType.clear();
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() != 0 && dataSnapshot != null) {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                       // /Log.d(TAG, "found "+snapshot.getValue());
                        String type;
                        type = snapshot.child("type").getValue().toString();
                        if (type.equals("conveyance")) {
                            Order_Conveyance conveyance_order = snapshot.getValue(Order_Conveyance.class);
                            aorders.add(conveyance_order);
                            aType.add("conveyance");
                        } else if (type.equals("photocopy")) {
                            Order_Photocopy photocopy_order = snapshot.getValue(Order_Photocopy.class);
                            aorders.add(photocopy_order);
                            aType.add("photocopy");
                        } else if (type.equals("print")) {
                            Order_Print print_order = snapshot.getValue(Order_Print.class);
                            aorders.add(print_order);
                            aType.add("print");
                        }
                       // Log.d(TAG, "found ");
                    }
                    adapter.notifyDataSetChanged();

                } else {
                   // Log.d(TAG, "onChildAdded: ");
                    aorders.add("0");
                    adapter.notifyDataSetChanged();
                }
                pbar.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
