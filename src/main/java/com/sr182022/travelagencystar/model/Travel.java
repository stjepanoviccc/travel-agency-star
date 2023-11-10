package com.sr182022.travelagencystar.model;
import java.time.LocalDateTime;

public class Travel {
    private int id;
    private Destination destination;
    private Vehicle vehicle;
    private AccommodationUnit accommodationUnit;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int numberOfNights;
    private TravelCategory travelCategory;

    private float price;
    public Travel(int id, Destination destination, Vehicle vehicle, AccommodationUnit accommodationUnit, LocalDateTime startDate, LocalDateTime endDate, int numberOfNights,
                  TravelCategory travelCategory) {
        this.id = id;
        this.destination = destination;
        this.vehicle = vehicle;
        this.accommodationUnit = accommodationUnit;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfNights = numberOfNights;
        this.travelCategory = travelCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public AccommodationUnit getAccommodationUnit() {
        return accommodationUnit;
    }

    public void setAccommodationUnit(AccommodationUnit accommodationUnit) {
        this.accommodationUnit = accommodationUnit;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public TravelCategory getTravelCategory() {
        return travelCategory;
    }

    public void setTravelCategory(TravelCategory travelCategory) {
        this.travelCategory = travelCategory;
    }
}