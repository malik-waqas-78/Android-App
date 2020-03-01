package com.dod.DOD_ServiceProviders.ui.allorders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.dod.DOD_ServiceProviders.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Adapter_Completed extends RecyclerView.Adapter {
    //==========================================Custom Classes======================================//
    public static class completedViewHolder extends RecyclerView.ViewHolder {
        TextView ordrNO, orderType, textside1, textSide2, timeNdate,bill;
        Button delete,viewbill;
        ProgressBar progressBar;

        public completedViewHolder(@NonNull View itemView) {
            super(itemView);
            ordrNO = itemView.findViewById(R.id.orderId);
            orderType = itemView.findViewById(R.id.type);
            textside1 = itemView.findViewById(R.id.textside1);
            textSide2 = itemView.findViewById(R.id.textside2);
            timeNdate = itemView.findViewById(R.id.timeDate);
            bill=itemView.findViewById(R.id.bill);
            viewbill=itemView.findViewById(R.id.viewbill);
            delete = itemView.findViewById(R.id.delete);
            progressBar=itemView.findViewById(R.id.progressBar);
        }
    }

    public static class No_Orders extends RecyclerView.ViewHolder {
        TextView title, content;
        ImageView img;

        public No_Orders(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_title);
            content = itemView.findViewById(R.id.text_description);
            img = itemView.findViewById(R.id.image_icon);
        }
    }

    //============================================AdAPTER Settings==================================//
    Context context;
    ArrayList<Object> order_details;
    ArrayList<String> types;
    DatabaseReference databaseReference;

    public Adapter_Completed(Context context, ArrayList<Object> order_details, ArrayList<String> types) {
        this.context = context;
        this.order_details = order_details;
        this.types = types;
        databaseReference= FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch(viewType){
            case 0:
                return new No_Orders(LayoutInflater.from(parent.getContext()).inflate(R.layout.no_orders,parent,false));
            case 1:
                return new completedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.completed_row,parent,false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case 0:
                ((No_Orders)holder).title.setText(R.string.progressActivityEmptyTitlePlaceholderC);
                ((No_Orders)holder).content.setText(R.string.progressActivityEmptyContentPlaceholderC);
                break;
            case 1:
                ((completedViewHolder)holder).progressBar.setVisibility(View.GONE);
                String orderno="";
                if(types.get(position).equals("conveyance")){
                    final Order_Conveyance conveyance_order = (Order_Conveyance) order_details.get(position);
                    orderno=conveyance_order.getOrder_no();
                    ((completedViewHolder)holder).bill.setText("Bill : "+conveyance_order.getBill()+" Rs");
                    ((completedViewHolder)holder).orderType.setText("Conveyance");
                    ((completedViewHolder)holder).ordrNO.setText("ID: " + conveyance_order.getOrder_no());
                    ((completedViewHolder)holder).timeNdate.setText(conveyance_order.getTime()
                            + "\n" + conveyance_order.getDate());
                    ((completedViewHolder)holder).textside1.setText("Pickup Point : " + "\n" +
                            conveyance_order.getPickup_point() +
                            "\nTransport Type : " + "\n" + conveyance_order.getTransport_Type() +
                            "\nPickup Time : " + "\n" + conveyance_order.getPickup_Time());
                    ((completedViewHolder)holder).textSide2.setText("Drop Point : " + "\n" + conveyance_order.getDrop_Point() +
                            "\nSeats : " + "\n" + conveyance_order.getSeats() +
                            "\nPickup Date : " + "\n" + conveyance_order.getPickup_Date());
                    final int i=position;
                    ((completedViewHolder)holder).delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((completedViewHolder)holder).progressBar.setVisibility(View.VISIBLE);
                            delete_Order(conveyance_order.getOrder_no());
                            showPopup("Admin", "Order has bbeen marked as completed.", context);
                            ((completedViewHolder)holder).progressBar.setVisibility(View.GONE);
                        }
                    });
                }else if(types.get(position).equals("print")){
                    final Order_Print print_order = (Order_Print) order_details.get(position);
                    orderno=print_order.getOrder_no();
                    ((completedViewHolder)holder).bill.setText("Bill : "+print_order.getBill()+" Rs");
                    ((completedViewHolder)holder).orderType.setText("Print");
                    ((completedViewHolder)holder).ordrNO.setText("ID: " + print_order.getOrder_no());
                    ((completedViewHolder)holder).textside1.setText("No of Pages:" + "\n" + print_order.getNo_of_Pages() +
                            "\nPrint Type:" + "\n" + print_order.getPage_Color() +
                            "\nPickup Point:" + "\n" + (print_order.getPickup_Point() != null ? print_order.getPickup_Point() : "null"));
                    ((completedViewHolder)holder).textSide2.setText("No of Prints:" + "\n" + print_order.getNo_of_Prints() +
                            "\nPickup Time:" + "\n" + (print_order.getPickup_Time() != null ? print_order.getPickup_Time() : "null") +
                            "\nDoc. Uploaded:" + "\n" + (print_order.getUrl() != null ? "True" : "False"));
                    ((completedViewHolder)holder).timeNdate.setText(print_order.getTime() + "\n" + print_order.getDate());
                    final int i=position;
                    ((completedViewHolder)holder).delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((completedViewHolder)holder).progressBar.setVisibility(View.VISIBLE);
                            delete_Order(print_order.getOrder_no());
                            showPopup("Admin", "Order has been acccepted. Now you can chat with the customer for more details.", context);
                            ((completedViewHolder)holder).progressBar.setVisibility(View.GONE);
                        }
                    });
                }else if(types.get(position).equals("photocopy")){
                    final Order_Photocopy photocopy_order = (Order_Photocopy) order_details.get(position);
                    orderno=photocopy_order.getOrderno();
                    ((completedViewHolder)holder).bill.setText("Bill : "+photocopy_order.getBill()+" Rs");
                    ((completedViewHolder)holder).orderType.setText("Photocopy");
                    ((completedViewHolder)holder).ordrNO.setText("ID: " + photocopy_order.getOrderno());
                    ((completedViewHolder)holder).textside1.setText("No of Pages:" + "\n" + photocopy_order.getNo_of_pages() +
                            "\nCopy Type:" + "\n" + photocopy_order.getPage_sides() +
                            "\nPickup Point:" + "\n" + photocopy_order.getPickup_point());
                    ((completedViewHolder)holder).textSide2.setText("No of Copies:" + "\n" + photocopy_order.getNo_of_copiess() +
                            "\nPickup Time:" + "\n" + photocopy_order.getPickup_time());
                    ((completedViewHolder)holder).timeNdate.setText(photocopy_order.getTime() + "\n" + photocopy_order.getDate());
                    final int i=position;
                    ((completedViewHolder)holder).delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((completedViewHolder)holder).progressBar.setVisibility(View.VISIBLE);
                            delete_Order(photocopy_order.getOrderno());
                            showPopup("Admin", "Order has been acccepted. Now you can chat with the customer for more details.", context);
                            ((completedViewHolder)holder).progressBar.setVisibility(View.GONE);
                        }
                    });
                }

                if(!orderno.equals("")){
                    final String finalOrderno = orderno;
                    ((completedViewHolder)holder).viewbill.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            databaseReference.child("BILL").child(finalOrderno).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(!dataSnapshot.exists()||dataSnapshot.getChildrenCount()==0){
                                        showPopup("Bill Details","Bill is Not submitted yet.",context);
                                        return;
                                    }
                                    String type=dataSnapshot.child("type").getValue().toString();
                                    if(type.equals("cony")){
                                        Bill_Conveyance bill_conveyance=dataSnapshot.getValue(Bill_Conveyance.class);
                                        String billdetails="Fair : "+bill_conveyance.getFair()+"\n"+
                                                "Driver Name : "+bill_conveyance.getDriver_name();
                                        showPopup("Bill Details",billdetails,context);
                                    }else if(type.equals("print")){
                                        Bill_Printing bill_printing=dataSnapshot.getValue(Bill_Printing.class);
                                        String billdetails="Total Bill : "+bill_printing.getTotalbill()+"\n"+
                                                "DoD Charges : "+bill_printing.getDodcharges()+"\n"+
                                                "Your Earnings : "+bill_printing.getProcharges()+"\n"+
                                                "Expances : "+bill_printing.ExpendeturesEarnings();
                                        showPopup("Bill Details",billdetails,context);
                                    }else if(type.equals("copy")){
                                        Bill_Copying bill_copying=dataSnapshot.getValue(Bill_Copying.class);
                                        String billdetails="Total Bill : "+bill_copying.getTotalbill()+"\n"+
                                                "DoD Charges : "+bill_copying.getDodcharges()+"\n"+
                                                "Your Earnings : "+bill_copying.getProcharges()+"\n"+
                                                "Expances : "+bill_copying.ExpendeturesEarnings();
                                        showPopup("Bill Details",billdetails,context);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    });
                }
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(order_details.get(position).equals("0")){
            return 0;
        }
        return 1;
    }

    @Override
    public int getItemCount() {
        return order_details.size();
    }

    public void delete_Order(String OrderNo) {
        databaseReference.child("ORDERS").child("completed").child(OrderNo).child("proVis").setValue("false");
        databaseReference.child("ORDERS").child("List")
                .child(OrderNo).child("proVis").setValue("false");
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
    }}
