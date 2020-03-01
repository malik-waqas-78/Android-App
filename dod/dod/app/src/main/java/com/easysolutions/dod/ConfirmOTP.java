package com.easysolutions.dod;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConfirmOTP extends AppCompatActivity {

    public DatabaseReference mDatabase;
    public String phoneNumber,password,name;
    private  String OTP;
    EditText otp;
    ProgressBar progressBar;
    TextView statusOTP;
    Button confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_otp);
        otp=findViewById(R.id.edtxtOTP);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Intent i=getIntent();
        phoneNumber=i.getStringExtra("PhoneNumber");
       password=i.getStringExtra("password");
       name=i.getStringExtra("Name");
       NotRegistered(phoneNumber);
            progressBar=findViewById(R.id.progressBar);
            statusOTP=findViewById(R.id.otpStatus);
            confirm=findViewById(R.id.btnOTP);
    }
    private void NotRegistered(final String phoneNumber) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("OTP").child("Status").child(phoneNumber).child("status").setValue("plz_sent")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        getTheOTP();
                    }
        });
    }
    private void getTheOTP() {

        DatabaseReference dref = mDatabase.child("OTP").child(phoneNumber);
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    OTP=dataSnapshot.child("otp").getValue().toString();
                    if(OTP!=null){
                        progressBar.setVisibility(View.GONE);
                        statusOTP.setText("OTP has been sent.");
                        otp.setVisibility(View.VISIBLE);
                        confirm.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showPopup(String otp_error, String s) {
        AlertDialog alert=new AlertDialog.Builder(this)
                .setTitle(otp_error)
                .setMessage(s)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, null)
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void cnfrmOTP(View view) {
        if(!otp.getText().toString().isEmpty()&&OTP!=null){
            if(OTP.equalsIgnoreCase(otp.getText().toString())){
                //Login to main page
                mDatabase=FirebaseDatabase.getInstance().getReference();
                mDatabase.child("USERS").child(phoneNumber).child("password").setValue(password);
                name.toUpperCase();
                mDatabase.child("USERS").child(phoneNumber).child("name").setValue(name);
                //showPopup("Admin","ok"+phoneNumber+password);
                mDatabase.child("OTP").child(phoneNumber).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent i=new Intent(ConfirmOTP.this,AvailableServices.class);
                        i.putExtra("phoneNumber",phoneNumber);
                        i.putExtra("Name",name);
                        startActivity(i);
                        finish();
                    }
                });


            }else{
                showPopup("Admin","Wrong OTP.");
            }
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                }).create().show();
    }
}
