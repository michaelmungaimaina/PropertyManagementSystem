/*
 * Copyright (c) 2023. Maina Michael.
 */

package com.mich.gwan.rms.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mich.gwan.rms.R;
import com.mich.gwan.rms.models.Tenant;

import java.util.List;

public class AssignTenantAdapter extends RecyclerView.Adapter<AssignTenantAdapter.ViewHolder>{
    private final List<Tenant> list;
    private int index = RecyclerView.NO_POSITION;
    private OnItemClickListener onItemClickListener;


    public void setOnItemClickListener (OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface  OnItemClickListener{
        void onClick(int position);
    }

    public AssignTenantAdapter(List<Tenant> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public AssignTenantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tenant_recycler, parent, false);

        return new AssignTenantAdapter.ViewHolder(itemView, onItemClickListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(AssignTenantAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        char firstChar = list.get(position).getTenantFirstName().charAt(0);
        holder.tenantName.setText(list.get(position).getTenantFirstName() + " " +list.get(position).getTenantLastName());
        holder.textInitial.setText(String.valueOf(firstChar));

        holder.itemView.setSelected(index == position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                index = holder.getLayoutPosition();
                notifyItemChanged(index);
                notifyDataSetChanged();
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = holder.getLayoutPosition();
                notifyItemChanged(index);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.v(AssignTenantAdapter.class.getSimpleName(),""+list.size());
        return list.size();
    }


    /**
     * ViewHolder class
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tenantName;
        public TextView textInitial;
        public CardView inner;
        public CardView outer;

        public ViewHolder(View view, OnItemClickListener onItemClickListener) {
            super(view);
            tenantName = view.findViewById(R.id.tenantName);
            textInitial = view.findViewById(R.id.textInitial);
            inner = view.findViewById(R.id.cardInitialInner);
            outer = view.findViewById(R.id.cardInitialOuter);

            tenantName.setOnClickListener(v -> onItemClickListener.onClick(getAdapterPosition()));
        }
    }
}

