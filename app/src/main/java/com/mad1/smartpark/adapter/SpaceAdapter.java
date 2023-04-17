package com.mad1.smartpark.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mad1.smartpark.R;
import com.mad1.smartpark.model.ParkingSpace;

import java.util.ArrayList;

public class SpaceAdapter extends RecyclerView.Adapter<SpaceAdapter.SpaceViewHolder> {

    Context context;
    ArrayList<ParkingSpace> list;

    public SpaceAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<ParkingSpace> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public SpaceAdapter.SpaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.spaceentry, parent, false);
        return new SpaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpaceAdapter.SpaceViewHolder holder, int position) {
        ParkingSpace space = list.get(position);
        holder.regTextView.setText(space.getRegistration());
        holder.nameTextView.setText(space.getName());
        if(space.isOccupied()){
            holder.itemView.setBackgroundColor(Color.parseColor("#E180AA"));
        }else {
            holder.itemView.setBackgroundColor(Color.parseColor("#80E1B6"));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class SpaceViewHolder extends RecyclerView.ViewHolder {
        TextView regTextView, nameTextView;
        public SpaceViewHolder(@NonNull View itemView) {
            super(itemView);
            regTextView = itemView.findViewById(R.id.regTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
        }
    }
}
