package com.mad1.smartpark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "HEREEEEEEEEEE";
    private EditText emailEditText, passwordEditText;
    SharedPreferences sharedPref;
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
                startActivity(new Intent(MainActivity.this, MyVehicles.class));
                return true;
            case R.id.action_spaces:
                startActivity(new Intent(MainActivity.this, SpaceAvailability.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);


    }

    public void login(View view) {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        Call<Driver> call = api.loginDriver(email,password);
        call.enqueue(new Callback<Driver>() {

            @Override
            public void onResponse(Call<Driver> call, Response<Driver> response) {
                if(response.isSuccessful()){
                    Driver driver = response.body();
                    sharedPref = getSharedPreferences("sharedValues", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("driver_id", driver.getDriverId());
                    editor.putString("fName", driver.getFirst_name());
                    editor.putString("lName", driver.getLast_name());
                    editor.putString("email", driver.getEmail());
                    editor.putString("phone", driver.getPhone());
                    editor.putString("password", driver.getPassword());
                    editor.apply();
                    Toast.makeText(MainActivity.this, "Login Successful as "+driver.getFirst_name()+" "+ driver.getLast_name(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, SpaceAvailability.class));
                } else {
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Driver> call, Throwable t) {
                Log.d(TAG, t.toString());
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void register(View view){
        startActivity(new Intent(MainActivity.this, RegisterDriver.class));
    }


}