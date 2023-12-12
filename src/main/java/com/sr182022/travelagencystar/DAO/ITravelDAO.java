package com.sr182022.travelagencystar.DAO;

import com.sr182022.travelagencystar.model.Travel;

import java.util.List;

public interface ITravelDAO {
    List<Travel> findAll();
    Travel findOne(int travelId);
    void save(Travel newTravel, int destinationId, int accommodationUnitId, int vehicleId);
    void update(Travel editTravel, int destinationId, int accommodationUnitId, int vehicleId);
    void delete(int travelId);
}
