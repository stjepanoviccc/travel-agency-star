package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@ModelAttribute User newUser) {
        userService.addNewUser(newUser);
        return "redirect:/dashboard";
    }

    @GetMapping("/editUser")
    public String editUser(@RequestParam int userId, Model model) {
        model.addAttribute("user", userService.findUserById(userId));
        return "editPages/editUserPage";
    }

    @PostMapping("/editUserPost")
    public String editUserPost(@ModelAttribute User editUser) {
        userService.editUser(editUser);
        // will be redirected to dashboard or profile
        return "redirect:/dashboard";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam int userId) {
        userService.deleteUser(userId);
        return "redirect:/dashboard";
    }
}