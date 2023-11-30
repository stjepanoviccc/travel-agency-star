package com.sr182022.travelagencystar.service.AccommodationUnitService;

import com.sr182022.travelagencystar.model.AccommodationUnit;

import java.util.List;

public interface IAccommodationUnitService {

    List<AccommodationUnit> findAllAccommodationUnits();
    List<String> findAllAccommodationTypes();
    AccommodationUnit findAccommodationUnitById(int accommodationUnitId);
    void addNewAccommodationUnit(AccommodationUnit newAccommodationUnit, int destinationId);
    void editAccommodationUnit(AccommodationUnit editAccommodationUnit, int destinationId);
    void deleteAccommodationUnit(int accommodationUnitId);
    int generateNextId();
}
