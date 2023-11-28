package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.AccommodationUnit;
import com.sr182022.travelagencystar.model.Destination;
import com.sr182022.travelagencystar.service.AccommodationUnitService.AccommodationUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboard/accommodation-units")
public class AccommodationUnitController {

    private final AccommodationUnitService accommodationUnitService;

    @Autowired
    public AccommodationUnitController(AccommodationUnitService accommodationUnitService) {
        this.accommodationUnitService = accommodationUnitService;
    }

    @PostMapping("addNewAccommodationUnit")
    public String addNewAccommodationUnit(@ModelAttribute AccommodationUnit newAccommodationUnit) {
        accommodationUnitService.addNewAccommodationUnit(newAccommodationUnit);
        return "redirect:/dashboard/accommodation-units";
    }

    @GetMapping("editAccommodationUnit")
    public String editDestination(@RequestParam int accommodationUnitId, Model model) {
        model.addAttribute("destination", accommodationUnitService.findAccommodationUnitById(accommodationUnitId));
        return "editPages/editDestinationPage";
    }

    @PostMapping("editAccommodationUnitPost")
    public String editDestinationPost(@ModelAttribute AccommodationUnit accommodationUnit) {
        accommodationUnitService.editAccommodationUnit(accommodationUnit);
        return "redirect:/dashboard/accommodation-units";
    }

    @PostMapping("deleteAccommodationUnit")
    public String deleteDestination(@RequestParam int accommodationUnitId) {
        accommodationUnitService.deleteAccommodationUnit(accommodationUnitId);
        return "redirect:/dashboard/accommodation-units";
    }

}
