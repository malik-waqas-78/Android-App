package com.dod.DOD_ServiceProviders.ui.totalearnings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.dod.DOD_ServiceProviders.Dashboard;
import com.dod.DOD_ServiceProviders.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EarningFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        final ProgressBar progressBar=root.findViewById(R.id.pbar);
        progressBar.setVisibility(View.VISIBLE);
        final TextView totalorders = root.findViewById(R.id.nooforders);
        final TextView balance=root.findViewById(R.id.balance);
        final TextView earnings = root.findViewById(R.id.earnings);
        final TextView dodcharges=root.findViewById(R.id.charges);
        final TextView expences=root.findViewById(R.id.expences);
        balance.setText("0");
        earnings.setText("0");
        dodcharges.setText("0");
        expences.setText("0");
        totalorders.setText(String.valueOf(0));
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("BILL").orderByChild("proNo").equalTo(Dashboard.phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                float vbalance=0,vearnings=0,vdodcharges=0,vexpencs=0;
                long vtotalorders=0;
                if(dataSnapshot.exists()){
                    vtotalorders=dataSnapshot.getChildrenCount();
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        String type=snapshot.child("type").getValue().toString();
                        if(type.equals("cony")){
                            Bill_Conveyance bill_conveyance=snapshot.getValue(Bill_Conveyance.class);
                            vbalance+=Float.valueOf(bill_conveyance.getFair());
                            vdodcharges+=0;
                            vearnings+=0;
                            vexpencs+=0;
                        }else if(type.equals("print")){
                            Bill_Printing bill_printing=snapshot.getValue(Bill_Printing.class);
                            vbalance+=Float.valueOf(bill_printing.getTotalbill());
                            vearnings+=Float.valueOf(bill_printing.getProcharges());
                            vdodcharges+=Float.valueOf(bill_printing.getDodcharges());
                            vexpencs+=Float.valueOf(bill_printing.ExpendeturesEarnings());
                        }else if(type.equals("copy")){
                            Bill_Copying bill_copying=snapshot.getValue(Bill_Copying.class);
                            vbalance+=Float.valueOf(bill_copying.getTotalbill());
                            vearnings+=Float.valueOf(bill_copying.getProcharges());
                            vdodcharges+=Float.valueOf(bill_copying.getDodcharges());
                            vexpencs+=Float.valueOf(bill_copying.ExpendeturesEarnings());
                        }

                    }
                }
                //here show bills

                balance.setText(String.valueOf(vbalance));
                earnings.setText(String.valueOf(vearnings)+" Rs");
                dodcharges.setText(String.valueOf(vdodcharges)+" Rs");
                expences.setText(String.valueOf(vexpencs)+" Rs");
                totalorders.setText((String.valueOf(vtotalorders)+" Rs"));
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return root;
    }
}