package com.test.admindod;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyOrdersListViewAdapter extends BaseAdapter {
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

    @Override
    public int getCount() {
        return order_details.size();
    }

    @Override
    public Object getItem(int i) {
        return order_details.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(types.get(i).equals("no order")){
            view=layoutInflater.inflate(R.layout.ticket,viewGroup,false);
            return view;
        } else if(view==null){
            view=layoutInflater.inflate(layout,viewGroup,false);

            TextView et_type = view.findViewById(R.id.type);
            TextView et_orderID = view.findViewById(R.id.orderId);
            TextView et_side1 = view.findViewById(R.id.textside1);
            TextView et_side2 = view.findViewById(R.id.textside2);
            TextView et_timeDate = view.findViewById(R.id.timeDate);

            if(types.get(i).equals("conveyance")){
                final Order_Conveyance conveyance_order= (Order_Conveyance) order_details.get(i);
                et_type.setText("Conveyance Order");
                et_orderID.setText("ID: "+conveyance_order.getOrder_no());
                et_timeDate.setText(conveyance_order.getTime()+"\n"+conveyance_order.getDate());
                et_side1.setText("Pickup Point : "+"\n"+conveyance_order.getPickup_point()+
                        "\nTransport Type : "+"\n"+conveyance_order.getTransport_Type()+
                        "\nPickup Time : "+"\n"+conveyance_order.getPickup_Time());
                et_side2.setText("Drop Point : "+"\n"+conveyance_order.getDrop_Point()+
                        "\nSeats : "+"\n"+conveyance_order.getSeats()+
                        "\nPickup Date : "+"\n"+conveyance_order.getPickup_Date());
                et_timeDate.setText(conveyance_order.getTime()+"\n"+conveyance_order.getDate());

            }else if(types.get(i).equals("print")){
                final Order_Print print_order= (Order_Print) order_details.get(i);
                et_type.setText("Print");
                et_orderID.setText("ID: "+print_order.getOrder_no());
                et_side1.setText("No of Pages:"+"\n"+print_order.getNo_of_Pages()+
                        "\nPrint Type:"+"\n"+print_order.getPage_Color()+
                        "\nPickup Point:"+"\n"+(print_order.getPickup_Point()!=null?print_order.getPickup_Point():"null")
                );
                et_side2.setText("No of Prints:"+"\n"+print_order.getNo_of_Prints()+
                        "\nPickup Time:"+"\n"+(print_order.getPickup_Time()!=null?print_order.getPickup_Time():"null")+
                        "\nDoc. Uploaded:"+"\n"+(print_order.getUrl()!=null?"True":"False")
                );

                et_timeDate.setText(print_order.getTime()+"\n"+print_order.getDate());

            }else if(types.get(i).equals("photocopy")){
                final Order_Photocopy photocopy_order= (Order_Photocopy) order_details.get(i);
                et_type.setText("Photocopy");
                et_orderID.setText("ID: "+photocopy_order.getOrderno());
                et_side1.setText("No of Pages:"+"\n"+photocopy_order.getNo_of_pages()+
                        "\nCopy Type:"+"\n"+photocopy_order.getPage_sides()+
                        "\nPickup Point:"+"\n"+photocopy_order.getPickup_point()
                );
                et_side2.setText("No of Copies:"+"\n"+photocopy_order.getNo_of_copiess()+
                        "\nPickup Time:"+"\n"+photocopy_order.getPickup_time());
                et_timeDate.setText(photocopy_order.getTime()+"\n"+photocopy_order.getDate());
            }

            return view;
        }else{
            return view;
        }
    }
}
