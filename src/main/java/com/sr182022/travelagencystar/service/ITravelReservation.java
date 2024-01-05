package com.sr182022.travelagencystar.service;

import com.sr182022.travelagencystar.model.TravelReservation;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.util.List;

public interface ITravelReservation {
    List<TravelReservation> findAll();
    List<TravelReservation> findAll(int travelId);
    List<TravelReservation> findAll(LocalDate startDate, LocalDate endDate, String sortDate, String sortTravelForReportsByDestination, String sortTravelForReportsByVehicle,
                                    String sortTravelForReportsByTotalSpace, String sortTravelForReportsBySoldSpace, String sortTravelForReportsByTotalPrice);
    void save(TravelReservation tr);
    void update(TravelReservation tr);
    boolean passengerValidation(HttpSession session);
    void createTravelReservation(HttpSession session);
}
