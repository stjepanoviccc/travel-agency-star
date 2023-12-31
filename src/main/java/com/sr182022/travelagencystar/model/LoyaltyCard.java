package com.sr182022.travelagencystar.model;

public class LoyaltyCard {
    private int id;
    private int points;
    private int userId;
    private boolean activated;

    public LoyaltyCard(int id) {
        this.id = id;
    }
    public LoyaltyCard(int id, int points, int userId, boolean activated) {
        this.id = id;
        this.points = points;
        this.userId = userId;
        this.activated = activated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}