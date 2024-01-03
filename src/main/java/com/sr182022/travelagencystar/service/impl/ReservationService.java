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
    public Reservation findOne(int travelId, int userId) {
        return reservationDAO.findOne(travelId, userId);
    }

    @Override
    public void save(Reservation res) { reservationDAO.save(res); }

    @Override
    public void delete(int travelId, int userId) {
        reservationDAO.delete(travelId, userId);
    }

    @Override
    public boolean validateReservation(HttpSession session) {
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
        return true;
    }

    @Override
    public List<Reservation> createReservation(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");

        List<Reservation> resList = new ArrayList<Reservation>();
        for(CartItem i : cart) {
            Reservation res = new Reservation(i.getTravel(), user, i.getPassengers(), LocalDateTime.now().plusHours(1));
            save(res);
        }

        // clear after success creating
        cart.clear();
        session.setAttribute("cart", cart);
        float f = 0;
        session.setAttribute("totalPrice", f);
        return resList;
    }
}
