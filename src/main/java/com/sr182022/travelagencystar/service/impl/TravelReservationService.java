package com.sr182022.travelagencystar.service.impl;

import com.sr182022.travelagencystar.DAO.ITravelReservationDAO;
import com.sr182022.travelagencystar.model.*;
import com.sr182022.travelagencystar.service.IReservationService;
import com.sr182022.travelagencystar.service.ITravelReservation;
import com.sr182022.travelagencystar.service.ITravelService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TravelReservationService implements ITravelReservation {

    private final ITravelReservationDAO trDAO;
    private final IReservationService reservationService;
    private final ITravelService travelService;

    @Autowired
    public TravelReservationService(ITravelReservationDAO trDAO, IReservationService reservationService, ITravelService travelService) {
        this.trDAO = trDAO;
        this.reservationService = reservationService;
        this.travelService = travelService;
    }

    @Override
    public List<TravelReservation> findAll() {
        return trDAO.findAll();
    }

    @Override
    public List<TravelReservation> findAll(int travelId) {
        return trDAO.findAll(travelId);
    }

    @Override
    public List<TravelReservation> findAll(LocalDate startDate, LocalDate endDate, String sortDate, String sortTravelForReportsByDestination,
                                           String sortTravelForReportsByVehicle, String sortTravelForReportsByTotalSpace, String sortTravelForReportsBySoldSpace,
                                           String sortTravelForReportsByTotalPrice) {
        if (startDate == null) {
            startDate = LocalDate.of(1999, 1, 1);
        }
        if (endDate == null) {
            endDate = LocalDate.of(2099, 1, 1);
        }

        return trDAO.findAll(startDate, endDate, sortDate, sortTravelForReportsByDestination,
                sortTravelForReportsByVehicle, sortTravelForReportsByTotalSpace, sortTravelForReportsBySoldSpace,
                sortTravelForReportsByTotalPrice);
    }

    @Override
    public TravelReservation findOne(int travelId) {
        return trDAO.findOne(travelId);
    }

    @Override
    public void save(TravelReservation tr) {
        trDAO.save(tr);
    }

    @Override
    public void update(TravelReservation tr) {trDAO.update(tr); }

    @Override
    public boolean passengerValidation(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");
        List<TravelReservation> trList = findAll();
        // initial
        if(trList.isEmpty()) {
            for(CartItem c : cart) {
                Travel t = travelService.findOne(c.getTravel().getId());
                int totalSpace = t.getAccommodationUnit().getCapacity();
                int vehicleSpace = t.getVehicle().getNumberOfSeats();
                int cartPassengers = c.getPassengers();

                if( cartPassengers > vehicleSpace) {
                    return false;
                }
                if(totalSpace-cartPassengers < 0) {
                    return false;
                }
            }
        }
        for(TravelReservation tr : trList) {
            for(CartItem c : cart) {
                if(c.getTravel().getId() == tr.getTravel().getId()) {
                    int availableSpace = tr.getAvailableSpace();
                    int vehicleSpace = tr.getTravel().getVehicle().getNumberOfSeats();
                    int cartPassengers = c.getPassengers();

                    if(cartPassengers > vehicleSpace) {
                        return false;
                    }
                    if(availableSpace-cartPassengers < 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // this is for creating!!
    @Override
    public void createTravelReservation(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        for(CartItem i : cart) {
            if(trDAO.findOne(i.getTravel().getId()) == null) {
                TravelReservation tr = new TravelReservation(
                        i.getTravel(),
                        i.getTravel().getAccommodationUnit().getCapacity(), // totalSpace
                        i.getPassengers(), // sold += this
                        i.getTravel().getAccommodationUnit().getCapacity() - i.getPassengers() // total - sold = available
                );

                save(tr);
            } else {
                TravelReservation tr = trDAO.findOne(i.getTravel().getId());
                int newSoldSpace = tr.getSoldSpace() + i.getPassengers();
                int newAvailableSpace = tr.getAvailableSpace() - i.getPassengers();
                tr.setSoldSpace(newSoldSpace);
                tr.setAvailableSpace(newAvailableSpace);

                update(tr);
            }
        }

        cart.clear();
        session.setAttribute("cart", cart);
        float f = 0;
        session.setAttribute("totalPrice", f);
    }

    @Override
    public TravelReservation reverseValidation(TravelReservation tr, Reservation declineRes) {
        // im just reversing logic -> it couldnt go other way because i realized late that manager have to accept it.
        int oldAvailableSpace = tr.getAvailableSpace();
        int oldSoldSpace = tr.getSoldSpace();
        tr.setAvailableSpace(oldAvailableSpace + declineRes.getPassengers());
        tr.setSoldSpace(oldSoldSpace - declineRes.getPassengers());
        return tr;
    }
}
