package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.LoyaltyCard;
import com.sr182022.travelagencystar.model.Reservation;
import com.sr182022.travelagencystar.model.WishlistItem;
import com.sr182022.travelagencystar.service.ILoyaltyCardService;
import com.sr182022.travelagencystar.service.IReservationService;
import com.sr182022.travelagencystar.service.IWishlistService;
import com.sr182022.travelagencystar.utils.CheckRoleUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final IUserService userService;
    private final IWishlistService wishlistService;
    private final IReservationService reservationService;

    public ProfileController(IUserService userService, IWishlistService wishlistService, IReservationService reservationService) {
        this.userService = userService;
        this.wishlistService = wishlistService;
        this.reservationService = reservationService;
    }

    @GetMapping
    public String getProfilePage(HttpSession session, Model model, @RequestParam int id) {
        try {
            User loggedUser = (User) session.getAttribute("user");
            if(!CheckRoleUtil.UserIsNull(session)) {
                return ErrorController.permissionErrorReturn;
            }
            if(loggedUser.getId() != id) {
                return ErrorController.permissionErrorReturn;
            }

            User user = userService.findOne(id);
            model.addAttribute("user", user);
            return "profile";

        } catch (Exception e) {
            System.out.println(e);
            return ErrorController.internalErrorReturn;
        }
    }

    @GetMapping("/wishlist")
    public String getProfileWishlistPage(HttpSession session, Model model, @RequestParam int id) {
        try {
        User loggedUser = (User) session.getAttribute("user");
        if(!CheckRoleUtil.UserIsNull(session)) {
            return ErrorController.permissionErrorReturn;
        }
        if(loggedUser.getId() != id) {
            return ErrorController.permissionErrorReturn;
        }

        List<WishlistItem> wishlist = wishlistService.findAll(id);
        model.addAttribute("wishlist", wishlist);
        return "profile/wishlist";

        } catch (Exception e) {
            System.out.println(e);
            return ErrorController.internalErrorReturn;
        }
    }

    @GetMapping("/orders")
    public String getProfileOrdersPage(HttpSession session, Model model, @RequestParam int id) {
        try {
            User loggedUser = (User) session.getAttribute("user");
            if(!CheckRoleUtil.UserIsNull(session)) {
                return ErrorController.permissionErrorReturn;
            }
            if(loggedUser.getId() != id) {
                return ErrorController.permissionErrorReturn;
            }

            User user = (User) session.getAttribute("user");
            List<Reservation> reservationList = reservationService.findAll(user.getId());
            model.addAttribute("reservationList", reservationList);

            return "profile/orders";
        } catch (Exception e) {
            System.out.println(e);
            return ErrorController.internalErrorReturn;
        }
    }
}
