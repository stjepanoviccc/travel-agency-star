package com.sr182022.travelagencystar.service;

import com.sr182022.travelagencystar.model.Reservation;

import java.util.List;

public interface IReservationService {
    List<Reservation> findAll();
    Reservation findOne(int travelId, int userId);
    void delete(int travelId, int userId);
}
