package com.easysolutions.dod.ui.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.easysolutions.dod.MessageAdapter;
import com.easysolutions.dod.Order_Conveyance;
import com.easysolutions.dod.Order_Photocopy;
import com.easysolutions.dod.Order_Print;
import com.easysolutions.dod.Orders;
import com.easysolutions.dod.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PendingOrdersFragement extends Fragment {

    ArrayList<Object> porders = new ArrayList<>();
    ArrayList<String> pType = new ArrayList<>();
    RecyclerView listView;
    Pending_Adapter adapter;
    ProgressBar pbar;
    DatabaseReference databaseReference;
    public  static  String TAG="92727";
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pendingorders, container, false);
        listView = root.findViewById(R.id.pending_listview);
        pbar = root.findViewById(R.id.pending_progressbar);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        adapter = new Pending_Adapter(porders, pType, container.getContext());
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
        databaseReference.child("ORDERS").child("pending").orderByChild("cusNo").equalTo(Orders.PHONENUMBE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                porders.clear();
                pType.clear();
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() != 0 && dataSnapshot != null) {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                       // Log.d(TAG, "found "+snapshot.getValue());
                        String type;
                        type = snapshot.child("type").getValue().toString();
                        if (type.equals("conveyance")) {
                            Order_Conveyance conveyance_order = snapshot.getValue(Order_Conveyance.class);
                            porders.add(conveyance_order);
                            pType.add("conveyance");
                        } else if (type.equals("photocopy")) {
                            Order_Photocopy photocopy_order = snapshot.getValue(Order_Photocopy.class);
                            porders.add(photocopy_order);
                            pType.add("photocopy");
                        } else if (type.equals("print")) {
                            Order_Print print_order = snapshot.getValue(Order_Print.class);
                            porders.add(print_order);
                            pType.add("print");
                        }
                        //Log.d(TAG, "found ");
                    }
                    adapter.notifyDataSetChanged();

                } else {
                    //Log.d(TAG, "onChildAdded: ");
                    porders.add("0");
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