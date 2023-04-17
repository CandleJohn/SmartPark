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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.mad1.smartpark.R;
import com.mad1.smartpark.interfaces.ExpressInterface;
import com.mad1.smartpark.model.Driver;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateDriver extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    EditText firstNameEditText, lastNameEditText, emailEditText, phoneEditText;
    Button updateDriverButton;
    SharedPreferences sharedPref;
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
                        startActivity(new Intent(UpdateDriver.this, MyVehicles.class));
                        break;
                    case R.id.action_spaces:
                        startActivity(new Intent(UpdateDriver.this, SpaceAvailability.class));
                        break;
                    case R.id.action_update_driver:
                        startActivity(new Intent(UpdateDriver.this, UpdateDriver.class));
                        break;
                    case R.id.action_bookings:
                        startActivity(new Intent(UpdateDriver.this, DriverBookings.class));
                        break;
                    case R.id.action_logout:
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.clear();
                        editor.apply();
                        startActivity(new Intent(UpdateDriver.this, MainActivity.class));
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
        setContentView(R.layout.activity_update_driver);
        initNavigationDrawer();
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPref = getSharedPreferences("sharedValues", Context.MODE_PRIVATE);
        String fName = sharedPref.getString("fName", "");
        String lName = sharedPref.getString("lName", "");
        String email = sharedPref.getString("email", "");
        String phone = sharedPref.getString("phone", "");

        firstNameEditText = findViewById(R.id.fNameEditText);
        lastNameEditText = findViewById(R.id.lNameEditText);
        emailEditText = findViewById(R.id.emailAddressEditText);
        phoneEditText = findViewById(R.id.phoneNumberEditText);
        updateDriverButton = findViewById(R.id.updateDriverFormButton);
        firstNameEditText.setText(fName);
        lastNameEditText.setText(lName);
        emailEditText.setText(email);
        phoneEditText.setText(phone);

        updateDriverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_driver();
            }
        });


    }

    public void update_driver(){
        String fName = firstNameEditText.getText().toString();
        String lName = lastNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        int id = sharedPref.getInt("driver_id", 0);

        Call<Driver> call = api.updateDriver(id, fName, lName, email, phone);
        call.enqueue(new Callback<Driver>() {
            @Override
            public void onResponse(Call<Driver> call, Response<Driver> response) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("fName", fName);
                editor.putString("lName", lName);
                editor.putString("email", email);
                editor.putString("phone", phone);
                editor.apply();
                startActivity(new Intent(UpdateDriver.this, SpaceAvailability.class));
                Toast.makeText(UpdateDriver.this, "Information Updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Driver> call, Throwable t) {
                Toast.makeText(UpdateDriver.this, "Update failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
}