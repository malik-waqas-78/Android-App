package com.dod.DOD_ServiceProviders.ui.allorders;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dod.DOD_ServiceProviders.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class orders_pending extends Fragment {
    static DatabaseReference databaseReference;
    ArrayList<String> types;
    ArrayList<Object> orders;
    ProgressBar progressBar;
    Adapter_Pending myListViewAdapter;
    RecyclerView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orders_panding, container, false);
        listView = view.findViewById(R.id.panding_listview);
        progressBar = view.findViewById(R.id.pBar_panding);
        //progressBar.setVisibility(View.VISIBLE);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        orders = new ArrayList<>();
        types = new ArrayList<>();
        myListViewAdapter = new Adapter_Pending(container.getContext(), orders, types);
        LinearLayoutManager layoutManager=new LinearLayoutManager(container.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(myListViewAdapter);
       // Toast.makeText(getActivity().getApplicationContext(),"on Create",Toast.LENGTH_SHORT).show();
        return view;
    }

    @Override
    public void onResume() {
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.INVISIBLE);
        getOrders();
       //Toast.makeText(getActivity().getApplicationContext(),"on Resume",Toast.LENGTH_SHORT).show();
        super.onResume();
    }


    public void getOrders() {
        //Toast.makeText(getActivity().getApplicationContext(),"get orders()",Toast.LENGTH_SHORT).show();
        databaseReference.child("ORDERS").child("pending").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String type;
                Order_Conveyance conveyance_order;
                Order_Photocopy photocopy_order;
                Order_Print print_order;
                types.clear();
                orders.clear();
                //System.out.println("92727 "+dataSnapshot.getChildrenCount());
                if ((!dataSnapshot.exists())&&dataSnapshot!=null&&dataSnapshot.getChildrenCount() == 0) {
                    types.add("no order");
                    orders.add("0");
                    progressBar.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    myListViewAdapter.notifyDataSetChanged();
                    return;
                }

                for (final DataSnapshot obj : dataSnapshot.getChildren()) {
                    Log.e("92727", obj.getKey());
                    type = obj.child("type").getValue().toString();
                    if (type.equals("conveyance")) {
                        conveyance_order = obj.getValue(Order_Conveyance.class);
                        //Log.e("92727",""+obj.child(""))
                        orders.add(conveyance_order);
                        types.add(type);
                    } else if (type.equals("photocopy")) {
                        photocopy_order = obj.getValue(Order_Photocopy.class);
                        orders.add(photocopy_order);
                        types.add(type);
                    } else if (type.equals("print")) {
                        print_order = obj.getValue(Order_Print.class);
                        orders.add(print_order);
                        types.add(type);
                    }
                }
                progressBar.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                myListViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
