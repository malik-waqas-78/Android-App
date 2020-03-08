package com.test.admindod.orders;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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

public class orders_canceled extends Fragment {

    ArrayList<Object> orders;
    static DatabaseReference databaseReference;
    static ArrayList<String> types;
    ProgressBar progressBar;
    MyOrdersListViewAdapter myListViewAdapter;
    RecyclerView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.orders_canceled, container, false);
        listView = view.findViewById(R.id.canceled_listview);
        progressBar=view.findViewById(R.id.pBar_canceled);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        orders=new ArrayList<>();
        types=new ArrayList<>();
        myListViewAdapter=new MyOrdersListViewAdapter(container.getContext(),orders,R.layout.orders,types);
        LinearLayoutManager layoutManager=new LinearLayoutManager(container.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(myListViewAdapter);
        getOrders();
        return view;
    }
    public void getOrders(){

        databaseReference.child("ORDERS").child("canceled").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String type ;
                Order_Conveyance conveyance_order;
                Order_Photocopy photocopy_order ;
                Order_Print print_order ;
                types.clear();
                orders.clear();
                //System.out.println("92727 "+dataSnapshot.getChildrenCount());
                if(dataSnapshot.getChildrenCount()==0){
                    types.add("empty");
                    orders.add("empty");
                    progressBar.setVisibility(View.GONE);
                    myListViewAdapter.notifyDataSetChanged();
                    return;
                }

                for (DataSnapshot obj:dataSnapshot.getChildren()) {
                    Log.e("92727",obj.getKey());
                    type =obj.child("type").getValue().toString();
                    if(type.equals("conveyance")){
                        conveyance_order =obj.getValue(Order_Conveyance.class);
                        //Log.e("92727",""+obj.child(""))
                        orders.add(conveyance_order);
                        types.add(type);
                    }else if(type.equals("photocopy")){
                        photocopy_order =obj.getValue(Order_Photocopy.class);

                        orders.add(photocopy_order);
                        types.add(type);
                    }else if(type.equals("print")){
                        print_order =obj.getValue(Order_Print.class);
                        orders.add(print_order);
                        types.add(type);
                    }
                }
                progressBar.setVisibility(View.GONE);
                myListViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
