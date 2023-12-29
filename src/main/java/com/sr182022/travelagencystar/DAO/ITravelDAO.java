package com.sr182022.travelagencystar.DAO;

import com.sr182022.travelagencystar.model.Travel;

import java.time.LocalDate;
import java.util.List;

public interface ITravelDAO {
    List<Travel> findAll();
    List<Travel> findAll(int destinationId);
    Travel findOne(int travelId);
    void save(Travel newTravel, int destinationId, int accommodationUnitId, int vehicleId);
    void update(Travel editTravel, int destinationId, int accommodationUnitId, int vehicleId);
    void delete(int travelId);
    List<Travel> findAll(String destination, String travelCategory, String travelVehicleType, String travelAccUnitType, Float minPrice, Float maxPrice,
                         LocalDate startDate, LocalDate endDate, String sortOrder);
}
