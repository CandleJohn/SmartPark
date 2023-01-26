package com.mad1.smartpark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterDriver extends AppCompatActivity {

    EditText firstNameEditText, lastNameEditText, emailEditText, phoneEditText,
            passwordEditText, passConfirmEditText;
    Button driverFormButton;
    SharedPreferences sharedPref;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5002/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ExpressInterface api = retrofit.create(ExpressInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_driver);

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
                Toast.makeText(RegisterDriver.this, "Registered now add a vehicle" + id, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterDriver.this, MyVehicles.class));
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(RegisterDriver.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}