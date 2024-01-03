package com.sr182022.travelagencystar.model;

import java.time.LocalDateTime;

public class Reservation {
    private int reservationId;
    private Travel travel;
    private User user;
    private int passengers;
    private float price;
    private LocalDateTime createdAt;

    public Reservation(int reservationId, Travel travel, User user, int passengers, float price, LocalDateTime createdAt) {
        this.reservationId = reservationId;
        this.travel = travel;
        this.user = user;
        this.passengers = passengers;
        this.price = price;
        this.createdAt = createdAt;
    }

    public Reservation(Travel travel, User user, int passengers, LocalDateTime createdAt) {
        this.travel = travel;
        this.user = user;
        this.passengers = passengers;
        this.createdAt = createdAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
