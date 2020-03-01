package com.easysolutions.dod;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fragment_create_account extends Fragment {
    ProgressBar progressBar;
    String phoneNumber, password, fdbPassword,name;
    EditText etphno, etpass, etname;
    private DatabaseReference mDatabase;
    TextView txt_signup;
    Button signup;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_creat_account, container, false);
        context=container.getContext();
        etpass = view.findViewById(R.id.edtxt_password);
        etphno = view.findViewById(R.id.edtxt_mobileNumber);
        progressBar = view.findViewById(R.id.loading);
        etname=view.findViewById(R.id.edtxt_name);
        txt_signup = view.findViewById(R.id.txt_signup);
        signup = view.findViewById(R.id.btn_signup);
        FloatingActionButton back = view.findViewById(R.id.btn_back_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new frag_main()).commit();
            }
        });
        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_login()).commit();
            }
        });
        return view;
    }

    public void createAccount() {

        password = etpass.getText().toString();
        phoneNumber = etphno.getText().toString();
        name=etname.getText().toString();
        if (password.length()!=6 || phoneNumber.length()!=11 || name.length()<3) {
            showPopup("Admin", "Plz fill the required fields first.");
        } else {
            NotRegistered(password, phoneNumber,name);
        }
    }

    private void NotRegistered(final String password, final String phoneNumber, final String name) {
        progressBar.setVisibility(View.VISIBLE);
        signup.setEnabled(false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("USERS").child(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getChildrenCount() == 1) {
                    progressBar.setVisibility(View.INVISIBLE);
                    signup.setEnabled(true);
                    showPopup("Admin", "You are already registered.");
                } else {
                    signup.setEnabled(true);
                    Intent intent = new Intent(context, ConfirmOTP.class);
                    intent.putExtra("Name",name);
                    intent.putExtra("PhoneNumber", phoneNumber);
                    intent.putExtra("password", password);
                    startActivity(intent);
                    progressBar.setVisibility(View.INVISIBLE);
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
                .setPositiveButton(android.R.string.yes, null)
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert);
        alert.show();
    }
}

