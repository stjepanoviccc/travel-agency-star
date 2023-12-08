package com.sr182022.travelagencystar.service.impl;

import com.sr182022.travelagencystar.DAO.impl.AccommodationUnitDAO;
import com.sr182022.travelagencystar.model.AccommodationUnit;
import com.sr182022.travelagencystar.service.IAccommodationUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationUnitService implements IAccommodationUnitService
{
    private final AccommodationUnitDAO accommodationUnitDAO;

    @Autowired
    public AccommodationUnitService(AccommodationUnitDAO accommodationUnitDAO) {
        this.accommodationUnitDAO = accommodationUnitDAO;
    }

    @Override
    public List<AccommodationUnit> findAll() {
        return accommodationUnitDAO.findAll();
    }

    @Override
    public List<String> findAllAccommodationTypes() {
        return accommodationUnitDAO.findAllAccommodationTypes();
    }

    @Override
    public AccommodationUnit findOne(int accommodationUnitId) {
        return accommodationUnitDAO.findOne(accommodationUnitId);
    }

    @Override
    public void save(AccommodationUnit newAccommodationUnit, int destinationId) {
        accommodationUnitDAO.save(newAccommodationUnit, destinationId);
    }

    @Override
    public void update(AccommodationUnit editAccommodationUnit, int destinationId) {
        accommodationUnitDAO.update(editAccommodationUnit, destinationId);
    }

    @Override
    public void delete(int accommodationUnitId) {
        accommodationUnitDAO.delete(accommodationUnitId);
    }

    @Override
    public int generateNextId() {
        return accommodationUnitDAO.generateNextId();
    }

    @Override
    public void setServicesChecking(AccommodationUnit newAccommodationUnit, boolean checkWifi, boolean checkBathroom, boolean checkTv, boolean checkConditioner) {
        newAccommodationUnit.setHasWifi(checkWifi);
        newAccommodationUnit.setHasBathroom(checkBathroom);
        newAccommodationUnit.setHasTv(checkTv);
        newAccommodationUnit.setHasConditioner(checkConditioner);
    }
}
