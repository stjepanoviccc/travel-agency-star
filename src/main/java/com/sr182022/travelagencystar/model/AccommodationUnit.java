package com.sr182022.travelagencystar.model;
import java.util.*;

public class AccommodationUnit {
    private int id;
    private String name;
    private int capacity;
    private String description;
    private Destination destination;
    private AccommodationType accommodationType;
    private boolean wifi;
    private boolean bathroom;
    private boolean tv;
    private boolean conditioner;
    private List<Review> reviews = new ArrayList<>();

    public AccommodationUnit() {}
    public AccommodationUnit(int id, String name, int capacity, AccommodationType accommodationType,
                             boolean wifi, boolean bathroom, boolean tv, boolean conditioner, Destination destination, String description) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.description = description;
        this.accommodationType = accommodationType;
        this.wifi = wifi;
        this.bathroom = bathroom;
        this.tv = tv;
        this.conditioner = conditioner;
        this.destination = destination;
    }

    public AccommodationUnit(List<Review> reviews) { this.reviews = reviews; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public AccommodationType getAccommodationType() {
        return accommodationType;
    }

    public void setAccommodationType(AccommodationType accommodationType) {
        this.accommodationType = accommodationType;
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setHasWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public boolean isBathroom() {
        return bathroom;
    }

    public void setHasBathroom(boolean bathroom) {
        this.bathroom = bathroom;
    }

    public boolean isTv() {
        return tv;
    }

    public void setHasTv(boolean tv) {
        this.tv = tv;
    }

    public boolean isConditioner() {
        return conditioner;
    }

    public void setHasConditioner(boolean conditioner) {
        this.conditioner = conditioner;
    }

    public String toFileString() {
        return this.getId() + "|" + this.getName() + "|" + this.getCapacity() + "|" + this.getDescription() + "|" + this.getDestination().getId() + "|"
                + this.getAccommodationType().toString() + "|" + this.isWifi() + "|" + this.isBathroom() + "|" + this.isTv() + "|" + this.isConditioner();
    }
}