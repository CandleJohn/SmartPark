package com.mad1.smartpark.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.mad1.smartpark.R;
import com.mad1.smartpark.adapter.VehicleAdapter;
import com.mad1.smartpark.interfaces.ExpressInterface;
import com.mad1.smartpark.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyVehicles extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
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

    public void initNavigationDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_vehicles:
                        startActivity(new Intent(MyVehicles.this, MyVehicles.class));
                        break;
                    case R.id.action_spaces:
                        startActivity(new Intent(MyVehicles.this, SpaceAvailability.class));
                        break;
                    case R.id.action_update_driver:
                        startActivity(new Intent(MyVehicles.this, UpdateDriver.class));
                        break;
                    case R.id.action_bookings:
                        startActivity(new Intent(MyVehicles.this, DriverBookings.class));
                        break;
                    case R.id.action_logout:
                        SharedPreferences.Editor editor = savedValues.edit();
                        editor.clear();
                        editor.apply();
                        startActivity(new Intent(MyVehicles.this, MainActivity.class));
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vehicles);
        initNavigationDrawer();
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                    Toast.makeText(MyVehicles.this, "No data for your account: "+ driver_id, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Vehicle>> call, Throwable t) {
                Toast.makeText(MyVehicles.this, "Connection error.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void addVehicle(View view){
        startActivity(new Intent(MyVehicles.this, AddVehicle.class));
    }
}