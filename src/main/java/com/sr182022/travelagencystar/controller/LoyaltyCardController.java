package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.service.ILoyaltyCardService;
import com.sr182022.travelagencystar.service.IUserService;
import com.sr182022.travelagencystar.utils.CheckRoleUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/loyaltyCard")
public class LoyaltyCardController {

    private final ILoyaltyCardService loyaltyCardService;
    private final IUserService userService;

    @Autowired
    public LoyaltyCardController(ILoyaltyCardService loyaltyCardService, IUserService userService) {
        this.loyaltyCardService = loyaltyCardService;
        this.userService = userService;
    }

    @PostMapping("askForLoyaltyCard")
    public String askForLoyaltyCard(HttpSession session, @RequestParam int userId) {
        // first it is false so it means manager must activate or delete it. also im finding user and setting him this new id.
        try {
            if(!CheckRoleUtil.RolePassenger(session)) {
                return ErrorController.permissionErrorReturn;
            }

            if(loyaltyCardService.findOne(userId) != null) {
                return "redirect:/profile?id=";
            }
            loyaltyCardService.save(0, userId, false);
            User u = userService.findOne(userId);
            u.setLoyaltyCard(loyaltyCardService.findOne(u.getId()));
            userService.update(u);
            session.setAttribute("user", u);
            return "redirect:/profile?id=" + userId;
        } catch(Exception e) {
            System.out.println(e);
            return ErrorController.internalErrorReturn;
        }
    }

    @PostMapping("acceptLoyaltyCard")
    public String acceptLoyaltyCard(HttpSession session, @RequestParam int loyaltyCardId) {
        try {
            if(!CheckRoleUtil.RoleAdministrator(session)) {
                return ErrorController.permissionErrorReturn;
            }

            loyaltyCardService.update(loyaltyCardId, 0, "updateInitOrReduce");
            User u = (User) session.getAttribute("user");
            return "redirect:/dashboard/loyaltyCards?id=" + u.getId();
        } catch(Exception e) {
            System.out.println(e);
            return ErrorController.internalErrorReturn;
        }

    }

    // started working on this
    @PostMapping("declineLoyaltyCard")
    public String declineLoyaltyCard(HttpSession session, @RequestParam int loyaltyCardId) {
        try {
            if(!CheckRoleUtil.RoleAdministrator(session)) {
                return ErrorController.permissionErrorReturn;
            }

            loyaltyCardService.delete(loyaltyCardId);
            User u = (User) session.getAttribute("user");
            return "redirect:/dashboard/loyaltyCards?id=" + u.getId();

        } catch(Exception e) {
            System.out.println(e);
            return ErrorController.internalErrorReturn;
        }
    }

}
