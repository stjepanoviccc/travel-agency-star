package com.sr182022.travelagencystar.model;

public class Review {
    private int id;
    private int accommodationUnitId;
    private User creator;
    private String message;
    private int stars;

    public Review() {}

    public Review(int id, int accommodationUnitId, User creator, String message, int stars) {
        this.id = id;
        this.accommodationUnitId = accommodationUnitId;
        this.creator = creator;
        this.message = message;
        this.stars = stars;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccommodationUnitId() { return accommodationUnitId; }

    public void setAccommodationUnitId(int accommodationUnitId) { this.accommodationUnitId = accommodationUnitId; }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
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

    public String toFileString() {
        return this.getId() + "|" + this.getAccommodationUnitId() + "|" + this.getCreator().getId() + "|" + this.getMessage() + "|" + this.getStars();
    }

}