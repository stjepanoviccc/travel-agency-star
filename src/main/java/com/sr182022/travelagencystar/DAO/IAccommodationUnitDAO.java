package com.sr182022.travelagencystar.DAO;

import com.sr182022.travelagencystar.model.AccommodationUnit;

import java.util.List;

public interface IAccommodationUnitDAO {
    List<AccommodationUnit> findAll();
    List<String> findAllAccommodationTypes();
    AccommodationUnit findOne(int accommodationUnitId);
    void save(AccommodationUnit newAccommodationUnit, int destinationId);
    void update(AccommodationUnit editAccommodationUnit, int destinationId);
    void delete(int accommodationUnitId);
}
