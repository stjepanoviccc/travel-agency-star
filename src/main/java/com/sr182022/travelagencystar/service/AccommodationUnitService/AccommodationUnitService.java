package com.sr182022.travelagencystar.service.AccommodationUnitService;

import com.sr182022.travelagencystar.DAO.AccommodationUnitDAO.AccommodationUnitDAO;
import com.sr182022.travelagencystar.model.AccommodationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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
    public List<AccommodationUnit> findAllAccommodationUnits() {
        return accommodationUnitDAO.findAllAccommodationUnits();
    }

    @Override
    public List<String> findAllAccommodationTypes() {
        return accommodationUnitDAO.findAllAccommodationTypes();
    }

    @Override
    public AccommodationUnit findAccommodationUnitById(int accommodationUnitId) {
        return accommodationUnitDAO.findAccommodationUnitById(accommodationUnitId);
    }

    @Override
    public void addNewAccommodationUnit(AccommodationUnit newAccommodationUnit, int destinationId) {
        accommodationUnitDAO.addNewAccommodationUnit(newAccommodationUnit, destinationId);
    }

    @Override
    public void editAccommodationUnit(AccommodationUnit editAccommodationUnit, int destinationId) {
        accommodationUnitDAO.editAccommodationUnit(editAccommodationUnit, destinationId);
    }

    @Override
    public void deleteAccommodationUnit(int accommodationUnitId) {
        accommodationUnitDAO.deleteAccommodationUnit(accommodationUnitId);
    }

    @Override
    public int generateNextId() {
        return accommodationUnitDAO.generateNextId();
    }
}
