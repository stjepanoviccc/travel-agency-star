package com.sr182022.travelagencystar.service.AccommodationUnitService;

import com.sr182022.travelagencystar.DAO.AccommodationUnitDAO.AccommodationUnitDAO;
import com.sr182022.travelagencystar.model.AccommodationType;
import com.sr182022.travelagencystar.model.AccommodationUnit;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationUnitService implements IAccommodationUnitService
{
    private final AccommodationUnitDAO accommodationUnitDAO;
    ServletContext servletContext;

    @Autowired
    public AccommodationUnitService(AccommodationUnitDAO accommodationUnitDAO, ServletContext servletContext) {
        this.accommodationUnitDAO = accommodationUnitDAO;
        this.servletContext = servletContext;
    }

    @Override
    public List<AccommodationUnit> findAllAccommodationUnits() {
        return accommodationUnitDAO.findAllAccommodationUnits();
    }

    @Override
    public List<AccommodationType> findAllAccommodationTypes() {
        return accommodationUnitDAO.findAllAccommodationTypes();
    }

    @Override
    public AccommodationUnit findAccommodationUnitById(int accommodationUnitId) {
        return accommodationUnitDAO.findAccommodationUnitById(accommodationUnitId);
    }

    @Override
    public void addNewAccommodationUnit(AccommodationUnit newAccommodationUnit) {
        accommodationUnitDAO.addNewAccommodationUnit(newAccommodationUnit);
    }

    @Override
    public void editAccommodationUnit(AccommodationUnit editAccommodationUnit) {
        accommodationUnitDAO.editAccommodationUnit(editAccommodationUnit);
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
