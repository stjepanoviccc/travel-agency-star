package com.sr182022.travelagencystar.service;

import com.sr182022.travelagencystar.model.Travel;
import com.sr182022.travelagencystar.model.TravelReservation;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.util.List;

public interface ITravelService {
    List<Travel> findAll();
    List<Travel> findAll(int destinationId);
    List<Travel> findAll(String destination, String destinationSort, String travelCategory, String travelCategorySort,
                         String travelVehicleType, String travelVehicleTypeSort, String travelAccUnitType, String travelAccUnitTypeSort,
                         Float minPrice, Float maxPrice, String priceSort,
                         LocalDate startDate, LocalDate endDate, String dateSort, Integer nightsFrom, Integer nightsTo, String nightsSort,
                         Integer passengersAvailability, String sortTravelPassengersAvailability, String inputID, String sortTravelByID);
    List<String> findAllTravelCategories();
    Travel findOne(int travelId);
    void save(Travel newTravel, int destinationId, int accommodationUnitId, int vehicleId);
    void update(Travel editTravel, int destinationId, int accommodationUnitId, int vehicleId);
    void delete(int travelId);
    int setNumberOfNights(LocalDate startDate, LocalDate endDate);
    // removeSelectedOne is used when i get all travels in details page to remove already existing one which i showed on top of page.
    List<Travel> removeSelectedOne(int selectedTravelId, List<Travel> travels);
    boolean tryValidate(Travel travel, int destinationId, int vehicleId, int accUnitId);
    List<Travel> returnOnlyAvailableTravels(HttpSession session, List<Travel> allTravels, List<TravelReservation> trs);
}
