package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.AccommodationUnit;
import com.sr182022.travelagencystar.model.Destination;
import com.sr182022.travelagencystar.service.*;
import com.sr182022.travelagencystar.utils.CheckRoleUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final IUserService userService;
    private final IDestinationService destinationService;
    private final IVehicleService vehicleService;
    private final IAccommodationUnitService accommodationUnitService;
    private final ITravelService travelService;

    @Autowired
    public DashboardController(IUserService userService, IDestinationService destinationService, IVehicleService vehicleService,
                               IAccommodationUnitService accommodationUnitService, ITravelService travelService)
    {
        this.userService = userService;
        this.destinationService = destinationService;
        this.vehicleService = vehicleService;
        this.accommodationUnitService = accommodationUnitService;
        this.travelService = travelService;
    }

    @GetMapping()
    public String getDashboardPage(HttpSession session, Model model) {
        try {
            if(!CheckRoleUtil.RoleAdministratorOrOrganizer(session)) {
                return ErrorController.permissionErrorReturn;
            }
            // ask this
            List<String> allRoles = userService.findAllRoles();
            allRoles.add(0, "");
            model.addAttribute("allRoles", allRoles);
            model.addAttribute("dashboardUsersContent", userService.findAll());
            model.addAttribute("sortOrder", "asc");
            return "dashboard";
        } catch(Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }

    @GetMapping("destinations")
    public String getDestinationsPage(HttpSession session, Model model) {
        try {
            if(!CheckRoleUtil.RoleAdministrator(session)) {
                return ErrorController.permissionErrorReturn;
            }
            model.addAttribute("dashboardDestinationsContent", destinationService.findAll());
            return "dashboard/destinations";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }

    @GetMapping("vehicles")
    public String getVehiclesPage(HttpSession session, Model model) {
        try {
            if(!CheckRoleUtil.RoleAdministrator(session)) {
                return ErrorController.permissionErrorReturn;
            }
            model.addAttribute("dashboardVehiclesContent", vehicleService.findAll());
            model.addAttribute("vehicleTypesForSelectMenu", vehicleService.findAllVehicleTypes());
            model.addAttribute("destinationsForSelectMenu", destinationService.findAll());
            return "dashboard/vehicles";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }

    @GetMapping("accommodation-units")
    public String getAccommodationUnitsPage(HttpSession session, Model model) {
        try {
            if(!CheckRoleUtil.RoleAdministrator(session)) {
                return ErrorController.permissionErrorReturn;
            }
            model.addAttribute("dashboardAccommodationUnitsContent", accommodationUnitService.findAll());
            model.addAttribute("accommodationTypesForSelectMenu", accommodationUnitService.findAllAccommodationTypes());
            model.addAttribute("destinationsForSelectMenu", destinationService.findAll());
            return "dashboard/accommodation-units";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }

    @GetMapping("travels")
    public String getTravelsPage(HttpSession session, Model model) {
        try {
            if(!CheckRoleUtil.RoleOrganizer(session)) {
                return ErrorController.permissionErrorReturn;
            }
            model.addAttribute("dashboardTravelsContent", travelService.findAll());
            model.addAttribute("destinationsForSelectMenu", destinationService.findAll());
            // because im working with MODAL i must provide only first so i can filter out later.... same for acc units
            List<Destination> destinations = destinationService.findAll();
            model.addAttribute("vehiclesForSelectMenu", vehicleService.findAll(destinations.get(0).getId()));
            model.addAttribute("accommodationUnitsForSelectMenu", accommodationUnitService.findAll(destinations.get(0).getId()));
            model.addAttribute("travelCategoriesForSelectMenu", travelService.findAllTravelCategories());
            return "dashboard/travels";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }
}
