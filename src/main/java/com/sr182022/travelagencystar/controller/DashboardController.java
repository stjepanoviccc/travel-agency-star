package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.VehicleType;
import com.sr182022.travelagencystar.service.UserService.UserService;
import com.sr182022.travelagencystar.service.DestinationService.DestinationService;
import com.sr182022.travelagencystar.service.VehicleService.VehicleService;
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
    private final VehicleService vehicleService;

    @Autowired
    public DashboardController(UserService userService, DestinationService destinationService, VehicleService vehicleService) {
        this.userService = userService;
        this.destinationService = destinationService;
        this.vehicleService = vehicleService;
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

    @GetMapping("vehicles")
    public String getVehiclesPage(Model model) {
        model.addAttribute("dashboardVehiclesContent", vehicleService.findAllVehicles());
        model.addAttribute("vehicleTypesForSelectMenu", vehicleService.findAllVehicleTypes());
        model.addAttribute("destinationsForSelectMenu", destinationService.findAllDestinations());
        return "dashboard/vehicles";
    }
}
