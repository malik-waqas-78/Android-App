package com.easysolutions.dod;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;


import java.util.ArrayList;

public class chatAdatpter extends BaseAdapter {

    Context context;
    ArrayList<List_Row> listRows;
    LayoutInflater layoutInflater;
    int layout;

    public chatAdatpter(Context context, ArrayList<List_Row> listRows, int layout) {
        this.context = context;
        this.listRows = listRows;
        layoutInflater = layoutInflater.from(context);
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return listRows.size();
    }

    @Override
    public Object getItem(int i) {
        return listRows.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view=layoutInflater.inflate(layout, viewGroup, false);
            TextView id,name,time;
            id=view.findViewById(R.id.order_id);
            name=view.findViewById(R.id.cus_name);
            time=view.findViewById(R.id.time);
            final List_Row row=listRows.get(i);
            id.setText(row.getId());
            name.setText(row.getProName());
            time.setText(row.getTime());
            CardView cardView=view.findViewById(R.id.chat_cardview);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //DO NEXT THING
                    Intent intent=new Intent(context, Messaging.class);
                    intent.putExtra("proName",row.getProName());
                    intent.putExtra("proNo",row.getProNo());
                    intent.putExtra("cusNo",row.getCusNo());
                    intent.putExtra("idNo",row.getId());
                    context.startActivity(intent);
                    //Toast.makeText(context, "selected card view", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return view;
    }
}
