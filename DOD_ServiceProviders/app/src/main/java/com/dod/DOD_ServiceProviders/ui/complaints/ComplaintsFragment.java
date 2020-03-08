package com.dod.DOD_ServiceProviders.ui.complaints;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dod.DOD_ServiceProviders.Dashboard;
import com.dod.DOD_ServiceProviders.R;
import com.dod.DOD_ServiceProviders.notification.APIClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComplaintsFragment extends Fragment {

    ArrayList<String> ordernos = new ArrayList<>();
    ArrayList<ComplaiintsViewModal> complaints = new ArrayList<>();
    AdapterComplaints adapter;
    Spinner orderno;
    DatabaseReference databaseReference;
    final ComplaiintsViewModal complaiintsViewModal = new ComplaiintsViewModal();
    Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_send, container, false);
        final Spinner status = root.findViewById(R.id.orstatus);
        context=container.getContext();
        orderno = root.findViewById(R.id.orderno);
        final EditText complaint = root.findViewById(R.id.complaint);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        adapter = new AdapterComplaints(container.getContext(), complaints);
        RecyclerView review = root.findViewById(R.id.listview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        review.setLayoutManager(layoutManager);
        review.setAdapter(adapter);
        Button sub = root.findViewById(R.id.submit);
        getComplaints();
        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    return;
                }
                getordernos(parent.getItemAtPosition(position).toString(), container.getContext());
                complaiintsViewModal.setStatus(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        orderno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                complaiintsViewModal.setOrderno(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status.getSelectedItemPosition() == 0 || orderno.getSelectedItemPosition() == 0) {
                    showPopup("Admin", "Plz select the status and orderno first.", getContext());
                    return;
                }
                if (complaiintsViewModal.getOrderno() != null && complaiintsViewModal.getStatus() != null) {
                    if (complaint != null && complaint.getText().toString().trim().length() <= 20) {
                        showPopup("Admin", "Complaint must contain atleast 20 charachters.", container.getContext());
                    } else {
                        complaiintsViewModal.setComplaint(complaint.getText().toString());
                        complaiintsViewModal.setPhNo(Dashboard.phoneNumber);
                        complaiintsViewModal.setName(Dashboard.name);
                        complaiintsViewModal.setComplaintBy("pro");
                        submitComplaint();
                    }
                }
            }

            private void submitComplaint() {
                complaiintsViewModal.setComplaintID("" + System.currentTimeMillis());
                databaseReference.child("COMPLAINTS").child("complaint").child(complaiintsViewModal.getComplaintID())
                        .setValue(complaiintsViewModal).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            notifyAdmin(complaiintsViewModal.getComplaint(),complaiintsViewModal.getName());
                            showPopup("Confirmation", "Your complaint has been submitted", container.getContext());
                            orderno.setSelection(0);
                            status.setSelection(0);
                            complaint.setText("");
                        } else {
                            showPopup("Alert", "Your complaint is not been submitted.", container.getContext());
                        }

                    }
                });
            }
        });

        return root;
    }

    private void getComplaints() {
        databaseReference.child("COMPLAINTS").child("complaint").orderByChild("phNo")
                .equalTo(Dashboard.phoneNumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                complaints.clear();
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() != 0) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ComplaiintsViewModal complaint = snapshot.getValue(ComplaiintsViewModal.class);
                        complaints.add(complaint);
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showPopup(String otp_error, String s, Context context) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context)
                .setTitle(otp_error)
                .setMessage(s)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, null)
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setIcon(android.R.drawable.ic_dialog_alert);
        alert.show();
    }

    private void getordernos(final String status, final Context context) {
        //Toast.makeText(context, status+Dashboard.phoneNumber, Toast.LENGTH_SHORT).show();
        Log.d("927277", "getordernos: " + status + Dashboard.phoneNumber);
        databaseReference.child("ORDERS").child("List").orderByChild("ProNo").equalTo(Dashboard.phoneNumber)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Toast.makeText(context, ""+dataSnapshot, Toast.LENGTH_SHORT).show();
                        Log.d("927277", "getordernos: " + dataSnapshot.getValue());
                        ordernos.clear();
                        ordernos.add("Select Order No");
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (snapshot.hasChild("proVis") && snapshot.child("proVis").getValue().equals("false")) {
                                continue;
                            }
                            if (snapshot.child("status").getValue().toString().equalsIgnoreCase(status)) {
                                ordernos.add(snapshot.getKey());
                            }

                        }
                        if (ordernos.size() == 1) {
                            ordernos.add("No order has been " + status);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, ordernos);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        orderno.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("927277", "onCancelled: " + databaseError.getMessage());
                    }
                });

    }

    private void notifyAdmin(String complaint, String proName) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        //Log.d("927277", "notifyProviders: " + proNo);
        reference.child("Admin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child("token").exists() && dataSnapshot.child("token").getValue() == null) {
                    Log.d("927277", "tken is null: ");
                    return;
                }
                String token = dataSnapshot.child("token").getValue().toString();
                JsonObject payload = buildNotificationPayload(token, proName, complaint);
                APIClient.getApiService().sendNotification(payload).enqueue(
                        new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(context, "Notification send successful",
                                            Toast.LENGTH_LONG).show();
                                    Log.d("927277", "Notification sent: " + response.toString());
                                } else {
                                    Log.d("927277", "Error responce: " + response.toString());
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                Log.d("927277", "Error failed: ");
                            }
                        });
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private JsonObject buildNotificationPayload(String token, String cusName, String complaint) {
        // compose notification json payload
        JsonObject payload = new JsonObject();
        payload.addProperty("to", token);
        // compose data payload here
        JsonObject data = new JsonObject();
        data.addProperty("subtext", "New Complaint");
        data.addProperty("message", complaint);
        data.addProperty("title", cusName);
        data.addProperty("intent", "admin1");
        // add data payload
        payload.add("data", data);
        return payload;
    }
}