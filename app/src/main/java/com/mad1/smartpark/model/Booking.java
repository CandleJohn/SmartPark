package com.mad1.smartpark.model;

import com.google.gson.annotations.SerializedName;

public class Booking {
    @SerializedName("booking_id")
    int bookingId;
    @SerializedName("booking_date")
    String time;
    @SerializedName("booking_cost")
    double cost;
    @SerializedName("registration")
    String registration;

    public Booking(){
    }

    public Booking(int bookingId, String time, double cost, String registration) {
        this.bookingId = bookingId;
        this.time = time;
        this.cost = cost;
        this.registration = registration;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }
}
