package com.test.admindod.providers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.admindod.R;
import com.test.admindod.providers.totalearnings.EarningFragment;

import java.util.ArrayList;

public class Adapter_Provider extends RecyclerView.Adapter {

    private class Provider_ViewHOlder extends RecyclerView.ViewHolder{
        TextView name,phoneNumber;
        ImageButton forward;
        public Provider_ViewHOlder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            phoneNumber=itemView.findViewById(R.id.phonenumber);
            forward=itemView.findViewById(R.id.forward);
        }
    }

    private class No_Providers extends RecyclerView.ViewHolder{
        TextView title,content;


        public No_Providers(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.text_title);
            content=itemView.findViewById(R.id.text_description);
        }
    }

    ArrayList<row_Providers> row_providers=new ArrayList<>();
    Context context;

    public Adapter_Provider(ArrayList<row_Providers> row_providers, Context context) {
        this.row_providers = row_providers;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return row_providers.get(position).getViewType();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                return  new No_Providers(LayoutInflater.from(parent.getContext()).inflate(R.layout.no_orders,parent,false));
            case 1:
                return new Provider_ViewHOlder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_providers,parent,false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case 0:
                break;
            case 1:
                final row_Providers row=row_providers.get(position);
                Provider_ViewHOlder vHOlder=((Provider_ViewHOlder) holder);
                vHOlder.name.setText(row.getName());
                vHOlder.phoneNumber.setText(row.getNo());
                vHOlder.forward.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //get different details about the providers
                        Intent inten=new Intent(context, EarningFragment.class);
                        inten.putExtra("phoneNumber",row.getNo());
                        inten.putExtra("proname",row.getName());
                        inten.putExtra("password",row.getPassword());
                        context.startActivity(inten);
                    }
                });
                break;
            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return row_providers.size();
    }
}
