package com.easysolutions.dod;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;

public class Tab_Photocopy extends Fragment {

    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_photocopy, container, false);
        final Order_Photocopy photocopy_order = new Order_Photocopy();
        final EditText no_of_pages = view.findViewById(R.id.et_photocopy_pages_no);
        final EditText no_of_copies = view.findViewById(R.id.et_photocopy_copies_no);
        final RadioButton oneside = (RadioButton) view.findViewById(R.id.rb_photocopy_onesided);
        final RadioButton twosided = (RadioButton) view.findViewById(R.id.rb_photocopy_twosided);
        final EditText pickup_point = view.findViewById(R.id.et_photocopy_pickup_point);
        final Button pickup_time = view.findViewById(R.id.bt_photocopy_pickup_time);
        final EditText extra_Details = view.findViewById(R.id.et_photocopy_details);
        Button place_Order = view.findViewById(R.id.bt_photocopy_place_order);
        progressBar = view.findViewById(R.id.progressBar4);
        pickup_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int mHour = calendar.get(Calendar.HOUR_OF_DAY);
                int mMint = calendar.get(Calendar.MINUTE);
                TimePickerDialog Tp = new TimePickerDialog(AvailableServices.context, android.R.style.Theme_Holo_Light_Dialog,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String patch = hourOfDay >= 12 ? " PM" : " AM";
                                if (hourOfDay == 0) {
                                    hourOfDay = 12;
                                }
                                if (hourOfDay > 12) {
                                    hourOfDay -= 12;
                                }
                                photocopy_order.setPickup_time(hourOfDay + ":" + (minute > 9 ? minute : "0" + minute) + patch);
                                pickup_time.setText("Pickup Time: " + photocopy_order.getPickup_time());
                            }
                        }, mHour, mMint, false);
                Tp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Tp.show();
            }
        });

        place_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photocopy_order.setNo_of_pages(no_of_pages.getText().toString());
                photocopy_order.setNo_of_copiess(no_of_copies.getText().toString());
                photocopy_order.setPickup_point(pickup_point.getText().toString());
                if (oneside.isChecked()) {
                    photocopy_order.setPage_sides("One sided");
                } else if (twosided.isChecked()) {
                    photocopy_order.setPage_sides("Two sided");
                } else {
                    showSimplePopup("error", "find error");
                }
                photocopy_order.setExtra_details(extra_Details.getText().toString());
                if (photocopy_order.validate_photcopyData()) {
                    String msg = "Your Order details:\nNo of Pages : " + photocopy_order.getNo_of_pages() +
                            "\nNo of Copies : " + photocopy_order.getNo_of_copiess() +
                            "\n Pages (1 Sided\\ 2 Sided): " + photocopy_order.getPage_sides();
                    showPopup("Order Details", msg, photocopy_order);
                } else {
                    showSimplePopup("Admin", "Plz fill the form Correctly.");
                }

            }
        });
        return view;
    }


    private void showPopup(String Title, String s, final Order_Photocopy photocopy_order) {
        AlertDialog.Builder alert = new AlertDialog.Builder(AvailableServices.context)
                .setTitle(Title)
                .setMessage(s)
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        placeOrder(photocopy_order);
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(AvailableServices.context, "Order canceled", Toast.LENGTH_SHORT);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);

        alert.show();
    }

    private void placeOrder(final Order_Photocopy photocopy_order) {
        progressBar.setVisibility(View.VISIBLE);
        final DatabaseReference databaseReference = AvailableServices.databaseReference;
        final String phoneNumber = AvailableServices.phoneNumber;
        final String name = AvailableServices.name;
        photocopy_order.setName(name);
        photocopy_order.setCusNo(phoneNumber);
        photocopy_order.setStatus("pending");
        photocopy_order.setType("photocopy");
        Calendar calendar = Calendar.getInstance();
        photocopy_order.setCurrent_time_milies("" + System.currentTimeMillis());
        photocopy_order.setOrderno(photocopy_order.getCurrent_time_milies());
        photocopy_order.setDate(calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.MONTH) + 1 + "/" + calendar.get(Calendar.DAY_OF_MONTH));
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String patch=hour>=12?" PM":" AM";
        int h = hour;
        if (hour == 0) {
            h = 12;
        }

        photocopy_order.setTime((hour > 12 ? hour -= 12 : h) + ":" + (calendar.get(Calendar.MINUTE)>9?calendar.get(Calendar.MINUTE):"0"+calendar.get(Calendar.MINUTE)) + ":"
                + calendar.get(Calendar.SECOND) + patch);
        databaseReference.child("ORDERS").child("pending").child(photocopy_order.getOrderno())
                .setValue(photocopy_order).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                databaseReference.child("ORDERS").child("List").child(photocopy_order.getOrderno()).child("CusNo")
                        .setValue(phoneNumber);
                databaseReference.child("ORDERS").child("List").child(photocopy_order.getOrderno()).child("name")
                        .setValue(name);
                databaseReference.child("ORDERS").child("List").child(photocopy_order.getOrderno()).child("status")
                        .setValue(photocopy_order.getStatus());
                progressBar.setVisibility(View.GONE);
                showSimplePopup("Order Details", "Your order has been placed successfully.");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                showSimplePopup("Order Details", "Your order placement has been failed.");
                e.printStackTrace();
            }
        });
    }


    private void showSimplePopup(String Title, String s) {
        AlertDialog.Builder alert = new AlertDialog.Builder(AvailableServices.context)
                .setTitle(Title)
                .setMessage(s)
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.ok, null)
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setIcon(android.R.drawable.ic_dialog_alert);

        alert.show();
    }
}
