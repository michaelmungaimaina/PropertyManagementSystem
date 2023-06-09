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
import com.mich.gwan.rms.databinding.HouseRecyclerBinding;
import com.mich.gwan.rms.models.Client;
import com.mich.gwan.rms.models.House;

import java.util.List;

public class HouseRecyclerAdapter extends RecyclerView.Adapter<HouseRecyclerAdapter.ViewHolder> {
    private final List<House> list;
    private int index = RecyclerView.NO_POSITION;

    public HouseRecyclerAdapter(List<House> list) {
        this.list = list;
    }

    @Override
    public HouseRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) throws InflateException {
        // inflating recycler item view
        HouseRecyclerBinding binding = HouseRecyclerBinding.inflate(LayoutInflater.from(parent.getContext()));

        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(HouseRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //String clientId = String.valueOf(list.get(position).getHouseId());
        holder.houseName.setText(list.get(position).getHouseName());
        holder.houseLocation.setText(list.get(position).getLocation());
        holder.houseType.setText(list.get(position).getHouseType());

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
                intent.putExtra("houseId", list.get(position).getHouseId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.v(HouseRecyclerAdapter.class.getSimpleName(), "" + list.size());
        return list.size();
    }


    /**
     * ViewHolder class
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView houseName;
        public TextView houseLocation;
        public TextView houseType;
        public ViewHolder(HouseRecyclerBinding binding) {
            super(binding.getRoot());
            houseName = binding.houseName;
            houseLocation = binding.houseLocation;
            houseType = binding.textViewHouseType;

            // houseName = view.findViewById(R.id.clientName);
            // houseLocation = view.findViewById(R.id.clientLocation);
            // houseType = view.findViewById(R.id.callImageView);
        }
    }
}
