package com.easysolutions.dod;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

public class Tab_Conveyance extends Fragment {

    ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab_conveyance,container,false);
        final Order_Conveyance conveyance_order=new Order_Conveyance();
        final EditText pickup_Point = view.findViewById(R.id.et_cony_pickup_place);
        final EditText drop_Point = view.findViewById(R.id.et_cony_drop_place);
        Spinner transport_Type = view.findViewById(R.id.sp_cony_type);
        final EditText seats = view.findViewById(R.id.et_cony_seats);
        final Button pickup_Time = view.findViewById(R.id.bt_cony_pickuptime);
        final Button pickup_Date = view.findViewById(R.id.bt_cony_pickdate);
        final EditText extraDetails = view.findViewById(R.id.et_cony_details);
        Button place_OrderButton = view.findViewById(R.id.bt_cony_place_order);
        progressBar=view.findViewById(R.id.progressBar3);
        transport_Type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                conveyance_order.setTransport_Type(adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        pickup_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar= Calendar.getInstance();
                int mHour=calendar.get(Calendar.HOUR_OF_DAY);
                int mMint=calendar.get(Calendar.MINUTE);
                TimePickerDialog Tp = new TimePickerDialog(AvailableServices.context,android.R.style.Theme_Holo_Light_Dialog,
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String patch=hourOfDay>=12?" PM":" AM";
                        String min;
                        if(hourOfDay==0){
                            hourOfDay=12;
                        }
                        if(hourOfDay>12){
                            hourOfDay-=12;
                        }

                        conveyance_order.setPickup_Time(hourOfDay+":"+(minute>9?minute:"0"+minute)+patch);
                        pickup_Time.setText("Pickup Time"+"\n"+conveyance_order.getPickup_Time());
                    }
                },mHour,mMint,false);
                Tp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Tp.show();
            }
        });
        pickup_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar= Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dp = new DatePickerDialog(AvailableServices.context,android.R.style.Theme_Holo_Light_Dialog,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        conveyance_order.setPickup_Date(dayOfMonth+"-"+monthOfYear+1+"-"+year);
                        pickup_Date.setText("Pickup Date"+"\n"+conveyance_order.getPickup_Date());
                    }
                },year,month,day);
                dp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dp.show();
            }
        });

        place_OrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conveyance_order.setDrop_Point(drop_Point.getText().toString());
                conveyance_order.setExtr_Details(extraDetails.getText().toString());
                conveyance_order.setSeats(seats.getText().toString());
                conveyance_order.setPickup_point(pickup_Point.getText().toString());
                if(conveyance_order.validate()){
                    String msg="Your order details:\nTransport Type : "+conveyance_order.transport_Type+
                            "\nSeats : "+conveyance_order.seats+"\nPickup Time: "+conveyance_order.pickup_Time+
                            "\npickup Date: "+conveyance_order.pickup_Date;
                    showPopup("Order Details",msg,conveyance_order);
                }else{
                    showSimplePopup("Admin","Plz fill the form properly");
                }
            }
        });

        return view;
    }
    private void showPopup(String Title, String s, final Order_Conveyance conveyance_order) {
        AlertDialog.Builder alert=new AlertDialog.Builder(AvailableServices.context)
                .setTitle(Title)
                .setMessage(s)
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        placeOrder(conveyance_order);
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(AvailableServices.context,"Order canceled",Toast.LENGTH_SHORT).show();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);

        alert.show();
    }
    private void showSimplePopup(String Title, String s) {
        AlertDialog.Builder alert=new AlertDialog.Builder(AvailableServices.context)
                .setTitle(Title)
                .setMessage(s)
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.ok,null)
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setIcon(android.R.drawable.ic_dialog_alert);

        alert.show();
    }
    private void placeOrder(final Order_Conveyance conveyance_order) {
        progressBar.setVisibility(View.VISIBLE);

        final DatabaseReference databaseReference=AvailableServices.databaseReference;
        final String phoneNumber=AvailableServices.phoneNumber;
        final String name=AvailableServices.name;
        conveyance_order.setName(name);
        conveyance_order.setCusNo(phoneNumber);
        conveyance_order.setStatus("pending");
        Calendar calendar=Calendar.getInstance();
        conveyance_order.setType("conveyance");
        conveyance_order.setCurrent_Time_milies(""+System.currentTimeMillis());
        conveyance_order.setOrder_no(conveyance_order.getCurrent_Time_milies());
        conveyance_order.setDate(calendar.get(Calendar.YEAR)+"/"+calendar.get(Calendar.MONTH)+1+"/"+calendar.get(Calendar.DAY_OF_MONTH));
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        String patch=hour>=12?" PM":" AM";
        int h=hour;
        if(hour==0){
            h=12;
        }
        conveyance_order.setTime((hour>12?hour-=12:h)+":"+(calendar.get(Calendar.MINUTE)>9?calendar.get(Calendar.MINUTE):"0"+calendar.get(Calendar.MINUTE))+":"
                +(calendar.get(Calendar.SECOND)>9?calendar.get(Calendar.SECOND):"0"+calendar.get(Calendar.SECOND))+patch);

        databaseReference.child("ORDERS").child("pending").child(conveyance_order.getOrder_no())
                .setValue(conveyance_order).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                databaseReference.child("ORDERS").child("List").child(conveyance_order.getOrder_no()).child("CusNo")
                        .setValue(phoneNumber);
                databaseReference.child("ORDERS").child("List").child(conveyance_order.getOrder_no()).child("name")
                        .setValue(name);
                databaseReference.child("ORDERS").child("List").child(conveyance_order.getOrder_no()).child("status")
                        .setValue(conveyance_order.getStatus());
                progressBar.setVisibility(View.GONE);
                showSimplePopup("Order Details","Your order has been placed successfully.");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                showSimplePopup("Order Details","Your order placement has been failed.");
                e.printStackTrace();

            }
        });

    }
}
