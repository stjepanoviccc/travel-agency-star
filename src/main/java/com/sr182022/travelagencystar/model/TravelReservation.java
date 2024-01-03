package com.sr182022.travelagencystar.model;

public class TravelReservation {
    private int tr_id;
    private int id_travel;
    private int id_reservation;
    private int totalSpace;
    private int soldSpace;
    private int availableSpace;

    public TravelReservation(int tr_id, int id_travel, int id_reservation, int totalSpace, int soldSpace, int availableSpace) {
        this.tr_id = tr_id;
        this.id_travel = id_travel;
        this.id_reservation = id_reservation;
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

    public int getId_travel() {
        return id_travel;
    }

    public void setId_travel(int id_travel) {
        this.id_travel = id_travel;
    }

    public int getId_reservation() {
        return id_reservation;
    }

    public void setId_reservation(int id_reservation) {
        this.id_reservation = id_reservation;
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