package com.sr182022.travelagencystar.service;

import com.sr182022.travelagencystar.model.TravelReservation;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface ITravelReservation {
    List<TravelReservation> findAll();
    List<TravelReservation> findAll(int travelId);
    void save(TravelReservation tr);
    void update(TravelReservation tr);
    boolean passengerValidation(HttpSession session);
    void createTravelReservation(HttpSession session);
}
