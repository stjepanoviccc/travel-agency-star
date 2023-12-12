package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.Travel;
import com.sr182022.travelagencystar.service.IAccommodationUnitService;
import com.sr182022.travelagencystar.service.IDestinationService;
import com.sr182022.travelagencystar.service.ITravelService;
import com.sr182022.travelagencystar.service.IVehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboard/travels")
public class TravelController {

    private final ITravelService travelService;
    private final IDestinationService destinationService;
    private final IVehicleService vehicleService;
    private final IAccommodationUnitService accommodationUnitService;

    public TravelController(ITravelService travelService, IDestinationService destinationService, IVehicleService vehicleService,
                            IAccommodationUnitService accommodationUnitService) {
        this.travelService = travelService;
        this.destinationService = destinationService;
        this.vehicleService = vehicleService;
        this.accommodationUnitService = accommodationUnitService;
    }

    @PostMapping("addNewTravel")
    public String addNewDestination(@ModelAttribute Travel newTravel,
                                    @RequestParam int destinationId,
                                    @RequestParam int accommodationUnitId,
                                    @RequestParam int vehicleId) {
        newTravel.setNumberOfNights(travelService.setNumberOfNights(newTravel.getStartDate(), newTravel.getEndDate()));
        travelService.save(newTravel, destinationId, accommodationUnitId, vehicleId);
        return "redirect:/dashboard/travels";
    }

    @GetMapping("editTravel")
    public String editTravel(@RequestParam int travelId, Model model) {
        model.addAttribute("travel", travelService.findOne(travelId));
        model.addAttribute("destinationsForSelectMenu", destinationService.findAll());
        model.addAttribute("vehiclesForSelectMenu", vehicleService.findAll());
        model.addAttribute("accommodationUnitsForSelectMenu", accommodationUnitService.findAll());
        model.addAttribute("travelCategoriesForSelectMenu", travelService.findAllTravelCategories());
        return "editPages/editTravelPage";
    }

    @PostMapping("editTravelPost")
    public String editTravelPost(@ModelAttribute Travel editTravel, int destinationId, int accommodationUnitId, int vehicleId) {
        editTravel.setNumberOfNights(travelService.setNumberOfNights(editTravel.getStartDate(), editTravel.getEndDate()));
        travelService.update(editTravel, destinationId, accommodationUnitId, vehicleId);
        return "redirect:/dashboard/travels";
    }

    @PostMapping("deleteTravel")
    public String deleteTravel(@RequestParam int travelId) {
        travelService.delete(travelId);
        return "redirect:/dashboard/travels";
    }
}