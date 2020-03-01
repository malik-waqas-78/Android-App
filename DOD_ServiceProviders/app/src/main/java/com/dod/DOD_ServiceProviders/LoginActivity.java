package com.dod.DOD_ServiceProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    ProgressBar progressBar;
    String password, fdbPassword, phoneNumber, name;
    Button login;
    EditText etpass, etphno;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = findViewById(R.id.loading);
        login = findViewById(R.id.btn_login);
        etpass = findViewById(R.id.edtxt_password);
        etphno = findViewById(R.id.edtxt_mobileNumber);
    }

    public void confirmLogin(View view) {
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
                DatabaseReference ref = mDatabase.child("PROVIDERS").child(phoneNumber);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            fdbPassword = dataSnapshot.child("password").getValue().toString();
                            name = dataSnapshot.child("name").getValue().toString();
                            if (fdbPassword.equals(password)) {
                                login.setEnabled(true);
                                Intent i = new Intent(LoginActivity.this, Dashboard.class);
                                i.putExtra("phoneNumber", phoneNumber);
                                i.putExtra("Name", name);
                                startActivity(i);
                                // showPopup("login","Login Successful");
                                progressBar.setVisibility(View.INVISIBLE);
                            } else {
                                progressBar.setVisibility(View.INVISIBLE);
                                login.setEnabled(true);
                                showPopup("Try Again", "Password is incorrect.");
                            }
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            login.setEnabled(true);
                            showPopup("Admin", "You are not Registered.\nCreate an Account First");
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
        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this)
                .setTitle(otp_error)
                .setMessage(s)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, null)
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setIcon(android.R.drawable.ic_dialog_alert);
        alert.show();
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
}
