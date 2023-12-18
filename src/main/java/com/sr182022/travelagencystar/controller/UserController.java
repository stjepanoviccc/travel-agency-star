package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.service.IUserService;
import com.sr182022.travelagencystar.utils.CheckRoleUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login-info")
    public String getInfoAfterRegistration() {
        return "/viewPages/login-info";
    }

    @PostMapping("/addNewUser")
    public String addNewUser(HttpSession session, @ModelAttribute User newUser) {
        if(!CheckRoleUtil.RoleAdministratorOrNull(session)) {
            return "redirect:/permission-error";
        }
        userService.save(newUser);
        if(!CheckRoleUtil.RoleAdministrator(session)) {
            return "redirect:/login-info";
        }
        return "redirect:/dashboard";
    }

    // in this /editUser im also adding deactivateUser because thats in details page.
    @GetMapping("/editUser")
    public String editUser(HttpSession session, @RequestParam int userId, Model model) {
        if(!CheckRoleUtil.RoleAdministratorOrNull(session)) {
            return "redirect:/permission-error";
        }
        User user = userService.findOne(userId);
        if(user == null) {
            return "redirect:/route-error";
        }
        model.addAttribute("user", user);
        return "editPages/editUserPage";
    }

    @PostMapping("/editUserPost")
    public String editUserPost(HttpSession session, @ModelAttribute User editUser) {
        if(!CheckRoleUtil.RoleAdministratorOrPassenger(session)) {
            return "redirect:/permission-error";
        }
        userService.update(editUser);
        if(!CheckRoleUtil.RoleAdministrator(session)) {
            return "redirect:/profile?id=" + editUser.getId();
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(HttpSession session, @RequestParam int userId) {
        if(!CheckRoleUtil.RoleAdministratorOrNull(session)) {
            return "redirect:/permission-error";
        }
        userService.delete(userId);
        if(!CheckRoleUtil.RoleAdministrator(session)) {
            return "redirect:/";
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/deactivateUser")
    public String deactivateUser(HttpSession session, @RequestParam int userId, @RequestParam boolean userIsBlocked) {
        if(!CheckRoleUtil.RoleAdministrator(session)) {
            return "redirect:/permission-error";
        }
        userService.delete(userId, userIsBlocked);

        return "redirect:/dashboard";
    }
}