package com.sr182022.travelagencystar.service.impl;

import com.sr182022.travelagencystar.DAO.IReservationDAO;
import com.sr182022.travelagencystar.model.CartItem;
import com.sr182022.travelagencystar.model.Reservation;
import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.service.IReservationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService implements IReservationService {
    private final IReservationDAO reservationDAO;
    @Autowired
    public ReservationService(IReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    @Override
    public List<Reservation> findAll() {
        return reservationDAO.findAll();
    }

    @Override
    public List<Reservation> findAll(int userId) {
        return reservationDAO.findAll(userId);
    }

    @Override
    public List<Reservation> findAll(boolean pending) {
        return reservationDAO.findAll(pending);
    }

    @Override
    public Reservation findOne(int reservationId) {
        return reservationDAO.findOne(reservationId);
    }

    @Override
    public Reservation findOne(int travelId, int userId) {
        return reservationDAO.findOne(travelId, userId);
    }

    @Override
    public void save(Reservation res, float pointsForUsage) { reservationDAO.save(res, pointsForUsage); }

    @Override
    public void delete(int travelId, int userId) {
        reservationDAO.delete(travelId, userId);
    }

    @Override
    public void delete(int reservationId) {reservationDAO.delete(reservationId);}

    @Override
    public boolean validateReservation(HttpSession session) {
        return true;
        /*
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");
        List<Reservation> allRes = findAll();
        for(Reservation r : allRes) {
            for(CartItem c : cart) {
                // if already exists
                if(c.getTravel().getId() == r.getTravel().getId() && user.getId() == r.getUser().getId()) {
                    return false;
                }
            }
        }
        return true; */
    }

    @Override
    public float createReservation(HttpSession session, int pointsForUsage, float totalFinalPrice) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");

        List<Reservation> resList = new ArrayList<Reservation>();

        for(CartItem i : cart) {
            float finalPrice = 0;
            Reservation res = new Reservation(i.getTravel(), user, i.getPassengers(), LocalDateTime.now().plusHours(1));

            // count if points are used / unused
            if(pointsForUsage == 0) {
                // if not used
                finalPrice = res.getPassengers() * res.getTravel().getPrice();
            } else {
                // if used
                float percentageToCutOff = 5 * pointsForUsage;
                finalPrice = res.getTravel().getPrice() - (res.getTravel().getPrice() * (percentageToCutOff/100));
                finalPrice *= res.getPassengers();
            }

            totalFinalPrice += finalPrice;
            save(res, finalPrice);
        }

        return totalFinalPrice;
    }

    @Override
    public void acceptReservation(int reservationId) {
        reservationDAO.acceptReservation(reservationId);
    }

    @Override
    public void declineReservation(Reservation r) {
        reservationDAO.declineReservation(r);
    }
}
