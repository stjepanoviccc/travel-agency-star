package com.sr182022.travelagencystar.model;
import java.util.*;

public class AccommodationUnit {
    private int id;
    private String name;
    private int capacity;
    private ArrayList<Review> reviews = null;
    private String description;
    private Destination destination;
    private ArrayList<String> services;
    private AccommodationType accommodationType;

    public AccommodationUnit(int id, String name, int capacity, String description, ArrayList<Review> reviews,  Destination destination, ArrayList<String> services,
                             AccommodationType accommodationType) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.reviews = reviews;
        this.description = description;
        this.destination = destination;
        this.services = services;
        this.accommodationType = accommodationType;
    }

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

    public ArrayList<String> getServices() {
        return services;
    }

    public void setServices(ArrayList<String> services) {
        this.services = services;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
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
}