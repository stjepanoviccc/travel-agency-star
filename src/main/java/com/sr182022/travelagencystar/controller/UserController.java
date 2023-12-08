package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.service.IUserService;
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

    @PostMapping("/addNewUser")
    public String addNewUser(@ModelAttribute User newUser) {
        userService.save(newUser);
        return "redirect:/dashboard";
    }

    @GetMapping("/editUser")
    public String editUser(@RequestParam int userId, Model model) {
        model.addAttribute("user", userService.findOne(userId));
        return "editPages/editUserPage";
    }

    @PostMapping("/editUserPost")
    public String editUserPost(@ModelAttribute User editUser) {
        userService.update(editUser);
        // will be redirected to dashboard or profile
        return "redirect:/dashboard";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam int userId) {
        userService.delete(userId);
        return "redirect:/dashboard";
    }
}