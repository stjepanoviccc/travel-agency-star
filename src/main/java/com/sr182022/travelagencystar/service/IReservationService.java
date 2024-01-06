package com.sr182022.travelagencystar.service;

import com.sr182022.travelagencystar.model.Reservation;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface IReservationService {
    List<Reservation> findAll();
    List<Reservation> findAll(int userId);
    List<Reservation> findAll(boolean pending);
    Reservation findOne(int reservationId);
    Reservation findOne(int travelId, int userId);
    void save(Reservation res, float pointsForUsage);
    void delete(int travelId, int userId);
    void delete(int reservationId);
    boolean validateReservation(HttpSession session);
    float createReservation(HttpSession session, int pointsForUsage, float totalPriceCounterForAddingPoints);
    void acceptReservation(int reservationId);
    void declineReservation(Reservation r);
}
