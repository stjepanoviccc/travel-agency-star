package com.sr182022.travelagencystar.model;

import java.time.LocalDateTime;

public class CartItem {
    private LocalDateTime cartItemId;
    private Travel travel;
    private int passengers;

    public CartItem() {

    }

    public CartItem(LocalDateTime cartItemId, Travel travel, int passengers) {
        this.cartItemId = cartItemId;
        this.travel = travel;
        this.passengers = passengers;
    }

    public LocalDateTime getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(LocalDateTime cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Travel getTravel() {
        return travel;
    }

    public void setTravel(Travel travel) {
        this.travel = travel;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }
}
