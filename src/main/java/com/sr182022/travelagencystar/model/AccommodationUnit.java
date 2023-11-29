package com.sr182022.travelagencystar.model;
import java.util.*;

public class AccommodationUnit {
    private int id;
    private String name;
    private int capacity;
    private List<Review> reviews;
    private String description;
    private Destination destination;
    private List<Service> services;
    private AccommodationType accommodationType;

    public AccommodationUnit() {}
    public AccommodationUnit(int id, String name, int capacity, String description, List<Review> reviews,  Destination destination, List<Service> services,
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

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
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

    public String toFileString() {
        return this.getId() + "|" + this.getName() + "|" + this.getCapacity() + "|" + this.getDescription() + "|" + this.getDestination().getId() + "|" + this.getServices() + "|"
                + this.getAccommodationType().toString();
    }
}