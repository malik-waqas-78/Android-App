package com.easysolutions.dod.complaints;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.easysolutions.dod.R;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<MsgDetails> msgs;
    LayoutInflater inflater;

    public MessageAdapter(Context context, ArrayList<MsgDetails> msgs) {
        this.context = context;
        this.msgs = msgs;
    }

    @Override
    public int getItemViewType(int position) {
        return msgs.get(position).getViewType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new MyCusViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_cus, parent, false));
            default:
                return new MyProViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_pro, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                //customer msg
                ((MyCusViewHolder) holder).message.setText(msgs.get(position).getMsgText());
                ((MyCusViewHolder) holder).tame.setText(msgs.get(position).getMsgTime());
                break;
            default:
                ((MyProViewHolder) holder).message.setText(msgs.get(position).getMsgText());
                ((MyProViewHolder) holder).tame.setText(msgs.get(position).getMsgTime());

        }
    }

    @Override
    public int getItemCount() {
        return msgs.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class MyProViewHolder extends RecyclerView.ViewHolder {
        TextView message, tame;

        public MyProViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.msg_pro);
            tame = itemView.findViewById(R.id.msg_protime);
        }
    }

    public static class MyCusViewHolder extends RecyclerView.ViewHolder {
        TextView message, tame;

        public MyCusViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.msg_cus);
            tame = itemView.findViewById(R.id.msg_custime);
        }
    }
}
