package com.dod.DOD_ServiceProviders.ui.allorders;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.dod.DOD_ServiceProviders.Dashboard;
import com.dod.DOD_ServiceProviders.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Adapter_Accepted extends RecyclerView.Adapter {

    //==========================================Custom Classes======================================//
    public static class acceptedViewHolder extends RecyclerView.ViewHolder {
        TextView ordrNO, orderType, textside1, textSide2, timeNdate,bill;
        Button completed,viewbill;
        ProgressBar progressBar;

        public acceptedViewHolder(@NonNull View itemView) {
            super(itemView);
            ordrNO = itemView.findViewById(R.id.orderId);
            orderType = itemView.findViewById(R.id.type);
            textside1 = itemView.findViewById(R.id.textside1);
            textSide2 = itemView.findViewById(R.id.textside2);
            timeNdate = itemView.findViewById(R.id.timeDate);
            bill=itemView.findViewById(R.id.bill);
            completed = itemView.findViewById(R.id.complete);
            progressBar=itemView.findViewById(R.id.progressBar);
            viewbill=itemView.findViewById(R.id.viewbill);
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

    public Adapter_Accepted(Context context, ArrayList<Object> order_details, ArrayList<String> types) {
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
                return new acceptedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.accepted_row,parent,false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull  RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case 0:
                ((No_Orders)holder).title.setText(R.string.progressActivityEmptyTitlePlaceholderA);
                ((No_Orders)holder).content.setText(R.string.progressActivityEmptyContentPlaceholderA);
                break;
            case 1:
                ((acceptedViewHolder)holder).progressBar.setVisibility(View.GONE);
               String orderno="";
                if(types.get(position).equals("conveyance")){
                    final Order_Conveyance conveyance_order = (Order_Conveyance) order_details.get(position);
                    orderno=conveyance_order.getOrder_no();
                    if(conveyance_order.getBill()!=null)
                    ((acceptedViewHolder)holder).bill.setText("Bill : "+conveyance_order.getBill()+" Rs");
                    else
                        ((acceptedViewHolder)holder).bill.setText("Bill : 0 Rs");
                    ((acceptedViewHolder)holder).orderType.setText("Conveyance");
                    ((acceptedViewHolder)holder).ordrNO.setText("ID: " + conveyance_order.getOrder_no());
                    ((acceptedViewHolder)holder).timeNdate.setText(conveyance_order.getTime()
                            + "\n" + conveyance_order.getDate());
                    ((acceptedViewHolder)holder).textside1.setText("Pickup Point : " + "\n" +
                            conveyance_order.getPickup_point() +
                            "\nTransport Type : " + "\n" + conveyance_order.getTransport_Type() +
                            "\nPickup Time : " + "\n" + conveyance_order.getPickup_Time());
                    ((acceptedViewHolder)holder).textSide2.setText("Drop Point : " + "\n" + conveyance_order.getDrop_Point() +
                            "\nSeats : " + "\n" + conveyance_order.getSeats() +
                            "\nPickup Date : " + "\n" + conveyance_order.getPickup_Date());
                    final int i=position;
                    final ProgressBar progressBar=((acceptedViewHolder)holder).progressBar;
                    ((acceptedViewHolder)holder).completed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            progressBar.setVisibility(View.VISIBLE);
                            complete_Order(conveyance_order);
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }else if(types.get(position).equals("print")){
                    final Order_Print print_order = (Order_Print) order_details.get(position);
                    orderno=print_order.getOrder_no();
                    if(print_order.getBill()!=null)
                    ((acceptedViewHolder)holder).bill.setText("Bill : "+print_order.getBill()+" Rs");
                    else
                        ((acceptedViewHolder)holder).bill.setText("Bill : 0 Rs");
                    ((acceptedViewHolder)holder).orderType.setText("Print");
                    ((acceptedViewHolder)holder).ordrNO.setText("ID: " + print_order.getOrder_no());
                    ((acceptedViewHolder)holder).textside1.setText("No of Pages:" + "\n" + print_order.getNo_of_Pages() +
                            "\nPrint Type:" + "\n" + print_order.getPage_Color() +
                            "\nPickup Point:" + "\n" + (print_order.getPickup_Point() != null ? print_order.getPickup_Point() : "null"));
                    ((acceptedViewHolder)holder).textSide2.setText("No of Prints:" + "\n" + print_order.getNo_of_Prints() +
                            "\nPickup Time:" + "\n" + (print_order.getPickup_Time() != null ? print_order.getPickup_Time() : "null") +
                            "\nDoc. Uploaded:" + "\n" + (print_order.getUrl() != null ? "True" : "False"));
                    ((acceptedViewHolder)holder).timeNdate.setText(print_order.getTime() + "\n" + print_order.getDate());
                    final int i=position;
                    final ProgressBar progressBar=((acceptedViewHolder)holder).progressBar;

                    ((acceptedViewHolder)holder).completed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            progressBar.setVisibility(View.VISIBLE);
                            complete_Order(print_order);
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }else if(types.get(position).equals("photocopy")){
                    final Order_Photocopy photocopy_order = (Order_Photocopy) order_details.get(position);
                    orderno=photocopy_order.getOrderno();
                    if(photocopy_order.getBill()!=null)
                    ((acceptedViewHolder)holder).bill.setText("Bill : "+photocopy_order.getBill()+" Rs");
                    else
                        ((acceptedViewHolder)holder).bill.setText("Bill : 0 Rs");
                    final TextView bill=((acceptedViewHolder)holder).bill;
                    ((acceptedViewHolder)holder).orderType.setText("Photocopy");
                    ((acceptedViewHolder)holder).ordrNO.setText("ID: " + photocopy_order.getOrderno());
                    ((acceptedViewHolder)holder).textside1.setText("No of Pages:" + "\n" + photocopy_order.getNo_of_pages() +
                            "\nCopy Type:" + "\n" + photocopy_order.getPage_sides() +
                            "\nPickup Point:" + "\n" + photocopy_order.getPickup_point());
                    ((acceptedViewHolder)holder).textSide2.setText("No of Copies:" + "\n" + photocopy_order.getNo_of_copiess() +
                            "\nPickup Time:" + "\n" + photocopy_order.getPickup_time());
                    ((acceptedViewHolder)holder).timeNdate.setText(photocopy_order.getTime() + "\n" + photocopy_order.getDate());
                    final int i=position;
                    final ProgressBar progressBar=((acceptedViewHolder)holder).progressBar;
                    ((acceptedViewHolder)holder).completed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            progressBar.setVisibility(View.VISIBLE);
                            complete_Order(photocopy_order);
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
                if(!orderno.equals("")){
                    final String finalOrderno = orderno;
                    ((acceptedViewHolder)holder).viewbill.setOnClickListener(new View.OnClickListener() {
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
    public int getItemCount() {
        return order_details.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(order_details.get(position).equals("0")){
            return 0;
        }
        return 1;
    }

    public void complete_Order(final Order_Photocopy order_photocopy) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.bill_print);
        dialog.setTitle("Printing Bill");
        final EditText copyingprice=dialog.findViewById(R.id.print_price);
        final TextView procharges=dialog.findViewById(R.id.proEarning);
        final TextView totalbill=dialog.findViewById(R.id.total_bill);
        final ProgressBar pbar=dialog.findViewById(R.id.pbar);
        final TextView dodcharges=dialog.findViewById(R.id.dodcharges);
        final Bill_Copying bill_copying=new Bill_Copying();
        copyingprice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bill_copying.setCopyingprice(copyingprice.getText().toString());
                if (bill_copying.validate(context)) {
                    bill_copying.calculate_Bill();
                    dodcharges.setText("DoD charges : "+bill_copying.getDodcharges()+" Rs");
                    procharges.setText("Your Earnings : " + bill_copying.getProcharges()+" Rs");
                    totalbill.setText("Total Bill : " + bill_copying.getTotalbill()+" Rs");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Button btn=dialog.findViewById(R.id.submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bill_copying.validate(context)){
                    pbar.setVisibility(View.VISIBLE);
                    bill_copying.calculate_Bill();
                    bill_copying.setType("copy");
                    dodcharges.setText("DoD charges : "+bill_copying.getDodcharges()+" Rs");
                    procharges.setText("Your Earnings : " + bill_copying.getProcharges()+" Rs");
                    totalbill.setText("Total Bill : " + bill_copying.getTotalbill()+" Rs");
                    bill_copying.setCusNo(order_photocopy.getCusNo());
                    bill_copying.setProNo(order_photocopy.getProNo());
                    bill_copying.setOrderNo(order_photocopy.getOrderno());
                    databaseReference.child("BILL").child(bill_copying.getOrderNo()).setValue(bill_copying);
                    databaseReference.child("ORDERS").child("accepted").child(bill_copying.getOrderNo()).child("bill").setValue(bill_copying.getTotalbill());
                    pbar.setVisibility(View.GONE);
                    dialog.dismiss();
                }

            }
        });
        dialog.show();
    }

    public void complete_Order(final Order_Print order_print) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.bill_print);
        dialog.setTitle("Printing Bill");
        final EditText printingprice=dialog.findViewById(R.id.print_price);
        final TextView procharges=dialog.findViewById(R.id.proEarning);
        final TextView totalbill=dialog.findViewById(R.id.total_bill);
        final TextView dodcharges=dialog.findViewById(R.id.dodcharges);
        final ProgressBar pbar=dialog.findViewById(R.id.pbar);

        final Bill_Printing bill_printing=new Bill_Printing();
        printingprice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bill_printing.setPrintingprice(printingprice.getText().toString());
                if (bill_printing.validate(context)) {
                    bill_printing.calculate_Bill();
                    dodcharges.setText("DoD charges : "+bill_printing.getDodcharges());
                    procharges.setText("Your Earnings : " + bill_printing.getProcharges());
                    totalbill.setText("Total Bill : " + bill_printing.getTotalbill());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Button btn=dialog.findViewById(R.id.submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bill_printing.validate(context)){
                    pbar.setVisibility(View.VISIBLE);
                    bill_printing.calculate_Bill();
                    bill_printing.setType("print");
                    dodcharges.setText("DoD charges : "+bill_printing.getDodcharges()+" Rs");
                    procharges.setText("Your Earnings : " + bill_printing.getProcharges()+" Rs");
                    totalbill.setText("Total Bill : " + bill_printing.getTotalbill()+" Rs");
                    bill_printing.setCusNo(order_print.getCusNo());
                    bill_printing.setProNo(order_print.getProNo());
                    bill_printing.setOrderNo(order_print.getOrder_no());
                    databaseReference.child("BILL").child(bill_printing.getOrderNo()).setValue(bill_printing);
                    databaseReference.child("ORDERS").child("accepted").child(bill_printing.getOrderNo()).child("bill").setValue(bill_printing.getTotalbill());
                    pbar.setVisibility(View.GONE);
                    dialog.dismiss();
                 }

            }
        });
        dialog.show();
    }

    public void complete_Order(final Order_Conveyance order_conveyance) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.bill_conveyance);
        dialog.setTitle("Conveyance Bill");
        final EditText fair,driver_name;
        fair=dialog.findViewById(R.id.fair);
        driver_name=dialog.findViewById(R.id.driver_name);
        Button submit=dialog.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bill_Conveyance bill_conveyance=new Bill_Conveyance();
                bill_conveyance.setFair(fair.getText().toString());
                bill_conveyance.setType("cony");
                bill_conveyance.setDriver_name(driver_name.getText().toString());
                bill_conveyance.setCusNo(order_conveyance.getCusNo());
                bill_conveyance.setProNo(order_conveyance.getProNo());
                bill_conveyance.setOrderNo(order_conveyance.getOrder_no());
                databaseReference.child("BILL").child(bill_conveyance.getOrderNo()).setValue(bill_conveyance);
                databaseReference.child("ORDERS").child("accepted").child(bill_conveyance.getOrderNo()).child("bill").setValue(bill_conveyance.getFair());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showPopup(String otp_error, String s, Context context) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context)
                .setTitle(otp_error)
                .setMessage(s)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.ok, null)
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setIcon(android.R.drawable.ic_dialog_alert);
        alert.show();
    }
}
