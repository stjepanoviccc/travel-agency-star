package com.sr182022.travelagencystar.service.impl;

import com.sr182022.travelagencystar.DAO.IReservationDAO;
import com.sr182022.travelagencystar.model.Reservation;
import com.sr182022.travelagencystar.service.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService implements IReservationService {
    private final IReservationDAO reservationDAO;
    @Autowired
    public ReservationService(IReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    @Override
    public List<Reservation> findAll() {
        return null;
    }

    @Override
    public Reservation findOne(int travelId, int userId) {
        return null;
    }

    @Override
    public void delete(int travelId, int userId) {

    }
}
