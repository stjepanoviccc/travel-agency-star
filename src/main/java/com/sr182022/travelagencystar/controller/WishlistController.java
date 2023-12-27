package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.service.IWishlistService;
import com.sr182022.travelagencystar.utils.CheckRoleUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WishlistController {

    private final IWishlistService wishlistService;

    public WishlistController(IWishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("addToWishlist")
    public String addToWishlist(HttpSession session, @RequestParam(required = false) int idUser, @RequestParam(required = false) int idTravel) {
        try {
            // I WILL FIX THIS, I CANT RETURN PERMISSION ERROR ON THIS ALSO I MUST FIX THAT CANT BE 2 OF SAME USER TRAVEL KEYS
            if(!CheckRoleUtil.RolePassenger(session)) {
                return ErrorController.permissionErrorReturn;
            }
            boolean doesExist = wishlistService.checkExistence(idUser, idTravel);
            // if doesnt exist, then add.
            if(!doesExist) {
                wishlistService.save(idUser, idTravel);
            }
            return "redirect:/";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }

    @PostMapping("deleteFromWishlist")
    public String deleteFromWishlist(HttpSession session, @RequestParam int idUser, @RequestParam int idTravel) {
        try {
            if(!CheckRoleUtil.UserIsNull(session)) {
                return ErrorController.permissionErrorReturn;
            }
            wishlistService.delete(idUser, idTravel);
            return "redirect:/profile/wishlist?id=" + idUser;
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }
}
