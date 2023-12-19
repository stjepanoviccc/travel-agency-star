package com.sr182022.travelagencystar.model;

public class WishlistItem {

    private User user;
    private Travel travel;

    public WishlistItem(User user, Travel travel) {
        this.user = user;
        this.travel = travel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(int userId) {
        this.user = user;
    }

    public Travel getTravel() {
        return travel;
    }

    public void setTravel(int travelId) {
        this.travel = travel;
    }
}
