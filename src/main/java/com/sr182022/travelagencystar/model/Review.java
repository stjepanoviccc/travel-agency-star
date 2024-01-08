package com.sr182022.travelagencystar.model;

public class Review {

    private int id;
    private String message;
    private int stars;
    private User user;
    private AccommodationUnit accommodationUnit;

    public Review() {}

    public Review(int id, String message, int stars, User user, AccommodationUnit accommodationUnit ) {
        this.id = id;
        this.message = message;
        this.stars = stars;
        this.user = user;
        this.accommodationUnit = accommodationUnit;
    }

    public Review(String message, int stars, User user, AccommodationUnit accommodationUnit) {
        this.message = message;
        this.stars = stars;
        this.user = user;
        this.accommodationUnit = accommodationUnit;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public AccommodationUnit getAccommodationUnit() { return accommodationUnit; }

    public void setAccommodationUnitId(AccommodationUnit accommodationUnit) { this.accommodationUnit = accommodationUnit; }

    public User getCreator() {
        return user;
    }

    public void setCreator(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}