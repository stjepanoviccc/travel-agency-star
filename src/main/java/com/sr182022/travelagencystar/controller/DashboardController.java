package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.service.AccommodationUnitService.IAccommodationUnitService;
import com.sr182022.travelagencystar.service.DestinationService.IDestinationService;
import com.sr182022.travelagencystar.service.UserService.IUserService;
import com.sr182022.travelagencystar.service.VehicleService.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final IUserService userService;
    private final IDestinationService destinationService;
    private final IVehicleService vehicleService;
    private final IAccommodationUnitService accommodationUnitService;

    @Autowired
    public DashboardController(IUserService userService, IDestinationService destinationService, IVehicleService vehicleService,
                               IAccommodationUnitService accommodationUnitService1)
    {
        this.userService = userService;
        this.destinationService = destinationService;
        this.vehicleService = vehicleService;
        this.accommodationUnitService = accommodationUnitService1;
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

    @GetMapping("accommodation-units")
    public String getAccommodationUnitsPage(Model model) {
        model.addAttribute("dashboardAccommodationUnitsContent", accommodationUnitService.findAllAccommodationUnits());
        model.addAttribute("accommodationTypesForSelectMenu", accommodationUnitService.findAllAccommodationTypes());
        model.addAttribute("destinationsForSelectMenu", destinationService.findAllDestinations());
        return "dashboard/accommodation-units";
    }
}
