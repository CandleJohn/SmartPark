package com.mad1.smartpark.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.mad1.smartpark.R;
import com.mad1.smartpark.interfaces.ExpressInterface;
import com.mad1.smartpark.model.Vehicle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddVehicle extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    EditText registrationEditText, colourEditText, brandEditText, modelEditText;
    SharedPreferences sharedPref;
    int driver_id;
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
                        startActivity(new Intent(AddVehicle.this, MyVehicles.class));
                        break;
                    case R.id.action_spaces:
                        startActivity(new Intent(AddVehicle.this, SpaceAvailability.class));
                        break;
                    case R.id.action_update_driver:
                        startActivity(new Intent(AddVehicle.this, UpdateDriver.class));
                        break;
                    case R.id.action_bookings:
                        startActivity(new Intent(AddVehicle.this, DriverBookings.class));
                        break;
                    case R.id.action_logout:
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.clear();
                        editor.apply();
                        startActivity(new Intent(AddVehicle.this, MainActivity.class));
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
        setContentView(R.layout.activity_add_vehicle);
        initNavigationDrawer();
        sharedPref = getSharedPreferences("sharedValues", Context.MODE_PRIVATE);
        driver_id = sharedPref.getInt("driver_id", 0);
        registrationEditText = findViewById(R.id.registrationEditText);
        colourEditText = findViewById(R.id.colourEditText);
        brandEditText = findViewById(R.id.brandEditText);
        modelEditText = findViewById(R.id.modelEditText);
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void add_vehicle(View view){
        String reg = registrationEditText.getText().toString();
        String brand = brandEditText.getText().toString();
        String model = modelEditText.getText().toString();
        String colour = colourEditText.getText().toString();
        if(!reg.equals("")) {
            Call<Vehicle> call = api.addVehicle(reg, colour, model, brand, driver_id);
            call.enqueue(new Callback<Vehicle>() {
                @Override
                public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                    Toast.makeText(AddVehicle.this, "Vehicle Added", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddVehicle.this, MyVehicles.class));
                }

                @Override
                public void onFailure(Call<Vehicle> call, Throwable t) {
                    Toast.makeText(AddVehicle.this, "Connection Error. Check your internet connection.", Toast.LENGTH_SHORT).show();
                }
            });
        } else{
            Toast.makeText(AddVehicle.this, "A registration is required to add a vehicle.", Toast.LENGTH_SHORT).show();
        }

    }
}