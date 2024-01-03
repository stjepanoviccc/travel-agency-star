package com.sr182022.travelagencystar.model;

public class Reservation {
    private int reservationId;
    private Travel travel;
    private User user;
    private int passengers;
    private float price;

    public Reservation(int reservationId, Travel travel, User user, int passengers, float price) {
        this.reservationId = reservationId;
        this.travel = travel;
        this.user = user;
        this.passengers = passengers;
        this.price = price;
    }

    public Reservation(Travel travel, User user, int passengers) {
        this.travel = travel;
        this.user = user;
        this.passengers = passengers;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Travel getTravel() {
        return travel;
    }

    public void setTravel(Travel travel) {
        this.travel = travel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
