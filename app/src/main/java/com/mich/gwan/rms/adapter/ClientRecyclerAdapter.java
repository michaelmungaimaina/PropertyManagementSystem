/*
 * Copyright (c) 2023. Maina Michael.
 */

package com.mich.gwan.rms.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mich.gwan.rms.R;
import com.mich.gwan.rms.activities.HouseActivity;
import com.mich.gwan.rms.models.Client;

import java.util.List;

public class ClientRecyclerAdapter extends RecyclerView.Adapter<ClientRecyclerAdapter.ViewHolder>{
    private final List<Client> list;
    private int index = RecyclerView.NO_POSITION;

    public ClientRecyclerAdapter(List<Client> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) throws InflateException {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.client_recycler, parent, false);

        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String clientId = String.valueOf(list.get(position).getClientId());
        holder.location.setText(list.get(position).getLocation());
        holder.clientName.setText(list.get(position).getFirstName() +" "+
                list.get(position).getSecondName() +" "+list.get(position).getLastName());

        holder.itemView.setSelected(index == position);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                notifyItemChanged(index);
                index = holder.getLayoutPosition();
                notifyItemChanged(index);
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyItemChanged(index);
                index = holder.getLayoutPosition();
                notifyItemChanged(index);

                Context context = view.getContext();
                Intent intent = new Intent(context, HouseActivity.class);
                intent.putExtra("clientId", clientId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.v(ClientRecyclerAdapter.class.getSimpleName(),""+list.size());
        return list.size();
    }


    /**
     * ViewHolder class
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView clientName;
        public TextView location;
        public ImageView callImageView;
        public ImageView emailImageView;

        public ViewHolder(View view) {
            super(view);
            clientName = view.findViewById(R.id.clientName);
            location = view.findViewById(R.id.clientLocation);
            callImageView = view.findViewById(R.id.callImageView);
            emailImageView = view.findViewById(R.id.mailImageView);
        }
    }
}
