package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.utils.CheckRoleUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final IUserService userService;

    public ProfileController(IUserService userService) {
        this.userService = userService;
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
        //
        return "profile/wishlist";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }
}
