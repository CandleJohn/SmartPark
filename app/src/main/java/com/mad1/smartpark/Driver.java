package com.mad1.smartpark;

import com.google.gson.annotations.SerializedName;

public class Driver {
    @SerializedName("driver_id")
    int driverId;
    @SerializedName("first_name")
    String first_name;
    @SerializedName("last_name")
    String last_name;
    @SerializedName("email")
    String email;
    @SerializedName("phone")
    String phone;
    @SerializedName("password")
    String password;

    public Driver(int driverId, String first_name, String last_name,
                  String email, String password, String phone) {
        this.driverId = driverId;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
