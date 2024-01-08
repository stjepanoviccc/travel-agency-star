package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.LoyaltyCard;
import com.sr182022.travelagencystar.model.Reservation;
import com.sr182022.travelagencystar.model.TravelReservation;
import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.service.ILoyaltyCardService;
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
    private ILoyaltyCardService loyaltyCardService;

    @Autowired
    public ReservationController(IReservationService reservationService, ITravelReservation trService, ILoyaltyCardService loyaltyCardService) {
        this.reservationService = reservationService;
        this.trService = trService;
        this.loyaltyCardService = loyaltyCardService;
    }

    @PostMapping("createReservation")
    public String createReservation(HttpSession session, int pointsForUsage) {
        try {
            if(!CheckRoleUtil.RolePassenger(session)) {
                return ErrorController.permissionErrorReturn;
            }

            boolean validation = reservationService.validateReservation(session);
            boolean passengerValidation = trService.passengerValidation(session);
            if(!validation || !passengerValidation) {
                return ErrorController.availableSpaceErrorReturn;
            }
            float totalFinalPrice = 0;
            totalFinalPrice = reservationService.createReservation(session, pointsForUsage, totalFinalPrice);
            trService.createTravelReservation(session);
            User user = (User) session.getAttribute("user");
            if(user.getLoyaltyCard() != null && totalFinalPrice >= 10000) {
                int addPoints = (int) totalFinalPrice / 10000;
                if(pointsForUsage > 0) {
                    addPoints -= pointsForUsage;
                }
                if(user.getLoyaltyCard().getPoints() > 0) {
                    loyaltyCardService.update(user.getLoyaltyCard().getId(), addPoints, "updateAdd");
                } else {
                    loyaltyCardService.update(user.getLoyaltyCard().getId(), addPoints, "updateInitOrReduce");
                }
                loyaltyCardService.saveJunction(user.getLoyaltyCard(), addPoints);
            }

            return "redirect:/";
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
            Reservation r = reservationService.findOne(reservationId);
            User u = r.getUser();
            if(u.getLoyaltyCard() != null) {
                loyaltyCardService.deleteJunction(u.getLoyaltyCard().getId());
            }
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
            if(declineRes.getUser().getLoyaltyCard() != null) {
                int reducePointsBy = loyaltyCardService.takePointsFromJunction(declineRes.getUser().getLoyaltyCard().getId());
                LoyaltyCard lc = loyaltyCardService.findOne(declineRes.getUser().getId());
                lc.setPoints(lc.getPoints() - reducePointsBy);
                loyaltyCardService.update(lc.getId(), lc.getPoints(), "updateInitOrReduce");
                loyaltyCardService.deleteJunction(lc.getId());
                reservationService.delete(declineRes.getReservationId());
            }

            return "redirect:/dashboard/pendingReservations";
        } catch(Exception e) {
            System.out.println(e);
            return ErrorController.internalErrorReturn;
        }
    }


}
