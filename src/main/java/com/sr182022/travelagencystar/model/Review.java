package com.sr182022.travelagencystar.model;

public class Review {

    private int id;
    private String message;
    private int stars;
    private int userId;
    private int accommodationUnitId;

    public Review() {}

    public Review(int id, String message, int stars, int userId, int accommodationUnitId ) {
        this.id = id;
        this.message = message;
        this.stars = stars;
        this.userId = userId;
        this.accommodationUnitId = accommodationUnitId;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccommodationUnitId() { return accommodationUnitId; }

    public void setAccommodationUnitId(int accommodationUnitId) { this.accommodationUnitId = accommodationUnitId; }

    public int getCreator() {
        return userId;
    }

    public void setCreator(int userId) {
        this.userId = userId;
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