package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.Destination;
import com.sr182022.travelagencystar.model.Travel;
import com.sr182022.travelagencystar.model.TravelReservation;
import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.service.*;
import com.sr182022.travelagencystar.utils.CheckRoleUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    private final ITravelService travelService;
    private final IAccommodationUnitService accommodationUnitService;
    private final IVehicleService vehicleService;
    private final IDestinationService destinationService;
    private final ITravelReservation trService;

    public HomeController(ITravelService travelService, IAccommodationUnitService accommodationUnitService,
                          IVehicleService vehicleService, IDestinationService destinationService, ITravelReservation trService) {
        this.travelService = travelService;
        this.accommodationUnitService = accommodationUnitService;
        this.vehicleService = vehicleService;
        this.destinationService = destinationService;
        this.trService = trService;
    }

    @GetMapping
    public String getHomePage(HttpSession session, Model model) {
        try {
            List<Travel> allTravels = travelService.findAll();
            List<TravelReservation> trs = trService.findAll();
            if(!CheckRoleUtil.RoleAdministratorOrOrganizer(session)) {
                travelService.returnOnlyAvailableTravels(session, allTravels, trs);
            }

            model.addAttribute("travelsForCards", allTravels);

            List<String> allTravelCategories = travelService.findAllTravelCategories();
            List<String> allAccUnitTypes = accommodationUnitService.findAllAccommodationTypes();
            List<String> allVehicleTypes = vehicleService.findAllVehicleTypes();

            allTravelCategories.add(0, "");
            allVehicleTypes.add(0, "");
            allAccUnitTypes.add(0, "");

            model.addAttribute("allTravelCategories", allTravelCategories);
            model.addAttribute("allVehicleTypes", allVehicleTypes);
            model.addAttribute("allAccUnitTypes", allAccUnitTypes);

            model.addAttribute("sortOrder", "asc");
            return "index";
        } catch (Exception e) {
            System.out.println(e);
            return ErrorController.internalErrorReturn;
        }
    }
}