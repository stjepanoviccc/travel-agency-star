package com.sr182022.travelagencystar.model;

public class Vehicle {
    private int id;
    private int numberOfSeats;
    private Destination finalDestination;
    private String description;
    private VehicleType vehicleType;

    public Vehicle() {

    }

    public Vehicle(int id, int numberOfSeats, Destination finalDestination, String description, VehicleType vehicleType) {
        this.id = id;
        this.numberOfSeats = numberOfSeats;
        this.finalDestination = finalDestination;
        this.description = description;
        this.vehicleType = vehicleType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Destination getFinalDestination() {
        return finalDestination;
    }

    public void setFinalDestination(Destination finalDestination) {
        this.finalDestination = finalDestination;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }
}