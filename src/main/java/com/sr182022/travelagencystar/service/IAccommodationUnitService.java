package com.sr182022.travelagencystar.service;

import com.sr182022.travelagencystar.model.AccommodationUnit;

import java.util.List;

public interface IAccommodationUnitService {

    List<AccommodationUnit> findAll();
    List<String> findAllAccommodationTypes();
    AccommodationUnit findOne(int accommodationUnitId);
    void save(AccommodationUnit newAccommodationUnit, int destinationId);
    void update(AccommodationUnit editAccommodationUnit, int destinationId);
    void delete(int accommodationUnitId);
    void setServicesChecking(AccommodationUnit newAccommodationUnit, boolean checkWifi, boolean checkBathroom, boolean checkTv, boolean checkConditioner);
}
