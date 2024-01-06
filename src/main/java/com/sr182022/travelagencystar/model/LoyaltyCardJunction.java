package com.sr182022.travelagencystar.model;

public class LoyaltyCardJunction {
    private int id;
    private int id_junction;
    private int points;

    public LoyaltyCardJunction(int id, int id_junction, int points) {
        this.id = id;
        this.id_junction = id_junction;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_junction() {
        return id_junction;
    }

    public void setId_junction(int id_junction) {
        this.id_junction = id_junction;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
