package com.mad1.smartpark;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateVehicle extends AppCompatActivity {

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
                startActivity(new Intent(UpdateVehicle.this, MyVehicles.class));
                return true;
            case R.id.action_spaces:
                startActivity(new Intent(UpdateVehicle.this, SpaceAvailability.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

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
                Log.d("HERERERERER", "errer", t);
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

            }

            @Override
            public void onFailure(Call<Vehicle> call, Throwable t) {

            }
        });

    }
}