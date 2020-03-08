package com.test.admindod.orders;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.test.admindod.R;

import java.util.ArrayList;

public class MyOrdersListViewAdapter extends RecyclerView.Adapter {


    private class No_Orders extends RecyclerView.ViewHolder{
        TextView title,content;


        public No_Orders(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.text_title);
            content=itemView.findViewById(R.id.text_description);
        }
    }

    private class Orders_ViewHolder extends RecyclerView.ViewHolder{
        TextView et_type,et_orderID,et_side1,et_side2,et_timeDate;
        Button details;

        public Orders_ViewHolder(@NonNull View itemView) {
            super(itemView);

            et_type = itemView.findViewById(R.id.type);
            et_orderID = itemView.findViewById(R.id.orderId);
            et_side1 = itemView.findViewById(R.id.textside1);
            et_side2 = itemView.findViewById(R.id.textside2);
            et_timeDate = itemView.findViewById(R.id.timeDate);
            details=itemView.findViewById(R.id.viewdetails);
        }
    }

    Context context;
    ArrayList<Object> order_details;
    ArrayList<String> types;
    LayoutInflater layoutInflater;
    int layout;
    DatabaseReference databaseReference;
    public MyOrdersListViewAdapter(Context context, ArrayList<Object> order_details, int layout, ArrayList<String> types) {
        this.context = context;
        this.order_details = order_details;
        this.layout = layout;
        this.types=types;
        this.layoutInflater = layoutInflater.from(context);
        databaseReference= FirebaseDatabase.getInstance().getReference();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                return  new No_Orders(LayoutInflater.from(parent.getContext()).inflate(R.layout.no_orders,parent,false));
            case 1:
                return  new Orders_ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orders,parent,false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case 0:
                ((No_Orders)holder).title.setText("No Orders Available.");
                ((No_Orders)holder).content.setText("No orders are in process yet.");
            break;
            case 1:
                Orders_ViewHolder viewHolder=(Orders_ViewHolder)holder;
                if(types.get(position).equals("conveyance")){
                    final Order_Conveyance conveyance_order= (Order_Conveyance) order_details.get(position);
                    viewHolder.et_type.setText("Conveyance Order");
                    viewHolder.et_orderID.setText("ID: "+conveyance_order.getOrder_no());
                    viewHolder.et_timeDate.setText(conveyance_order.getTime()+"\n"+conveyance_order.getDate());
                    viewHolder.et_side1.setText("Pickup Point : "+"\n"+conveyance_order.getPickup_point()+
                            "\nTransport Type : "+"\n"+conveyance_order.getTransport_Type()+
                            "\nPickup Time : "+"\n"+conveyance_order.getPickup_Time());
                    viewHolder.et_side2.setText("Drop Point : "+"\n"+conveyance_order.getDrop_Point()+
                            "\nSeats : "+"\n"+conveyance_order.getSeats()+
                            "\nPickup Date : "+"\n"+conveyance_order.getPickup_Date());
                    viewHolder.et_timeDate.setText(conveyance_order.getTime()+"\n"+conveyance_order.getDate());
                    viewHolder.details.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String str="Order placed by : "+conveyance_order.getName()+"\n";
                            str+=conveyance_order.getProname()!=null?"Service Provider : "+conveyance_order.getProname()+"\n":"";
                            str+=conveyance_order.getBill()!=null?"Total Bill : "+conveyance_order.getBill()+"\n":"";
                            showPopup("Details",str);
                        }
                    });

                }else if(types.get(position).equals("print")){
                    final Order_Print print_order= (Order_Print) order_details.get(position);
                    viewHolder.et_type.setText("Print");
                    viewHolder.et_orderID.setText("ID: "+print_order.getOrder_no());
                    viewHolder.et_side1.setText("No of Pages:"+"\n"+print_order.getNo_of_Pages()+
                            "\nPrint Type:"+"\n"+print_order.getPage_Color()+
                            "\nPickup Point:"+"\n"+(print_order.getPickup_Point()!=null?print_order.getPickup_Point():"null")
                    );
                    viewHolder.et_side2.setText("No of Prints:"+"\n"+print_order.getNo_of_Prints()+
                            "\nPickup Time:"+"\n"+(print_order.getPickup_Time()!=null?print_order.getPickup_Time():"null")+
                            "\nDoc. Uploaded:"+"\n"+(print_order.getUrl()!=null?"True":"False")
                    );

                    viewHolder.et_timeDate.setText(print_order.getTime()+"\n"+print_order.getDate());
                    viewHolder.details.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String str="Order placed by : "+print_order.getName()+"\n";
                            str+=print_order.getProname()!=null?"Service Provider : "+print_order.getProname()+"\n":"";
                            str+=print_order.getBill()!=null?"Total Bill : "+print_order.getBill()+"\n":"";
                            showPopup("Details",str);
                        }
                    });

                }else if(types.get(position).equals("photocopy")){
                    final Order_Photocopy photocopy_order= (Order_Photocopy) order_details.get(position);
                    viewHolder.et_type.setText("Photocopy");
                    viewHolder.et_orderID.setText("ID: "+photocopy_order.getOrderno());
                    viewHolder.et_side1.setText("No of Pages:"+"\n"+photocopy_order.getNo_of_pages()+
                            "\nCopy Type:"+"\n"+photocopy_order.getPage_sides()+
                            "\nPickup Point:"+"\n"+photocopy_order.getPickup_point()
                    );
                    viewHolder.et_side2.setText("No of Copies:"+"\n"+photocopy_order.getNo_of_copiess()+
                            "\nPickup Time:"+"\n"+photocopy_order.getPickup_time());
                    viewHolder.et_timeDate.setText(photocopy_order.getTime()+"\n"+photocopy_order.getDate());
                    viewHolder.details.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String str="Order placed by : "+photocopy_order.getName()+"\n";
                            str+=photocopy_order.getProname()!=null?"Service Provider : "+photocopy_order.getProname()+"\n":"";
                            str+=photocopy_order.getBill()!=null?"Total Bill : "+photocopy_order.getBill()+"\n":"";
                            showPopup("Details",str);
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

    @Override
    public int getItemViewType(int position) {
        if(types.get(position).equals("empty")){
            return 0;
        }else{
            return 1;
        }
    }

    private void showPopup(String otp_error, String s) {
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
