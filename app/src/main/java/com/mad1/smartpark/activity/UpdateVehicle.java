package com.mad1.smartpark.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class UpdateVehicle extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    EditText registrationEditText, colourEditText, brandEditText, modelEditText;
    TextView vehicleFormTextView;
    Button vehicleFormButton;
    SharedPreferences sharedPref;
    int driver_id, vehicle_id;
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
                        startActivity(new Intent(UpdateVehicle.this, MyVehicles.class));
                        break;
                    case R.id.action_spaces:
                        startActivity(new Intent(UpdateVehicle.this, SpaceAvailability.class));
                        break;
                    case R.id.action_update_driver:
                        startActivity(new Intent(UpdateVehicle.this, UpdateDriver.class));
                        break;
                    case R.id.action_bookings:
                        startActivity(new Intent(UpdateVehicle.this, DriverBookings.class));
                        break;
                    case R.id.action_logout:
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.clear();
                        editor.apply();
                        startActivity(new Intent(UpdateVehicle.this, MainActivity.class));
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
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPref = getSharedPreferences("sharedValues", Context.MODE_PRIVATE);
        driver_id = sharedPref.getInt("driver_id", 0);
        vehicle_id = getIntent().getExtras().getInt("vehicleId");

        vehicleFormTextView = findViewById(R.id.vehicleFormTextView);
        registrationEditText = findViewById(R.id.registrationEditText);
        colourEditText = findViewById(R.id.colourEditText);
        brandEditText = findViewById(R.id.brandEditText);
        modelEditText = findViewById(R.id.modelEditText);
        vehicleFormButton = findViewById(R.id.vehicleFormButton);
        Log.d("HEEEEEEERERERER", ""+vehicle_id);

        vehicleFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateVehicle(vehicle_id);
                startActivity(new Intent(UpdateVehicle.this, MyVehicles.class));
            }
        });

        Call<Vehicle> call = api.getVehicleById(vehicle_id);
        call.enqueue(new Callback<Vehicle>() {
            @Override
            public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                Vehicle vehicle = response.body();
                Log.d("HERERERERERE", vehicle.getRegistration());
                vehicleFormTextView.setText("Update Vehicle");
                registrationEditText.setText(vehicle.getRegistration());
                colourEditText.setText(vehicle.getColour());
                brandEditText.setText(vehicle.getBrand());
                modelEditText.setText(vehicle.getModel());
                vehicleFormButton.setText("Update Vehicle");
            }

            @Override
            public void onFailure(Call<Vehicle> call, Throwable t) {
                Toast.makeText(UpdateVehicle.this,"Connection Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateVehicle(int vehicle_id) {

        Call<Vehicle> call = api.updateVehicle(vehicle_id, registrationEditText.getText().toString(),
                colourEditText.getText().toString(), modelEditText.getText().toString(),
                brandEditText.getText().toString());
        call.enqueue(new Callback<Vehicle>() {
            @Override
            public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                Toast.makeText(UpdateVehicle.this, "Vehicle Updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Vehicle> call, Throwable t) {
                Toast.makeText(UpdateVehicle.this, "Update failed. Check connection", Toast.LENGTH_SHORT).show();
            }
        });

    }
}