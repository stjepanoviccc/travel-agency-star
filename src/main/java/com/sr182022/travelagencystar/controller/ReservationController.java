package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.Reservation;
import com.sr182022.travelagencystar.model.TravelReservation;
import com.sr182022.travelagencystar.service.IReservationService;
import com.sr182022.travelagencystar.service.ITravelReservation;
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
    private ITravelReservation trService;

    @Autowired
    public ReservationController(IReservationService reservationService, ITravelReservation trService) {
        this.reservationService = reservationService;
        this.trService = trService;
    }

    @PostMapping("createReservation")
    public String createReservation(HttpSession session) {
        try {
            if(!CheckRoleUtil.RolePassenger(session)) {
                return ErrorController.permissionErrorReturn;
            }

            boolean validation = reservationService.validateReservation(session);
            boolean passengerValidation = trService.passengerValidation(session);
            if(!validation || !passengerValidation) {
                return ErrorController.availableSpaceErrorReturn;
            }

            reservationService.createReservation(session);
            trService.createTravelReservation(session);

            return "redirect:/cart";
        } catch(Exception e) {
            System.out.println(e);
            return ErrorController.internalErrorReturn;
        }
    }

    @PostMapping("acceptReservation")
    public String acceptReservation(HttpSession session, int reservationId) {
        try {
            if(!CheckRoleUtil.RoleOrganizer(session)) {
                return ErrorController.permissionErrorReturn;
            }

            reservationService.acceptReservation(reservationId);
            return "redirect:/dashboard/pendingReservations";
        } catch(Exception e) {
            System.out.println(e);
            return ErrorController.internalErrorReturn;
        }
    }

    @PostMapping("declineReservation")
    public String declineReservation(HttpSession session, int reservationId) {
        try {
            if(!CheckRoleUtil.RoleOrganizer(session)) {
                return ErrorController.permissionErrorReturn;
            }

            Reservation declineRes = reservationService.findOne(reservationId);
            TravelReservation tr = trService.findOne(declineRes.getTravel().getId());
            tr = trService.reverseValidation(tr, declineRes);
            trService.update(tr);
            reservationService.declineReservation(declineRes);
            return "redirect:/dashboard/pendingReservations";
        } catch(Exception e) {
            System.out.println(e);
            return ErrorController.internalErrorReturn;
        }
    }


}
