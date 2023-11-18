package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String getDashboardPage(Model model) {
        List<User> usersList = userService.getAllUsers();
        model.addAttribute("dashboardContent", usersList);
        return "dashboard";
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@ModelAttribute User newUser) {
        userService.addNewUser(newUser);
        return "redirect:/dashboard";
    }

    @PostMapping("/editUser")
    public String editUser(@ModelAttribute User editUser) {
        userService.editUser(editUser);
        return "redirect:/dashboard";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam int userId) {
        userService.deleteUser(userId);
        return "redirect:/dashboard";
    }
}
