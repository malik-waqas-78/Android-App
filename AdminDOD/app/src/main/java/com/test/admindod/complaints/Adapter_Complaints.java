package com.test.admindod.complaints;

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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.test.admindod.R;

import java.util.ArrayList;

public class Adapter_Complaints extends RecyclerView.Adapter {
    class ComplaintViewHolder extends RecyclerView.ViewHolder{
        TextView orderno,complaint;
        Button chat;
        public ComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            orderno=itemView.findViewById(R.id.orderno);
            complaint=itemView.findViewById(R.id.complaint);
            chat=itemView.findViewById(R.id.chatadmin);
        }
    }
    private class No_Complaints extends RecyclerView.ViewHolder{
        TextView title,content;


        public No_Complaints(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.text_title);
            content=itemView.findViewById(R.id.text_description);
        }
    }
    Context context;
    ArrayList<ComplaiintsViewModal> complaints;

    public Adapter_Complaints(Context context, ArrayList<ComplaiintsViewModal> complaints) {
        this.context = context;
        this.complaints = complaints;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                return  new No_Complaints(LayoutInflater.from(parent.getContext()).inflate(R.layout.no_orders,parent,false));
            case 1:
                return new ComplaintViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.complaintrow,parent,false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        switch(holder.getItemViewType()){
            case 0:
                ((No_Complaints)holder).title.setText("No Complaints");
                ((No_Complaints)holder).content.setText("No Open Complaints.");
                break;
            case 1:
                ((ComplaintViewHolder)holder).orderno.setText(complaints.get(position).getOrderno());
                ((ComplaintViewHolder)holder).complaint.setText(complaints.get(position).getComplaint());
                final int p=position;
               // final String orno=((ComplaintViewHolder)holder).orderno.getText().toString();
                ((ComplaintViewHolder)holder).chat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context, MessagingChat.class);
                        intent.putExtra("name",complaints.get(p).getName());
                        intent.putExtra("no",complaints.get(p).getPhNo());
                        intent.putExtra("cmpID",complaints.get(p).getComplaintID());
                        context.startActivity(intent);
                    }
                });
                break;
            default:
                return;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return Integer.valueOf(complaints.get(position).getViewType());
    }

    @Override
    public int getItemCount() {
        return complaints.size();
    }
}
