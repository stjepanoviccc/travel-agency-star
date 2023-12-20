package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.service.IWishlistService;
import com.sr182022.travelagencystar.utils.CheckRoleUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/profile/wishlist")
public class WishlistController {

    private final IWishlistService wishlistService;

    public WishlistController(IWishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("addToWishlist")
    public String addToWishlist(HttpSession session, @RequestParam int idUser, @RequestParam int idTravel) {
        return "i will implement this";
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
