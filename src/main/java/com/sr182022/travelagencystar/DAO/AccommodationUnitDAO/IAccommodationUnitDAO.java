package com.sr182022.travelagencystar.DAO.AccommodationUnitDAO;

import com.sr182022.travelagencystar.model.AccommodationUnit;

import java.util.List;

public interface IAccommodationUnitDAO {
    List<AccommodationUnit> Load();
    List<AccommodationUnit> findAllAccommodationUnits();
    AccommodationUnit findAccommodationUnitById(int accommodationUnitId);
    void addNewAccommodationUnit(AccommodationUnit newAccommodationUnit);
    void editAccommodationUnit(AccommodationUnit editAccommodationUnit);
    void deleteAccommodationUnit(int accommodationUnitId);
    int generateNextId();
}
