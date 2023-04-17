package com.mad1.smartpark.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ParkingSpace {
    private String name;
    private String registration;
    private boolean occupied;

    public ParkingSpace(){
    }

    public ParkingSpace(String name, String registration, boolean occupied) {
        this.name = name;
        this.registration = registration;
        this.occupied = occupied;
    }

    public String getName() {
        return name;
    }

    public String getRegistration() {
        return registration;
    }

    public boolean isOccupied() {
        return occupied;
    }
}
