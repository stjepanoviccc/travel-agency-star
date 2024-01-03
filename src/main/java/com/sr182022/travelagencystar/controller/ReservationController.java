package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.Reservation;
import com.sr182022.travelagencystar.service.IReservationService;
import com.sr182022.travelagencystar.utils.CheckRoleUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/reservation")
public class ReservationController {
    private IReservationService reservationService;

    @Autowired
    public ReservationController(IReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("createReservation")
    public String createReservation(HttpSession session) {
        try {
            if(!CheckRoleUtil.RolePassenger(session)) {
                return ErrorController.permissionErrorReturn;
            }

            boolean validation = reservationService.validateReservation(session);
            if(!validation) {
                // handle false validity
                return "redirect:/cart";
            }

            List<Reservation> reservations = reservationService.createReservation(session);
            return "redirect:/cart";
        } catch(Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }


}
