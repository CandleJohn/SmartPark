package com.mad1.smartpark.model;

import com.google.gson.annotations.SerializedName;

public class Vehicle {
    @SerializedName("vehicle_id")
    private int vehicle_id;
    @SerializedName("registration")
    private String registration;
    @SerializedName("brand")
    private String brand;
    @SerializedName("model")
    private String model;
    @SerializedName("colour")
    private String colour;
    @SerializedName("driver_id")
    private int driver_id;

    public Vehicle(int vehicle_id, String registration, String brand, String model, String colour, int driver_id) {
        this.vehicle_id = vehicle_id;
        this.registration = registration;
        this.brand = brand;
        this.model = model;
        this.colour = colour;
        this.driver_id = driver_id;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(int driver_id) {
        this.driver_id = driver_id;
    }
}
