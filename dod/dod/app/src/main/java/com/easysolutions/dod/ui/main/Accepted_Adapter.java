package com.easysolutions.dod.ui.main;

import android.content.Context;
import android.content.DialogInterface;
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

import com.easysolutions.dod.Order_Conveyance;
import com.easysolutions.dod.Order_Photocopy;
import com.easysolutions.dod.Order_Print;
import com.easysolutions.dod.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Accepted_Adapter extends RecyclerView.Adapter {

    public  static  String TAG="92727";

    public Accepted_Adapter(ArrayList<Object> aorders, ArrayList<String> aType, Context applicationContext) {
        this.aOrders=aorders;
        this.aTypes=aType;
        this.context=applicationContext;
        databaseReference=FirebaseDatabase.getInstance().getReference();
    }

    public static class Accepted_Orders_View extends RecyclerView.ViewHolder {

        TextView ordrNO, orderType, textside1, textSide2, timeNdate,bill;
        Button completed,viewbill;
        ProgressBar progressBar;

        public Accepted_Orders_View(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.pbar_cancel);
            ordrNO = itemView.findViewById(R.id.orderId);
            orderType = itemView.findViewById(R.id.type);
            textside1 = itemView.findViewById(R.id.textside1);
            textSide2 = itemView.findViewById(R.id.textside2);
            timeNdate = itemView.findViewById(R.id.timendate);
            bill=itemView.findViewById(R.id.bill);
            viewbill=itemView.findViewById(R.id.viewbill);
            completed = itemView.findViewById(R.id.completedOrder);
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

    Context context;
    ArrayList<Object> aOrders = new ArrayList<>();
    ArrayList<String> aTypes = new ArrayList<>();
    DatabaseReference databaseReference;
    ProgressBar progressBar;
    int ind;



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                Log.d(TAG, "onCreateViewHolder: no orders");
                return new No_Orders(LayoutInflater.from(parent.getContext()).inflate(R.layout.no_orders, parent, false));
            case 1:
                Log.d(TAG, "onCreateViewHolder: row");
                return new Accepted_Orders_View(LayoutInflater.from(parent.getContext()).inflate(R.layout.aorders_row, parent, false));
            default:
                Log.d(TAG, "onCreateViewHolder: null");
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: "+position);
        switch (holder.getItemViewType()) {
            case 0:
                //no orders layout
                ((No_Orders) holder).title.setText(R.string.progressActivityEmptyTitlePlaceholderA);
                ((No_Orders) holder).content.setText(R.string.progressActivityEmptyContentPlaceholderA);
                break;
            case 1:
                //pending order details
                ind = position;
                String orderno="";
                this.progressBar = ((Accepted_Orders_View) holder).progressBar;
                if (aTypes.get(position).equals("conveyance")) {
                    final Order_Conveyance conveyance_order = (Order_Conveyance) aOrders.get(position);
                    orderno=conveyance_order.getOrder_no();
                    if(conveyance_order.getBill()!=null)
                    ((Accepted_Orders_View)holder).bill.setText("Bill : "+conveyance_order.getBill()+" Rs");
                    else
                        ((Accepted_Orders_View)holder).bill.setText("Bill : 0 Rs");
                    ((Accepted_Orders_View) holder).orderType.setText("Conveyance Order");
                    ((Accepted_Orders_View) holder).ordrNO.setText("ID: " + conveyance_order.getOrder_no());
                    ((Accepted_Orders_View) holder).timeNdate.setText(conveyance_order.getTime() + "\n" + conveyance_order.getDate());
                    ((Accepted_Orders_View) holder).textside1.setText("Pickup Point : " + "\n" + conveyance_order.getPickup_point() +
                            "\nTransport Type : " + "\n" + conveyance_order.getTransport_Type() +
                            "\nPickup Time : " + "\n" + conveyance_order.getPickup_Time());
                    ((Accepted_Orders_View) holder).textSide2.setText("Drop Point : " + "\n" + conveyance_order.getDrop_Point() +
                            "\nSeats : " + "\n" + conveyance_order.getSeats() +
                            "\nPickup Date : " + "\n" + conveyance_order.getPickup_Date());

                    ((Accepted_Orders_View) holder).completed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showPopup("Complete Order", "Do you want to Mark your order as Completed?", conveyance_order);
                        }
                    });

                } else if (aTypes.get(position).equals("print")) {
                    final Order_Print print_order = (Order_Print) aOrders.get(position);
                    orderno=print_order.getOrder_no();
                    if(print_order.getBill()!=null)
                    ((Accepted_Orders_View)holder).bill.setText("Bill : "+print_order.getBill()+" Rs");
                    else
                        ((Accepted_Orders_View)holder).bill.setText("Bill : 0 Rs");
                    ((Accepted_Orders_View) holder).orderType.setText("Print");
                    ((Accepted_Orders_View) holder).ordrNO.setText("ID: " + print_order.getOrder_no());
                    ((Accepted_Orders_View) holder).textside1.setText("No of Pages:" + "\n" + print_order.getNo_of_Pages() +
                            "\nPrint Type:" + "\n" + print_order.getPage_Color() +
                            "\nPickup Point:" + "\n" + (print_order.getPickup_Point() != null ? print_order.getPickup_Point() : "null")
                    );
                    ((Accepted_Orders_View) holder).textSide2.setText("No of Prints:" + "\n" + print_order.getNo_of_Prints() +
                            "\nPickup Time:" + "\n" + (print_order.getPickup_Time() != null ? print_order.getPickup_Time() : "null") +
                            "\nDoc. Uploaded:" + "\n" + (print_order.getUrl() != null ? "True" : "False")
                    );

                    ((Accepted_Orders_View) holder).timeNdate.setText(print_order.getTime() + "\n" + print_order.getDate());
                    ((Accepted_Orders_View) holder).completed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showPopup("Complete Order", "Do you want to Mark your order as Completed?", print_order);
                        }
                    });
                } else if (aTypes.get(position).equals("photocopy")) {
                    final Order_Photocopy photocopy_order = (Order_Photocopy) aOrders.get(position);
                    orderno=photocopy_order.getOrderno();
                    if(photocopy_order.getBill()!=null)
                    ((Accepted_Orders_View)holder).bill.setText("Bill : "+photocopy_order.getBill()+" Rs");
                    else
                        ((Accepted_Orders_View)holder).bill.setText("Bill : 0 Rs");
                    ((Accepted_Orders_View) holder).orderType.setText("Photocopy");
                    ((Accepted_Orders_View) holder).ordrNO.setText("ID: " + photocopy_order.getOrderno());
                    ((Accepted_Orders_View) holder).textside1.setText("No of Pages:" + "\n" + photocopy_order.getNo_of_pages() +
                            "\nCopy Type:" + "\n" + photocopy_order.getPage_sides() +
                            "\nPickup Point:" + "\n" + photocopy_order.getPickup_point()
                    );
                    ((Accepted_Orders_View) holder).textSide2.setText("No of Copies:" + "\n" + photocopy_order.getNo_of_copiess() +
                            "\nPickup Time:" + "\n" + photocopy_order.getPickup_time());

                    ((Accepted_Orders_View) holder).timeNdate.setText(photocopy_order.getTime() + "\n" + photocopy_order.getDate());
                    ((Accepted_Orders_View) holder).completed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showPopup("Complete Order", "Do you want to Mark your order as Completed?", photocopy_order);
                        }
                    });
                }

                if(!orderno.equals("")) {
                    final String finalOrderno = orderno;
                    ((Accepted_Orders_View) holder).viewbill.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            databaseReference.child("BILL").child(finalOrderno).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (!dataSnapshot.exists() || dataSnapshot.getChildrenCount() == 0) {
                                        showSimplePopup("Bill Details", "Bill is Not submitted yet.");
                                        return;
                                    }
                                    String type = dataSnapshot.child("type").getValue().toString();
                                    if (type.equals("cony")) {
                                        Bill_Conveyance bill_conveyance = dataSnapshot.getValue(Bill_Conveyance.class);
                                        String billdetails = "Fair : " + bill_conveyance.getFair() + "\n" +
                                                "Driver Name : " + bill_conveyance.getDriver_name();
                                        showSimplePopup("Bill Details", billdetails);
                                    } else if (type.equals("print")) {
                                        Bill_Printing bill_printing = dataSnapshot.getValue(Bill_Printing.class);
                                        String billdetails = "Total Bill : " + bill_printing.getTotalbill() + "\n" +
                                                "DoD Charges : " + bill_printing.getDodcharges() + "\n" +
                                                "Your Earnings : " + bill_printing.getProcharges() + "\n" +
                                                "Expances : " + bill_printing.ExpendeturesEarnings();
                                        showSimplePopup("Bill Details", billdetails);
                                    } else if (type.equals("copy")) {
                                        Bill_Copying bill_copying = dataSnapshot.getValue(Bill_Copying.class);
                                        String billdetails = "Total Bill : " + bill_copying.getTotalbill() + "\n" +
                                                "DoD Charges : " + bill_copying.getDodcharges() + "\n" +
                                                "Your Earnings : " + bill_copying.getProcharges() + "\n" +
                                                "Expances : " + bill_copying.ExpendeturesEarnings();
                                        showSimplePopup("Bill Details", billdetails);
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
        return aOrders.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (aOrders.get(position).equals("0")) {
            return 0;
        } else {
            return 1;
        }
    }

    //=======================================Custom Funtions===============================================//
    private void showPopup(String Title, String s, final Order_Conveyance conveyance_order) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context)
                .setTitle(Title)
                .setMessage(s)
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Complete_Order_conveyance(conveyance_order);
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("No", null)
                .setIcon(android.R.drawable.ic_dialog_alert);
        alert.show();

    }

    private void showPopup(String Title, String s, final Order_Print print_order) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context)
                .setTitle(Title)
                .setMessage(s)
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Complete_order_print(print_order);
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("No", null)
                .setIcon(android.R.drawable.ic_dialog_alert);

        alert.show();


    }

    private void showPopup(String Title, String s, final Order_Photocopy photocopy_order) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context)
                .setTitle(Title)
                .setMessage(s)
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Complete_order_Photocopy(photocopy_order);
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("No", null)
                .setIcon(android.R.drawable.ic_dialog_alert);

        alert.show();

    }

    private void showSimplePopup(String Title, String s) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context)
                .setTitle(Title)
                .setMessage(s)
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("OK", null)
                // A null listener allows the button to dismiss the dialog and take no further action.

                .setIcon(android.R.drawable.ic_dialog_alert);

        alert.show();

    }

    public void Complete_Order_conveyance(final Order_Conveyance conveyance_order) {

        databaseReference.child("BILL").child(conveyance_order.getOrder_no())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    conveyance_order.setStatus("completed");
                    progressBar.setVisibility(View.VISIBLE);
                    Log.d(TAG, "Complete_Order_conveyance: "+conveyance_order.getOrder_no());
                    databaseReference.child("ORDERS").child("List").child(conveyance_order.getOrder_no()).
                            child("status").setValue("completed");
                    databaseReference.child("ORDERS").child("accepted").child(conveyance_order.getOrder_no()).removeValue();
                    databaseReference.child("ORDERS").child("completed")
                            .child(conveyance_order.getOrder_no()).setValue(conveyance_order);
                    progressBar.setVisibility(View.GONE);
                    Bill_Conveyance bill_conveyance=dataSnapshot.getValue(Bill_Conveyance.class);
                    String fiar="Fair : " +bill_conveyance.getFair();
                    String drivername="\nDriver Name : "+bill_conveyance.getDriver_name();
                    showSimplePopup("Bill Details", fiar+drivername
                            +"\n\"Your Order has been Marked as Completed.\"");
                }else{
                    showSimplePopup("Message", "Your Order is not Completed yet.");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        Orders.refresh();
        //this.notifyDataSetChanged();

    }

    public void Complete_order_print(final Order_Print print_order) {

        databaseReference.child("BILL").child(print_order.getOrder_no())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            print_order.setStatus("completed");
                            progressBar.setVisibility(View.VISIBLE);
                            databaseReference.child("ORDERS").child("List").child(print_order.getOrder_no()).
                                    child("status").setValue("completed");
                            databaseReference.child("ORDERS").child("accepted").child(print_order.getOrder_no()).removeValue();
                            databaseReference.child("ORDERS").child("completed")
                                    .child(print_order.getOrder_no()).setValue(print_order);
                            progressBar.setVisibility(View.GONE);
                            Bill_Printing bill_printing=dataSnapshot.getValue(Bill_Printing.class);
                            String priceP="Printing Expances : " +bill_printing.getPrintingprice();
                            String totalBill="\nTotal Bill : "+bill_printing.getTotalbill();
                            showSimplePopup("Bill Details", priceP+totalBill
                                    +"\n\"Your Order has been Marked as Completed.\"");
                        }else{
                            showSimplePopup("Message", "Your Order is not Completed yet.");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void Complete_order_Photocopy(final Order_Photocopy photocopy_order) {
        databaseReference.child("BILL").child(photocopy_order.getOrderno())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            photocopy_order.setStatus("completed");
                            progressBar.setVisibility(View.VISIBLE);
                            databaseReference.child("ORDERS").child("List").child(photocopy_order.getOrderno()).
                                    child("status").setValue("completed");
                            databaseReference.child("ORDERS").child("accepted").child(photocopy_order.getOrderno()).removeValue();
                            databaseReference.child("ORDERS").child("completed")
                                    .child(photocopy_order.getOrderno()).setValue(photocopy_order);
                            progressBar.setVisibility(View.GONE);
                            Bill_Copying bill_copying=dataSnapshot.getValue(Bill_Copying.class);
                            String priceC="Copying Expances : " +bill_copying.getCopyingprice();
                            String totalBill="\nTotal Bill : "+bill_copying.getTotalbill();
                            showSimplePopup("Bill Details", priceC+totalBill
                                    +"\n\"Your Order has been Marked as Completed.\"");
                        }else{
                            showSimplePopup("Message", "Order is not Completed yet.");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


//        Orders.refresh();
    }
}
