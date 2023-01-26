package com.mad1.smartpark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddVehicle extends AppCompatActivity {

    EditText registrationEditText, colourEditText, brandEditText, modelEditText;
    SharedPreferences sharedPref;
    int driver_id;
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
                startActivity(new Intent(AddVehicle.this, MyVehicles.class));
                return true;
            case R.id.action_spaces:
                startActivity(new Intent(AddVehicle.this, SpaceAvailability.class));
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
        registrationEditText = findViewById(R.id.registrationEditText);
        colourEditText = findViewById(R.id.colourEditText);
        brandEditText = findViewById(R.id.brandEditText);
        modelEditText = findViewById(R.id.modelEditText);


    }

    public void add_vehicle(View view){
        String reg = registrationEditText.getText().toString();
        String brand = brandEditText.getText().toString();
        String model = modelEditText.getText().toString();
        String colour = colourEditText.getText().toString();

        Call<Vehicle> call = api.addVehicle(reg, colour, model, brand, driver_id);
        call.enqueue(new Callback<Vehicle>() {
            @Override
            public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                Toast.makeText(AddVehicle.this, "Vehicle Added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddVehicle.this, MyVehicles.class));
            }

            @Override
            public void onFailure(Call<Vehicle> call, Throwable t) {

            }
        });

    }
}