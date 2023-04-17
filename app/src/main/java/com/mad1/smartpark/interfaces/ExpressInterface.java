package com.mad1.smartpark.interfaces;

import com.mad1.smartpark.model.Booking;
import com.mad1.smartpark.model.Driver;
import com.mad1.smartpark.model.Vehicle;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ExpressInterface {

    @GET("LoginDriver")
    Call<Driver> loginDriver(@Query("loginEmail") String email, @Query("loginPassword") String password);

    @POST("registerUser")
    Call<Integer> registerUser(@Query("firstName") String fName, @Query("lastName")String lName,
                              @Query("email") String email, @Query("phone")String phone,
                              @Query("password")String password);

    @POST("updateUser")
    Call<Driver> updateDriver(@Query("driver_id") int driverId, @Query("firstName") String fName, @Query("lastName") String lName,
                        @Query("email") String email, @Query("phone") String phone);

    @POST("addVehicle")
    Call<Vehicle> addVehicle(@Query("registration") String registration,
                             @Query("colour") String colour, @Query("model") String model,
                             @Query("brand") String brand, @Query("driverId") int driverId);

    @POST("userVehicles")
    Call<List<Vehicle>> getVehicle(@Query("driverId") int driverId);

    @POST("getVehicle")
    Call<Vehicle> getVehicleById(@Query("vehicle_id") int vehicleId);

    @DELETE("deleteVehicle")
    Call<Vehicle> deleteVehicle(@Query("vehicle_id") int vehicleId);

    @PUT("updateVehicle")
    Call<Vehicle> updateVehicle(@Query("vehicle_id") int vehicleId,
                                @Query("registration") String registration,
                                @Query("colour") String colour, @Query("model") String model,
                                @Query("brand") String brand);

    @POST("driverBookings")
    Call<List<Booking>> getBookings(@Query("driver_id") int driverId);

}
