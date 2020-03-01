package com.dod.DOD_ServiceProviders.ui.allorders;

import android.content.Context;
import android.util.Log;
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

import com.dod.DOD_ServiceProviders.Dashboard;
import com.dod.DOD_ServiceProviders.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Pending extends RecyclerView.Adapter {

    //=============================================viewholder classes ==========================////

    public static class pendingViewHolder extends RecyclerView.ViewHolder {
        TextView ordrNO, orderType, textside1, textSide2, timeNdate;
        Button accept;
        ProgressBar progressBar;

        public pendingViewHolder(@NonNull View itemView) {
            super(itemView);
            ordrNO = itemView.findViewById(R.id.orderId);
            orderType = itemView.findViewById(R.id.type);
            textside1 = itemView.findViewById(R.id.textside1);
            textSide2 = itemView.findViewById(R.id.textside2);
            timeNdate = itemView.findViewById(R.id.timeDate);
            accept = itemView.findViewById(R.id.accept);
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

    ////=======================================Setting overriden methods=======================////

    Context context;
    ArrayList<Object> order_details;
    ArrayList<String> types;
    DatabaseReference databaseReference;

    public Adapter_Pending(Context context, ArrayList<Object> order_details, ArrayList<String> types) {
        this.context = context;
        this.order_details = order_details;
        this.types = types;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }


    @Override
    public int getItemViewType(int position) {
        if(order_details.get(position).equals("0")){
            return 0;
        }
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                 Log.d("92727", "onCreateViewHolder: no order");
                return new No_Orders(LayoutInflater.from(parent.getContext()).inflate(R.layout.no_orders, parent, false));
            case 1:
                 Log.d("92727", "onCreateViewHolder: found");
                return new pendingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_row, parent, false));
            default:
                Log.d("92727", "onCreateViewHolder: null");
                return null;

        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()){
            case 0:
                Log.d("92727", "onBindViewHolder: no orders");
                ((No_Orders)holder).title.setText(R.string.progressActivityEmptyTitlePlaceholder);
                ((No_Orders)holder).content.setText(R.string.progressActivityEmptyContentPlaceholder);
                break;
            case 1:
                ((pendingViewHolder)holder).progressBar.setVisibility(View.GONE);
                if(types.get(position).equals("conveyance")){
                    final Order_Conveyance conveyance_order = (Order_Conveyance) order_details.get(position);
                    ((pendingViewHolder)holder).orderType.setText("Conveyance");
                    ((pendingViewHolder)holder).ordrNO.setText("ID: " + conveyance_order.getOrder_no());
                    ((pendingViewHolder)holder).timeNdate.setText(conveyance_order.getTime()
                            + "\n" + conveyance_order.getDate());
                    ((pendingViewHolder)holder).textside1.setText("Pickup Point : " + "\n" +
                            conveyance_order.getPickup_point() +
                        "\nTransport Type : " + "\n" + conveyance_order.getTransport_Type() +
                        "\nPickup Time : " + "\n" + conveyance_order.getPickup_Time());
                    ((pendingViewHolder)holder).textSide2.setText("Drop Point : " + "\n" + conveyance_order.getDrop_Point() +
                     "\nSeats : " + "\n" + conveyance_order.getSeats() +
                        "\nPickup Date : " + "\n" + conveyance_order.getPickup_Date());
                    final int i=position;
                    ((pendingViewHolder)holder).accept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((pendingViewHolder)holder).progressBar.setVisibility(View.VISIBLE);
                            accept_Order(conveyance_order);
                            showPopup("Admin", "Order has been acccepted. Now you can chat with the customer for more details.", context);
                            ((pendingViewHolder)holder).progressBar.setVisibility(View.GONE);
                        }
                    });
                }else if(types.get(position).equals("print")){
                    final Order_Print print_order = (Order_Print) order_details.get(position);
                    ((pendingViewHolder)holder).orderType.setText("Print");
                    ((pendingViewHolder)holder).ordrNO.setText("ID: " + print_order.getOrder_no());
                    ((pendingViewHolder)holder).textside1.setText("No of Pages:" + "\n" + print_order.getNo_of_Pages() +
                      "\nPrint Type:" + "\n" + print_order.getPage_Color() +
                        "\nPickup Point:" + "\n" + (print_order.getPickup_Point() != null ? print_order.getPickup_Point() : "null"));
                    ((pendingViewHolder)holder).textSide2.setText("No of Prints:" + "\n" + print_order.getNo_of_Prints() +
                        "\nPickup Time:" + "\n" + (print_order.getPickup_Time() != null ? print_order.getPickup_Time() : "null") +
                        "\nDoc. Uploaded:" + "\n" + (print_order.getUrl() != null ? "True" : "False"));
                    ((pendingViewHolder)holder).timeNdate.setText(print_order.getTime() + "\n" + print_order.getDate());
                    final int i=position;
                    ((pendingViewHolder)holder).accept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((pendingViewHolder)holder).progressBar.setVisibility(View.VISIBLE);
                            accept_Order(print_order);
                            showPopup("Admin", "Order has been acccepted. Now you can chat with the customer for more details.", context);
                            ((pendingViewHolder)holder).progressBar.setVisibility(View.GONE);
                        }
                    });
                }else if(types.get(position).equals("photocopy")){
                    final Order_Photocopy photocopy_order = (Order_Photocopy) order_details.get(position);
                    ((pendingViewHolder)holder).orderType.setText("Photocopy");
                    ((pendingViewHolder)holder).ordrNO.setText("ID: " + photocopy_order.getOrderno());
                    ((pendingViewHolder)holder).textside1.setText("No of Pages:" + "\n" + photocopy_order.getNo_of_pages() +
                       "\nCopy Type:" + "\n" + photocopy_order.getPage_sides() +
                        "\nPickup Point:" + "\n" + photocopy_order.getPickup_point());
                    ((pendingViewHolder)holder).textSide2.setText("No of Copies:" + "\n" + photocopy_order.getNo_of_copiess() +
                       "\nPickup Time:" + "\n" + photocopy_order.getPickup_time());
                    ((pendingViewHolder)holder).timeNdate.setText(photocopy_order.getTime() + "\n" + photocopy_order.getDate());
                    final int i=position;
                    ((pendingViewHolder)holder).accept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((pendingViewHolder)holder).progressBar.setVisibility(View.VISIBLE);
                            accept_Order(photocopy_order);
                            showPopup("Admin", "Order has been acccepted. Now you can chat with the customer for more details.", context);
                            ((pendingViewHolder)holder).progressBar.setVisibility(View.GONE);
                        }
                    });
                }
                break;
            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return order_details.size();
    }



// ========================================Custom Methods================================================================//
    public void accept_Order(Order_Photocopy order_photocopy) {
        order_photocopy.setStatus("accepted");
        order_photocopy.setProNo(Dashboard.phoneNumber);
        databaseReference.child("ORDERS").child("accepted").child(order_photocopy.getOrderno()).setValue(order_photocopy);
        databaseReference.child("ORDERS").child("List").child(order_photocopy.getOrderno()).child("ProNo").setValue(Dashboard.phoneNumber);
        databaseReference.child("ORDERS").child("List").child(order_photocopy.getOrderno()).child("ProName").setValue(Dashboard.name);
        databaseReference.child("ORDERS").child("List").child(order_photocopy.getOrderno()).child("time").setValue(order_photocopy.getTime());
        databaseReference.child("ORDERS").child("List").child(order_photocopy.getOrderno()).child("status").setValue("accepted");
        databaseReference.child("ORDERS").child("pending").child(order_photocopy.getOrderno()).removeValue();
    }

    public void accept_Order(Order_Print order_print) {
        order_print.setStatus("accepted");
        order_print.setProNo(Dashboard.phoneNumber);
        databaseReference.child("ORDERS").child("accepted").child(order_print.getOrder_no()).setValue(order_print);
        databaseReference.child("ORDERS").child("List").child(order_print.getOrder_no()).child("ProNo").setValue(Dashboard.phoneNumber);
        databaseReference.child("ORDERS").child("List").child(order_print.getOrder_no()).child("ProName").setValue(Dashboard.name);
        databaseReference.child("ORDERS").child("List").child(order_print.getOrder_no()).child("time").setValue(order_print.getTime());
        databaseReference.child("ORDERS").child("List").child(order_print.getOrder_no()).child("status").setValue("accepted");
        databaseReference.child("ORDERS").child("pending").child(order_print.getOrder_no()).removeValue();
    }

    public void accept_Order(Order_Conveyance order_conveyance) {
        order_conveyance.setStatus("accepted");
        order_conveyance.setProNo(Dashboard.phoneNumber);
        databaseReference.child("ORDERS").child("accepted").child(order_conveyance.getOrder_no()).setValue(order_conveyance);
        databaseReference.child("ORDERS").child("List").child(order_conveyance.getOrder_no()).child("ProNo").setValue(Dashboard.phoneNumber);
        databaseReference.child("ORDERS").child("List").child(order_conveyance.getOrder_no()).child("ProName").setValue(Dashboard.name);
        databaseReference.child("ORDERS").child("List").child(order_conveyance.getOrder_no()).child("time").setValue(order_conveyance.getTime());
        databaseReference.child("ORDERS").child("List").child(order_conveyance.getOrder_no()).child("status").setValue("accepted");
        databaseReference.child("ORDERS").child("pending").child(order_conveyance.getOrder_no()).removeValue();
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
}
