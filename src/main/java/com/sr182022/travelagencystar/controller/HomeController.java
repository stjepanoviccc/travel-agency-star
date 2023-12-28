package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.Destination;
import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.service.IAccommodationUnitService;
import com.sr182022.travelagencystar.service.IDestinationService;
import com.sr182022.travelagencystar.service.ITravelService;
import com.sr182022.travelagencystar.service.IVehicleService;
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

    public HomeController(ITravelService travelService, IAccommodationUnitService accommodationUnitService,
                          IVehicleService vehicleService, IDestinationService destinationService) {
        this.travelService = travelService;
        this.accommodationUnitService = accommodationUnitService;
        this.vehicleService = vehicleService;
        this.destinationService = destinationService;
    }

    @GetMapping
    public String getHomePage(Model model) {
        try {
            model.addAttribute("travelsForCards", travelService.findAll());
            /*
            List<Destination> allDestinations = destinationService.findAll();
            List<String> allDestinationNames = new ArrayList<String>();
            for(Destination d : allDestinations) {
                allDestinationNames.add(d.getCity());
            } */
            List<String> allTravelCategories = travelService.findAllTravelCategories();
            List<String> allAccUnitTypes = accommodationUnitService.findAllAccommodationTypes();
            List<String> allVehicleTypes = vehicleService.findAllVehicleTypes();

           // allDestinationNames.add(0, "");
            allTravelCategories.add(0, "");
            allVehicleTypes.add(0, "");
            allAccUnitTypes.add(0, "");

        //    model.addAttribute("allDestinationNames", allDestinationNames);
            model.addAttribute("allTravelCategories", allTravelCategories);
            model.addAttribute("allVehicleTypes", allVehicleTypes);
            model.addAttribute("allAccUnitTypes", allAccUnitTypes);

            model.addAttribute("sortOrder", "asc");
            return "index";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }
}