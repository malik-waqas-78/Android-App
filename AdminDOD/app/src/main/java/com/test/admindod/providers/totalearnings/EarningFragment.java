package com.test.admindod.providers.totalearnings;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.admindod.R;
import com.test.admindod.providers.row_Providers;

import java.util.ArrayList;


public class EarningFragment extends AppCompatActivity {
    TextView dodcharges;
    row_Providers row=new row_Providers();
    ArrayList<String> ordernos=new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_share);

        Intent intent=getIntent();
        row.setName(intent.getStringExtra("proname"));
        row.setNo(intent.getStringExtra("phoneNumber"));
        row.setPassword(intent.getStringExtra("password"));

        getSupportActionBar().setTitle(row.getName());

        final ProgressBar progressBar=findViewById(R.id.pbar);
        progressBar.setVisibility(View.VISIBLE);
        final TextView totalorders = findViewById(R.id.nooforders);
        final TextView balance=findViewById(R.id.balance);
        final TextView earnings = findViewById(R.id.earnings);
        dodcharges=findViewById(R.id.charges);
        final TextView expences=findViewById(R.id.expences);
        final Button shwpassword=findViewById(R.id.viewPassword);
        Button clearcharges=findViewById(R.id.clearcharges);
        balance.setText("0");
        earnings.setText("0");
        dodcharges.setText("0");
        expences.setText("0");
        shwpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=row.getName()+"'s account with mobile no \n "+row.getNo()+"\n password is "+row.getPassword()+".";
                showPopup("Provider Passwrod",str);
            }
        });
        clearcharges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup2("Confirmation","If you have received the DoD charges from "+row.getName()+", only than " +
                        "you should clear these charges.");
            }
        });
        totalorders.setText(String.valueOf(0));
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("BILL").orderByChild("proNo").equalTo(row.getNo()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                float vbalance=0,vearnings=0,vdodcharges=0,vexpencs=0;
                long vtotalorders=0;
                if(dataSnapshot.exists()){
                    vtotalorders=dataSnapshot.getChildrenCount();
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        ordernos.add(snapshot.getKey());
                        String type=snapshot.child("type").getValue().toString();
                        if(type.equals("cony")){
                            Bill_Conveyance bill_conveyance=snapshot.getValue(Bill_Conveyance.class);
                            vbalance+=Float.valueOf(bill_conveyance.getFair());
                            if(snapshot.child("payed").exists()&&snapshot.child("payed").getValue().equals("true")) {
                                vdodcharges+=0;
                            }else{
                                vdodcharges+=0;
                            }
                            vearnings+=0;
                            vexpencs+=0;
                        }else if(type.equals("print")){
                            Bill_Printing bill_printing=snapshot.getValue(Bill_Printing.class);
                            if(snapshot.child("payed").exists()&&snapshot.child("payed").getValue().equals("true")) {
                                vdodcharges+=0;
                            }else{
                                vdodcharges+=Float.valueOf(bill_printing.getDodcharges());
                            }
                            vbalance+=Float.valueOf(bill_printing.getTotalbill());
                            vearnings+=Float.valueOf(bill_printing.getProcharges());

                            vexpencs+=Float.valueOf(bill_printing.ExpendeturesEarnings());
                        }else if(type.equals("copy")){
                            Bill_Copying bill_copying=snapshot.getValue(Bill_Copying.class);
                            if(snapshot.child("payed").exists()&&snapshot.child("payed").getValue().equals("true")) {
                                vdodcharges+=0;
                            }else{
                                vdodcharges+=Float.valueOf(bill_copying.getDodcharges());
                            }
                            vbalance+=Float.valueOf(bill_copying.getTotalbill());
                            vearnings+=Float.valueOf(bill_copying.getProcharges());

                            vexpencs+=Float.valueOf(bill_copying.ExpendeturesEarnings());
                        }

                    }
                }
                //here show bills

                balance.setText(String.valueOf(vbalance)+" Rs");
                earnings.setText(String.valueOf(vearnings)+" Rs");
                dodcharges.setText(String.valueOf(vdodcharges)+" Rs");
                expences.setText(String.valueOf(vexpencs)+" Rs");
                totalorders.setText((String.valueOf(vtotalorders)));
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void showPopup(String otp_error, String s) {
        AlertDialog.Builder alert = new AlertDialog.Builder(EarningFragment.this)
                .setTitle(otp_error)
                .setMessage(s)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.ok, null)
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setIcon(android.R.drawable.ic_dialog_alert);
        alert.show();
    }

    private void showPopup2(String otp_error, String s) {
        AlertDialog.Builder alert = new AlertDialog.Builder(EarningFragment.this)
                .setTitle(otp_error)
                .setMessage(s)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Received Charges?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
                        for(String order:ordernos){
                            databaseReference.child("BILL").child(order).child("payed").setValue("true");
                        }
                        dodcharges.setText("0");
                    }
                }).setNegativeButton("Cancel",null)
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setIcon(android.R.drawable.ic_dialog_alert);
        alert.show();
    }

}