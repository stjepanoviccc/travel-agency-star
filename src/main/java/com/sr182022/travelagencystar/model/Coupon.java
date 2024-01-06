package com.sr182022.travelagencystar.model;

import java.time.LocalDate;

public class Coupon {
    private int id;
    private Travel travel;
    private boolean Winter;
    private boolean Summer;
    private boolean Last_min;
    private boolean New_year;
    private LocalDate startDate;
    private LocalDate endDate;
    private Float discount;

    public Coupon() {};

    public Coupon(LocalDate startDate, LocalDate endDate, float discount) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.discount = discount;
    }

    public Coupon(int id, Travel travel, boolean Winter, boolean Summer, boolean Last_min, boolean New_year, LocalDate startDate, LocalDate endDate, float discount) {
        this.id = id;
        this.travel = travel;
        this.Winter = Winter;
        this.Summer = Summer;
        this.Last_min = Last_min;
        this.New_year = New_year;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Travel getTravel() {
        return travel;
    }

    public void setTravel(Travel travel) {
        this.travel = travel;
    }

    public boolean isWinter() {
        return Winter;
    }

    public void setWinter(boolean winter) {
        Winter = winter;
    }

    public boolean isSummer() {
        return Summer;
    }

    public void setSummer(boolean summer) {
        Summer = summer;
    }

    public boolean isLast_min() {
        return Last_min;
    }

    public void setLast_min(boolean last_min) {
        Last_min = last_min;
    }

    public boolean isNew_year() {
        return New_year;
    }

    public void setNew_year(boolean new_year) {
        New_year = new_year;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }
}
