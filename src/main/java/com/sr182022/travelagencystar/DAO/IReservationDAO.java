package com.sr182022.travelagencystar.DAO;

import com.sr182022.travelagencystar.model.Reservation;

import java.util.List;

public interface IReservationDAO {
    List<Reservation> findAll();
    List<Reservation> findAll(int userId);
    Reservation findOne(int travelId, int userId);

    Reservation findOne(int reservationId);

    void save(Reservation res);
    void delete(int travelId, int userId);
}
