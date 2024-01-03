package com.sr182022.travelagencystar.DAO;

import com.sr182022.travelagencystar.controller.TravelReservation;

import java.util.List;

public interface ITravelReservationDAO {
    List<TravelReservation> findAll();
}
