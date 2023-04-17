package com.mad1.smartpark.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.mad1.smartpark.R;
import com.mad1.smartpark.interfaces.ExpressInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterDriver extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    EditText firstNameEditText, lastNameEditText, emailEditText, phoneEditText,
            passwordEditText, passConfirmEditText;
    Button driverFormButton;
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
                        startActivity(new Intent(RegisterDriver.this, MyVehicles.class));
                        break;
                    case R.id.action_spaces:
                        startActivity(new Intent(RegisterDriver.this, SpaceAvailability.class));
                        break;
                    case R.id.action_update_driver:
                        startActivity(new Intent(RegisterDriver.this, UpdateDriver.class));
                        break;
                    case R.id.action_bookings:
                        startActivity(new Intent(RegisterDriver.this, DriverBookings.class));
                        break;
                    case R.id.action_logout:
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.clear();
                        editor.apply();
                        startActivity(new Intent(RegisterDriver.this, MainActivity.class));
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
        setContentView(R.layout.activity_register_driver);
        initNavigationDrawer();
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPref = getSharedPreferences("sharedValues", Context.MODE_PRIVATE);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passConfirmEditText = findViewById(R.id.passConfirmEditText);
        driverFormButton = findViewById(R.id.driverFormButton);

        driverFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passwordEditText.getText().toString().equals(passConfirmEditText.getText().toString())) {
                    registerUser();
                } else {
                    Toast.makeText(RegisterDriver.this, "Passwords must match", Toast.LENGTH_SHORT).show();
                    passConfirmEditText.setBackgroundColor(Color.RED);
                }
            }
        });
    }

    private void registerUser() {
        String fName, lName, email, phone, password;
        fName = firstNameEditText.getText().toString();
        lName = lastNameEditText.getText().toString();
        email = emailEditText.getText().toString();
        phone = phoneEditText.getText().toString();
        password = passwordEditText.getText().toString();

        Call<Integer> call = api.registerUser(fName,lName,email,phone,password);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                int id = response.body();
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("driver_id", id);
                editor.putString("fName", fName);
                editor.putString("lName", lName);
                editor.putString("email", email);
                editor.putString("phone", phone);
                editor.putString("password", password);
                editor.apply();
                Toast.makeText(RegisterDriver.this, "Registered now add a vehicle", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterDriver.this, MyVehicles.class));
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(RegisterDriver.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}