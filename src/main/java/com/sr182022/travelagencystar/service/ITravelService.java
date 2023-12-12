package com.sr182022.travelagencystar.service;

import com.sr182022.travelagencystar.model.Travel;

import java.time.LocalDate;
import java.util.List;

public interface ITravelService {
    List<Travel> findAll();
    List<String> findAllTravelCategories();
    Travel findOne(int travelId);
    void save(Travel newTravel, int destinationId, int accommodationUnitId, int vehicleId);
    void update(Travel editTravel, int destinationId, int accommodationUnitId, int vehicleId);
    void delete(int travelId);
    int setNumberOfNights(LocalDate startDate, LocalDate endDate);
}
