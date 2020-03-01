package com.dod.DOD_ServiceProviders.ui.complaints;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.dod.DOD_ServiceProviders.Dashboard;
import com.dod.DOD_ServiceProviders.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterComplaints extends RecyclerView.Adapter {

    class ComplaintViewHolder extends RecyclerView.ViewHolder{
        TextView  orderno,complaint;
        Button chat;
        ImageButton close;
        public ComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            close=itemView.findViewById(R.id.close);
            orderno=itemView.findViewById(R.id.orderno);
            complaint=itemView.findViewById(R.id.complaint);
            chat=itemView.findViewById(R.id.chatadmin);
        }
    }

    Context context;
    ArrayList<ComplaiintsViewModal> complaints;

    public AdapterComplaints(Context context, ArrayList<ComplaiintsViewModal> complaints) {
        this.context = context;
        this.complaints = complaints;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 1:
                return new ComplaintViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.complaintrow,parent,false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch(holder.getItemViewType()){
            case 1:
                ((ComplaintViewHolder)holder).orderno.setText(complaints.get(position).getOrderno());
                ((ComplaintViewHolder)holder).complaint.setText(complaints.get(position).getComplaint());
                final int p=position;
                final String orno=((ComplaintViewHolder)holder).orderno.getText().toString();
                ((ComplaintViewHolder)holder).chat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,Messaging.class);
                        intent.putExtra("prono",complaints.get(p).getProNo());
                        intent.putExtra("orderno",complaints.get(p).getOrderno());
                        context.startActivity(intent);
                    }
                });
                ((ComplaintViewHolder)holder).close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopUp("Confirmation","Are you sure you want to remove your complaint?");
                    }

                    private void showPopUp(String confirmation, String s) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(context)
                                .setTitle(confirmation)
                                .setMessage(s)

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
                                        databaseReference.child("COMPLAINTS").child(Dashboard.phoneNumber).child("complaint").
                                                child(orno).removeValue();
                                        databaseReference.child("COMPLAINTS").child(Dashboard.phoneNumber).child("msg").
                                                child(orno).removeValue();
                                    }
                                }).setNegativeButton(android.R.string.no,null)
                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setIcon(android.R.drawable.ic_dialog_alert);
                        alert.show();
                    }
                });
                break;
            default:
                return;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return complaints.size()>0?1:0;
    }

    @Override
    public int getItemCount() {
        return complaints.size();
    }
}
