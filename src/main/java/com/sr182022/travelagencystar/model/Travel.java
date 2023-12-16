package com.sr182022.travelagencystar.model;
import java.time.LocalDate;

public class Travel {
    private Integer id;
    private Destination destination;
    private Vehicle vehicle;
    private AccommodationUnit accommodationUnit;
    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfNights;
    private TravelCategory travelCategory;
    private float price;

    public Travel(Integer id, Destination destination, Vehicle vehicle, AccommodationUnit accommodationUnit, LocalDate startDate, LocalDate endDate, int numberOfNights,
                  TravelCategory travelCategory, float price) {
        this.id = id;
        this.destination = destination;
        this.vehicle = vehicle;
        this.accommodationUnit = accommodationUnit;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfNights = numberOfNights;
        this.travelCategory = travelCategory;
        this.price = price;
    }

    public Travel() {

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
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