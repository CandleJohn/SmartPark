package com.mad1.smartpark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyVehicles extends AppCompatActivity {

    Context context;
    VehicleAdapter adapter;
    private Button addVehicleButton;
    SharedPreferences savedValues;
    ArrayList<Vehicle> list;
    RecyclerView recyclerView;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5002/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ExpressInterface api = retrofit.create(ExpressInterface.class);

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_vehicles:
                startActivity(new Intent(MyVehicles.this, MyVehicles.class));
                return true;
            case R.id.action_spaces:
                startActivity(new Intent(MyVehicles.this, SpaceAvailability.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vehicles);

        recyclerView = findViewById(R.id.rvVehicles);
        list = new ArrayList<>();
        savedValues = getSharedPreferences("sharedValues", Context.MODE_PRIVATE);
        int driver_id = savedValues.getInt("driver_id", 0);
        adapter = new VehicleAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setVehicles(list);
        recyclerView.setAdapter(adapter);
        Call<List<Vehicle>> call = api.getVehicle(driver_id);
        call.enqueue(new Callback<List<Vehicle>>() {
            @Override
            public void onResponse(Call<List<Vehicle>> call, Response<List<Vehicle>> response) {
                if(response.isSuccessful()){
                    List<Vehicle> list = response.body();
                    adapter.setVehicles(list);
                    adapter.notifyDataSetChanged();
                } else{
                    Toast.makeText(MyVehicles.this, "No data for "+ driver_id, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Vehicle>> call, Throwable t) {
                Log.d("HERERERERERER", t.toString());
            }
        });

    }

    public void addVehicle(View view){
        startActivity(new Intent(MyVehicles.this, AddVehicle.class));
    }
}