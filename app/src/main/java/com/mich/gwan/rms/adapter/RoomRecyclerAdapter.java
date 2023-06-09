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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mich.gwan.rms.R;
import com.mich.gwan.rms.activities.HouseActivity;
import com.mich.gwan.rms.dao.DatabaseHelper;
import com.mich.gwan.rms.databinding.RoomRecyclerBinding;
import com.mich.gwan.rms.models.Room;

import java.util.List;
import java.util.Objects;

public class RoomRecyclerAdapter extends RecyclerView.Adapter<RoomRecyclerAdapter.ViewHolder> {
    private final List<Room> list;
    private int index = RecyclerView.NO_POSITION;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener (OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener{
        void onClick(int position);
    }
    public RoomRecyclerAdapter(List<Room> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RoomRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        //RoomRecyclerBinding binding = RoomRecyclerBinding.inflate(LayoutInflater.from(parent.getContext()));
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_recycler, parent, false);
        context = parent.getContext();

        return new RoomRecyclerAdapter.ViewHolder(view,onItemClickListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RoomRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textViewDeposit.setText(list.get(position).getDeposit());
        holder.textViewRent.setText(list.get(position).getRent());
        holder.textViewStatus.setText(list.get(position).getStatus());

        DatabaseHelper db = new DatabaseHelper(context);

        if (db.getRoomStatus(list.get(position).getHouseId(),list.get(position).getRoomId()).equals("VACANT")){
            holder.layoutCompat.setBackgroundColor(ContextCompat.getColor(context, R.color.color_vacant));
            holder.cardViewAssignTenant.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color_not_vacant));
            holder.cardViewAssignTenantInner.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color_vacant));
        } else {
            holder.layoutCompat.setBackgroundColor(ContextCompat.getColor(context, R.color.color_not_vacant));
            holder.cardViewAssignTenant.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color_vacant));
            holder.cardViewAssignTenantInner.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color_not_vacant));
        }

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
    }

    @Override
    public int getItemCount() {
        Log.v(RoomRecyclerAdapter.class.getSimpleName(), "" + list.size());
        return list.size();
    }


    /**
     * ViewHolder class
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewDeposit;
        public TextView textViewRent;
        public TextView textViewStatus;
        public TextView textViewAssignTenant;
        public CardView cardViewAssignTenant;
        public CardView cardViewAssignTenantInner;
        public LinearLayoutCompat layoutCompat;
        public ViewHolder(View view, OnItemClickListener onItemClickListener) {
            super(view);
            textViewDeposit = view.findViewById(R.id.textViewRoomDeposit);
            textViewRent = view.findViewById(R.id.textViewRoomRent);
            textViewStatus = view.findViewById(R.id.textViewRoomStatus);
            textViewAssignTenant = view.findViewById(R.id.textViewAssignTenant);
            cardViewAssignTenant = view.findViewById(R.id.cardViewAssignTenant);
            cardViewAssignTenantInner = view.findViewById(R.id.cardViewAssignTenantInner);
            layoutCompat = view.findViewById(R.id.roomRecyclerLayout);

            cardViewAssignTenantInner.setOnClickListener(v -> onItemClickListener.onClick(getAdapterPosition()));
        }
    }
}
