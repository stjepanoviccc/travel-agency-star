package com.sr182022.travelagencystar.service;

import com.sr182022.travelagencystar.model.CartItem;
import com.sr182022.travelagencystar.model.Reservation;
import com.sr182022.travelagencystar.model.User;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface IReservationService {
    List<Reservation> findAll();
    Reservation findOne(int travelId, int userId);
    void save(Reservation res);
    void delete(int travelId, int userId);
    boolean validateReservation(HttpSession session);
    List<Reservation> createReservation(HttpSession session);
}
