package com.easysolutions.dod;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class Tab_printdoc extends Fragment {
    Order_Print print_order;
    FileUpload fileUpload;
    Uri uriData;
    Button uploadDOC;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_printdoc, container, false);
        final EditText no_of_pages = view.findViewById(R.id.et_print_pages_no);
        final EditText no_of_prints = view.findViewById(R.id.et_print_print_no);
        final EditText pickupPoint = view.findViewById(R.id.et_print_pickup_point);
        final EditText extraDetails = view.findViewById(R.id.et_print_details);
        final Button pickupTime = view.findViewById(R.id.bt_print_pickup_time);
        uploadDOC = view.findViewById(R.id.bt_print_upload);
        Button placeOrder = view.findViewById(R.id.bt_print_place_order);
        final RadioButton rbColor = view.findViewById(R.id.rb_print_color);
        final RadioButton rbBW = view.findViewById(R.id.rb_print_bw);
        print_order = new Order_Print();
        progressBar = view.findViewById(R.id.progressBar5);
        pickupTime.setOnClickListener(new View.OnClickListener() {
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
                                print_order.setPickup_Time(hourOfDay + ":" + (minute > 9 ? minute : "0" + minute) + patch);
                                pickupTime.setText("Pickup Time: " + print_order.getPickup_Time());
                            }
                        }, mHour, mMint, false);
                Tp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Tp.show();
            }
        });
        uploadDOC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUploadDialog();
            }
        });
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                print_order.setNo_of_Pages(no_of_pages.getText().toString());
                print_order.setNo_of_Prints(no_of_prints.getText().toString());
                print_order.setExtra_details(extraDetails.getText().toString());
                print_order.setPickup_Point(pickupPoint.getText().toString());
                if (rbColor.isChecked()) {
                    print_order.setPage_Color("Color print");
                } else if (rbBW.isChecked()) {
                    print_order.setPage_Color("B&W print");
                }
                if (print_order.validateData()) {
                    String msg = "Your Order details:\nNo of Pages : " + print_order.getNo_of_Pages() +
                            "\nNo of Copies : " + print_order.getNo_of_Prints() +
                            "\n Color (B/W Print\\ Color Print): " + print_order.page_Color;
                    showPopup("Order Details", msg, print_order);
                }


            }
        });
        return view;
    }

    private void showPopup(String Title, String s, final Order_Print print_order) {
        AlertDialog.Builder alert = new AlertDialog.Builder(AvailableServices.context)
                .setTitle(Title)
                .setMessage(s)
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        placeOrder(print_order);
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

    private void placeOrder(Order_Print print_order) {
        if (uriData != null) {
            uploadfile(uriData, print_order);
        } else {
            simpleUpload(print_order);
        }

    }

    private void simpleUpload(final Order_Print print_order) {
        progressBar.setVisibility(View.VISIBLE);
        final DatabaseReference databaseReference = AvailableServices.databaseReference;
        final String phoneNumber = AvailableServices.phoneNumber;
        final String name = AvailableServices.name;
        print_order.setCusNo(phoneNumber);
        print_order.setName(name);
        print_order.setType("print");
        print_order.setStatus("pending");
        Calendar calendar = Calendar.getInstance();
        print_order.setCurrent_Time_milies("" + System.currentTimeMillis());
        print_order.setOrder_no(print_order.getCurrent_Time_milies());
        print_order.setDate(calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.MONTH) + 1 + "/" + calendar.get(Calendar.DAY_OF_MONTH));
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String patch=hour>=12?" PM":" AM";
        int h = hour;
        if (hour == 0) {
            h = 12;
        }
        print_order.setTime((hour > 12 ? hour -= 12 : h) + ":" + (calendar.get(Calendar.MINUTE)>9?calendar.get(Calendar.MINUTE):"0"+calendar.get(Calendar.MINUTE)) + ":"
                + calendar.get(Calendar.SECOND) + patch);
        databaseReference.child("ORDERS").child("pending").child(print_order.getOrder_no())
                .setValue(print_order).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                databaseReference.child("ORDERS").child("List").child(print_order.getOrder_no()).child("CusNo")
                        .setValue(phoneNumber);
                databaseReference.child("ORDERS").child("List").child(print_order.getOrder_no()).child("name")
                        .setValue(name);
                databaseReference.child("ORDERS").child("List").child(print_order.getOrder_no()).child("status")
                        .setValue(print_order.getStatus());
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

    private void showUploadDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(AvailableServices.context)
                .setTitle("Admin")
                .setMessage("If you upload a Doc It will be stored on cloud and it's prints will be delivered to you. " +
                        "After that it will be deleted.\n If a document is uploaded than there is no " +
                        "need for picking up the document.\n press OK to upload.")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openFiles();
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setIcon(android.R.drawable.ic_dialog_alert);

        alert.show();
    }

    public void openFiles() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 927);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 927) {
            if (data != null) {
                uriData = data.getData();
                if (uriData != null) {
                    uploadDOC.setTextSize(18);
                    uploadDOC.setText("Upload Document\nDocument Selected");//to print tick
                }
                //showSimplePopup("Choosen File Path",uriData.getPath());
            }
        }
    }

    private void uploadfile(final Uri data, final Order_Print print_order) {
        final ProgressDialog progressDialog = new ProgressDialog(AvailableServices.context);

        progressDialog.setTitle("Uploading.....\nBe Patient.");
        progressDialog.show();

        final StorageReference print = AvailableServices.storageReference.child("print/" +
                System.currentTimeMillis());
        UploadTask uploadTask = print.putFile(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                while (!uri.isComplete()) ;
                Uri url = uri.getResult();
                fileUpload = new FileUpload(String.valueOf(System.currentTimeMillis()), url.toString());
                print_order.setUrl(fileUpload.getUrl());
                progressDialog.dismiss();
                Toast.makeText(AvailableServices.context, "File Uploaded", Toast.LENGTH_LONG);
                simpleUpload(print_order);
                //showSimplePopup("Upload","Upload completed.");

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded     " + (int) progress + "%");

            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                showSimplePopup("Upload", "Upload canceled.");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showSimplePopup("Upload", "Upload Failed.\nPlz tryagain.");
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
