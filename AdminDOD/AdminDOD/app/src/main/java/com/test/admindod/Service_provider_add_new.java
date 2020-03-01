package com.test.admindod;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Service_provider_add_new extends Fragment {

    ProgressBar progressBar;
    String phoneNumber, password, name;
    EditText etphno, etpass, etname;
    private DatabaseReference mDatabase;
    Button signup;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.provide_add_new, container, false);
        context = container.getContext();
        etpass = view.findViewById(R.id.edtxt_password);
        etphno = view.findViewById(R.id.edtxt_mobileNumber);
        progressBar = view.findViewById(R.id.loading);
        etname = view.findViewById(R.id.edtxt_name);
        signup = view.findViewById(R.id.add_provider_btn_regiser);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
        return view;
    }

    public void createAccount() {

        password = etpass.getText().toString();
        phoneNumber = etphno.getText().toString();
        name = etname.getText().toString();
        if (password.length() != 6 || phoneNumber.length() != 11 || name.length() < 3) {
            showPopup("Admin", "Plz fill the required fields first.");
        } else {
            NotRegistered(password, phoneNumber, name);
        }
    }

    private void NotRegistered(final String password, final String phoneNumber, final String name) {
        progressBar.setVisibility(View.VISIBLE);
        signup.setEnabled(false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("PROVIDERS").child(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getChildrenCount() == 1) {
                    progressBar.setVisibility(View.INVISIBLE);
                    signup.setEnabled(true);
                    showPopup("Admin", "You are already registered.");
                } else {

                    mDatabase.child("PROVIDERS").child(phoneNumber).child("password").setValue(password);
                    mDatabase.child("PROVIDERS").child(phoneNumber).child("name").setValue(name)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    showPopup("Registration Notification","Provider has been registered successfully.");
                                    signup.setEnabled(true);
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void showPopup(String otp_error, String s) {
        progressBar.setVisibility(View.INVISIBLE);
        AlertDialog.Builder alert = new AlertDialog.Builder(context)
                .setTitle(otp_error)
                .setMessage(s)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Ok", null)
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setIcon(android.R.drawable.ic_dialog_alert);
        alert.show();
    }
}
