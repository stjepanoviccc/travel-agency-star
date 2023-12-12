package com.sr182022.travelagencystar.service.impl;

import com.sr182022.travelagencystar.DAO.IAccommodationUnitDAO;
import com.sr182022.travelagencystar.model.AccommodationType;
import com.sr182022.travelagencystar.model.AccommodationUnit;
import com.sr182022.travelagencystar.service.IAccommodationUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccommodationUnitService implements IAccommodationUnitService
{
    private final IAccommodationUnitDAO databaseAccommodationUnitDAO;

    @Autowired
    public AccommodationUnitService(IAccommodationUnitDAO databaseAccommodationUnitDAO) {
        this.databaseAccommodationUnitDAO = databaseAccommodationUnitDAO;
    }

    @Override
    public List<AccommodationUnit> findAll() {
        return databaseAccommodationUnitDAO.findAll();
    }

    @Override
    public List<String> findAllAccommodationTypes() {
        return Arrays.stream(AccommodationType.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public AccommodationUnit findOne(int accommodationUnitId) {
        return databaseAccommodationUnitDAO.findOne(accommodationUnitId);
    }

    @Override
    public void save(AccommodationUnit newAccommodationUnit, int destinationId) {
        databaseAccommodationUnitDAO.save(newAccommodationUnit, destinationId);
    }

    @Override
    public void update(AccommodationUnit editAccommodationUnit, int destinationId) {
        databaseAccommodationUnitDAO.update(editAccommodationUnit, destinationId);
    }

    @Override
    public void delete(int accommodationUnitId) {
        databaseAccommodationUnitDAO.delete(accommodationUnitId);
    }

    @Override
    public void setServicesChecking(AccommodationUnit newAccommodationUnit, boolean checkWifi, boolean checkBathroom, boolean checkTv, boolean checkConditioner) {
        newAccommodationUnit.setHasWifi(checkWifi);
        newAccommodationUnit.setHasBathroom(checkBathroom);
        newAccommodationUnit.setHasTv(checkTv);
        newAccommodationUnit.setHasConditioner(checkConditioner);
    }
}
