package com.sr182022.travelagencystar.DAO;

import com.sr182022.travelagencystar.model.TravelReservation;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ITravelReservationDAO {
    List<TravelReservation> findAll();
    List<TravelReservation> findAll(int travelId);
    TravelReservation findOne(int travelId);
    void save(TravelReservation tr);
    @Transactional
    void update(TravelReservation tr);
}
