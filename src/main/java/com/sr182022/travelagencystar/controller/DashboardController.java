package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.service.IAccommodationUnitService;
import com.sr182022.travelagencystar.service.IDestinationService;
import com.sr182022.travelagencystar.service.IUserService;
import com.sr182022.travelagencystar.service.IVehicleService;
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
        model.addAttribute("dashboardUsersContent", userService.findAll());
        return "dashboard";
    }

    @GetMapping("destinations")
    public String getDestinationsPage(Model model) {
        model.addAttribute("dashboardDestinationsContent", destinationService.findAll());
        return "dashboard/destinations";
    }

    @GetMapping("vehicles")
    public String getVehiclesPage(Model model) {
        model.addAttribute("dashboardVehiclesContent", vehicleService.findAll());
        model.addAttribute("vehicleTypesForSelectMenu", vehicleService.findAllVehicleTypes());
        model.addAttribute("destinationsForSelectMenu", destinationService.findAll());
        return "dashboard/vehicles";
    }

    @GetMapping("accommodation-units")
    public String getAccommodationUnitsPage(Model model) {
        model.addAttribute("dashboardAccommodationUnitsContent", accommodationUnitService.findAll());
        model.addAttribute("accommodationTypesForSelectMenu", accommodationUnitService.findAllAccommodationTypes());
        model.addAttribute("destinationsForSelectMenu", destinationService.findAll());
        return "dashboard/accommodation-units";
    }
}
