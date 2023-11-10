package com.sr182022.travelagencystar.model;

public class LoyaltyCard {
    private int id;
    private float discount;
    private int points;

    public LoyaltyCard(int id, float discount, int points) {
        this.id = id;
        this.discount = discount;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}