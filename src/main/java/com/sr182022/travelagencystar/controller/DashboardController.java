package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.service.UserService.UserService;
import com.sr182022.travelagencystar.service.DestinationService.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final UserService userService;
    private final DestinationService destinationService;

    @Autowired
    public DashboardController(UserService userService, DestinationService destinationService) {
        this.userService = userService;
        this.destinationService = destinationService;
    }

    @GetMapping()
    public String getDashboardPage(Model model) {
        model.addAttribute("dashboardUsersContent", userService.findAllUsers());
        return "dashboard";
    }

    @GetMapping("destinations")
    public String getDestinationsPage(Model model) {
        model.addAttribute("dashboardDestinationsContent", destinationService.findAllDestinations());
        return "dashboard/destinations";
    }
}
