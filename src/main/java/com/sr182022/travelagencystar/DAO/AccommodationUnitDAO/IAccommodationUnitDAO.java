package com.sr182022.travelagencystar.DAO.AccommodationUnitDAO;

import com.sr182022.travelagencystar.model.AccommodationUnit;

import java.util.List;

public interface IAccommodationUnitDAO {
    List<AccommodationUnit> Load();
    void Save(List<AccommodationUnit> accommodationUnitsList);
}
