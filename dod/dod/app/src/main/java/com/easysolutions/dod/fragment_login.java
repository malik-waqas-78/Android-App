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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class fragment_login extends Fragment {
    ProgressBar progressBar;
    String phoneNumber, password, fdbPassword,name;
    EditText etphno, etpass;
    private DatabaseReference mDatabase;
    TextView txt_signup;
    Button login;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        context=container.getContext();
        etpass = view.findViewById(R.id.edtxt_password);
        etphno = view.findViewById(R.id.edtxt_mobileNumber);
        progressBar = view.findViewById(R.id.loading);
        txt_signup = view.findViewById(R.id.txt_signup);
        login=view.findViewById(R.id.btn_login);
        FloatingActionButton back=view.findViewById(R.id.btn_back_login);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new frag_main()).commit();
            }
        });
        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new fragment_create_account()).commit();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cnfmlogin();
            }
        });
        return view;
    }

    private boolean applyValidations(String password, String phoneNumber) {
        if (password.length() != 6 || (phoneNumber.length() != 11)) {
            return false;
        } else if (validate(phoneNumber)) {
            return true;
        }
        return false;
    }

    private boolean validate(String phoneNumber) {
        Pattern p = Pattern.compile("(0||92)?(30[0-9]||31[1-5]||32[0-5]||33[0-7]||34[0-7])[0-9]{7}");
        Matcher m = p.matcher(phoneNumber);
        boolean name = m.matches();
        return name;
    }

    public void cnfmlogin() {
        login.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        password = etpass.getText().toString();
        phoneNumber = etphno.getText().toString();
        if (password.isEmpty() || phoneNumber.isEmpty()) {
            showPopup("Admin", "Plz fill the required fields first.");
            progressBar.setVisibility(View.INVISIBLE);
            login.setEnabled(true);
        } else {
            if (applyValidations(password, phoneNumber)) {
                mDatabase = FirebaseDatabase.getInstance().getReference();
                DatabaseReference ref = mDatabase.child("USERS").child(phoneNumber);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            fdbPassword = dataSnapshot.child("password").getValue().toString();
                            name=dataSnapshot.child("name").getValue().toString();
                            if (fdbPassword.equals(password)) {
                                login.setEnabled(true);
                                Intent i = new Intent(context, AvailableServices.class);
                                i.putExtra("phoneNumber", phoneNumber);
                                i.putExtra("Name",name);
                                startActivity(i);
                                progressBar.setVisibility(View.INVISIBLE);
                            } else {
                                progressBar.setVisibility(View.INVISIBLE);
                                login.setEnabled(true);
                                showPopup("Try Again", "Password is incorrect.");
                            }
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            login.setEnabled(true);
                            showPopup("Admin", "You are not Registered as a provider.");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            } else {
                login.setEnabled(true);
                progressBar.setVisibility(View.INVISIBLE);
                showPopup("Admin", "Plz enter valid Phone Number and password.");
            }
        }
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
