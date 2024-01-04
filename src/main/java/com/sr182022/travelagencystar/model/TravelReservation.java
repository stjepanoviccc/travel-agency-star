package com.sr182022.travelagencystar.model;

public class TravelReservation {
    private int tr_id;
    private Travel travel;
    private int totalSpace;
    private int soldSpace;
    private int availableSpace;

    public TravelReservation(int soldSpace, int availableSpace, int tr_id) {
        this.soldSpace = soldSpace;
        this.availableSpace = availableSpace;
        this.tr_id = tr_id;
    }

    public TravelReservation(Travel travel, int totalSpace, int soldSpace, int availableSpace) {
        this.travel = travel;
        this.totalSpace = totalSpace;
        this.soldSpace = soldSpace;
        this.availableSpace = availableSpace;
    }

    public TravelReservation(int tr_id, Travel travel, int totalSpace, int soldSpace, int availableSpace) {
        this.tr_id = tr_id;
        this.travel = travel;
        this.totalSpace = totalSpace;
        this.soldSpace =soldSpace;
        this.availableSpace = availableSpace;
    }

    public int getTr_id() {
        return tr_id;
    }

    public void setTr_id(int tr_id) {
        this.tr_id = tr_id;
    }

    public Travel getTravel() {
        return travel;
    }

    public void setTravel(Travel travel) {
        this.travel = travel;
    }

    public int getTotalSpace() {
        return totalSpace;
    }

    public void setTotalSpace(int totalSpace) {
        this.totalSpace = totalSpace;
    }

    public int getSoldSpace() {
        return soldSpace;
    }

    public void setSoldSpace(int soldSpace) {
        this.soldSpace = soldSpace;
    }

    public int getAvailableSpace() {
        return availableSpace;
    }

    public void setAvailableSpace(int availableSpace) {
        this.availableSpace = availableSpace;
    }
}