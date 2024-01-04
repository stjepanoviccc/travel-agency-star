package com.sr182022.travelagencystar.service.impl;

import com.sr182022.travelagencystar.DAO.ITravelDAO;
import com.sr182022.travelagencystar.model.*;
import com.sr182022.travelagencystar.service.IAccommodationUnitService;
import com.sr182022.travelagencystar.service.ITravelService;
import com.sr182022.travelagencystar.service.IVehicleService;
import com.sr182022.travelagencystar.utils.CheckRoleUtil;
import com.sr182022.travelagencystar.utils.DateTimeUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelService implements ITravelService {

    private final ITravelDAO databaseTravelDAO;
    private final IVehicleService vehicleService;
    private final IAccommodationUnitService accommodationUnitService;

    @Autowired
    public TravelService(ITravelDAO databaseTravelDAO, IVehicleService vehicleService, IAccommodationUnitService accommodationUnitService) {
        this.databaseTravelDAO = databaseTravelDAO;
        this.vehicleService = vehicleService;
        this.accommodationUnitService = accommodationUnitService;
    }

    @Override
    public List<Travel> findAll() {
        return databaseTravelDAO.findAll();
    }

    @Override
    public List<Travel> findAll(int destinationId) {
        return databaseTravelDAO.findAll(destinationId);
    }

    @Override
    public List<Travel> findAll(String destination, String destinationSort, String travelCategory, String travelCategorySort,
                                String travelVehicleType, String travelVehicleTypeSort, String travelAccUnitType, String travelAccUnitTypeSort,
                                Float minPrice, Float maxPrice, String priceSort,
                                LocalDate startDate, LocalDate endDate, String dateSort,
                                Integer nightsFrom, Integer nightsTo, String sortNights,
                                Integer passengersAvailability, String sortTravelPassengersAvailability, String inputID, String sortTravelByID) {

        if(destination == null) {
            destination = "";
        }
        if(travelCategory == null) {
            travelCategory = "";
        }
        if(travelVehicleType == null) {
            travelVehicleType = "";
        }
        if(travelAccUnitType == null) {
            travelAccUnitType = "";
        }
        if(minPrice == null) {
            minPrice = 0F;
        }
        if(maxPrice == null) {
            maxPrice = 999999F;
        }
        if (startDate == null) {
            startDate = LocalDate.of(1999, 1, 1);
        }
        if (endDate == null) {
            endDate = LocalDate.of(2099, 1, 1);
        }
        if(nightsFrom == null) {
            nightsFrom = Integer.MIN_VALUE;
        }
        if(nightsTo == null) {
            nightsTo = Integer.MAX_VALUE;
        }
        if(passengersAvailability == null) {
            passengersAvailability = Integer.MIN_VALUE;
        }
        if(inputID == null) {
            inputID = "";
        }

        return databaseTravelDAO.findAll(destination, destinationSort, travelCategory, travelCategorySort, travelVehicleType, travelVehicleTypeSort,
                travelAccUnitType, travelAccUnitTypeSort, minPrice, maxPrice, priceSort, startDate, endDate, dateSort, nightsFrom, nightsTo, sortNights,
                passengersAvailability, sortTravelPassengersAvailability, inputID, sortTravelByID);
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
    public void update(Travel editTravel, int destinationId, int accommodationUnitId, int vehicleId) {
        databaseTravelDAO.update(editTravel, destinationId, accommodationUnitId, vehicleId);
    }

    @Override
    public void delete(int travelId) {
        databaseTravelDAO.delete(travelId);
    }

    public int setNumberOfNights(LocalDate startDate, LocalDate endDate) {
        long daysDifference = DateTimeUtil.calculateDaysBetween(startDate, endDate);
        return (int) daysDifference;
    }

    @Override
    public List<Travel> removeSelectedOne(int selectedTravelId, List<Travel> travels) {
        Travel travelToRemove = new Travel();
        for(Travel tr : travels) {
            if(tr.getId() == selectedTravelId) {
                travelToRemove = tr;
                break;
            }
        }

        travels.remove(travelToRemove);
        return travels;
    }

    @Override
    public boolean tryValidate(Travel travel, int destinationId, int vehicleId, int accUnitId) {
        if(travel.getPrice() < 0) {
            return false;
        }

        int daysBetween = (int) DateTimeUtil.calculateDaysBetween(travel.getStartDate(), travel.getEndDate());
        if(daysBetween < 0) {
            return false;
        }

        if(DateTimeUtil.isDateInPast(travel.getStartDate()) || DateTimeUtil.isDateInPast(travel.getEndDate())) {
           return false;
        }

        Vehicle vehicle = vehicleService.findOne(vehicleId);
        AccommodationUnit accUnit = accommodationUnitService.findOne(accUnitId);
        if(destinationId != vehicle.getFinalDestination().getId() || destinationId != accUnit.getDestination().getId()) {
            return false;
        }
        return true;
    }

    @Override
    public List<Travel> returnOnlyAvailableTravels(HttpSession session, List<Travel> allTravels, List<TravelReservation> trs) {
        List<Travel> travelsToRemove = new ArrayList<Travel>();
        for (TravelReservation tr : trs) {
            for (Travel t : allTravels) {
                if (t.getId() == tr.getTravel().getId()) {
                    if (tr.getAvailableSpace() <= 0) {
                        travelsToRemove.add(t);
                    }
                }
            }
        }
        allTravels.removeAll(travelsToRemove);
        return allTravels;
        }
    }
