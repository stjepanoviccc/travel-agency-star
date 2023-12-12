package com.sr182022.travelagencystar.service.impl;

import com.sr182022.travelagencystar.DAO.ITravelDAO;
import com.sr182022.travelagencystar.model.Travel;
import com.sr182022.travelagencystar.model.TravelCategory;
import com.sr182022.travelagencystar.service.ITravelService;
import com.sr182022.travelagencystar.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelService implements ITravelService {

    private final ITravelDAO databaseTravelDAO;

    @Autowired
    public TravelService(ITravelDAO databaseTravelDAO) { this.databaseTravelDAO = databaseTravelDAO; }

    @Override
    public List<Travel> findAll() {
        return databaseTravelDAO.findAll();
    }

    @Override
    public List<String> findAllTravelCategories() {
        return Arrays.stream(TravelCategory.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public Travel findOne(int travelId) {
        return databaseTravelDAO.findOne(travelId);
    }

    @Override
    public void save(Travel newTravel, int destinationId, int accommodationUnitId, int vehicleId) {
        databaseTravelDAO.save(newTravel, destinationId, accommodationUnitId, vehicleId);
    }

    @Override
    public void update(Travel editTravel) {
        databaseTravelDAO.update(editTravel);
    }

    @Override
    public void delete(int travelId) {
        databaseTravelDAO.delete(travelId);
    }

    public int setNumberOfNights(LocalDate startDate, LocalDate endDate) {
        long daysDifference = DateTimeUtil.calculateDaysBetween(startDate, endDate);
        return (int) daysDifference;
    }
}
