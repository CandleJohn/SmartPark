package com.mad1.smartpark.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mad1.smartpark.R;
import com.mad1.smartpark.activity.UpdateVehicle;
import com.mad1.smartpark.interfaces.ExpressInterface;
import com.mad1.smartpark.model.Vehicle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder> {

    Context context;
    List<Vehicle> list;

    public VehicleAdapter(Context context){
        this.context = context;
    }

    public void setVehicles(List<Vehicle> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public VehicleAdapter.VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.vehicle, parent, false);
        return new VehicleViewHolder(view).vehicleAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleAdapter.VehicleViewHolder holder, int position) {
        Vehicle vehicle = list.get(position);
        holder.registrationTextView.setText(vehicle.getRegistration());
        holder.modelTextView.setText(vehicle.getModel());
        holder.brandTextView.setText(vehicle.getBrand());
        holder.colourTextView.setText(vehicle.getColour());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class VehicleViewHolder extends RecyclerView.ViewHolder {
        TextView registrationTextView, modelTextView, brandTextView, colourTextView;
        Button updateVehicleButton, deleteVehicleButton;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5002/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ExpressInterface api = retrofit.create(ExpressInterface.class);
        private VehicleAdapter adapter;

        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            registrationTextView = itemView.findViewById(R.id.registrationTextView);
            modelTextView = itemView.findViewById(R.id.modelTextView);
            brandTextView = itemView.findViewById(R.id.brandTextView);
            colourTextView = itemView.findViewById(R.id.colourTextView);
            itemView.findViewById(R.id.updateVehicleButton).setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), UpdateVehicle.class);
                intent.putExtra("vehicleId", adapter.list.get(getAdapterPosition()).getVehicle_id());
                itemView.getContext().startActivity(intent);
            });
            itemView.findViewById(R.id.deleteVehicleButton).setOnClickListener(view -> {
                Call<Vehicle> call = api.deleteVehicle(adapter.list.get(getAdapterPosition()).getVehicle_id());
                call.enqueue(new Callback<Vehicle>() {
                    @Override
                    public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                        adapter.list.remove(getAdapterPosition());
                        adapter.notifyItemRemoved(getAdapterPosition());
                    }

                    @Override
                    public void onFailure(Call<Vehicle> call, Throwable t) {
                        Log.d("Error: ", t.toString());
                    }
                });
            });
        }

        public VehicleViewHolder vehicleAdapter(VehicleAdapter adapter){
            this.adapter = adapter;
            return this;
        }
    }
}
