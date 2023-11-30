package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.AccommodationUnit;
import com.sr182022.travelagencystar.service.AccommodationUnitService.IAccommodationUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboard/accommodation-units")
public class AccommodationUnitController {

    private final IAccommodationUnitService accommodationUnitService;

    @Autowired
    public AccommodationUnitController(IAccommodationUnitService accommodationUnitService) {
        this.accommodationUnitService = accommodationUnitService;
    }

    @PostMapping("addNewAccommodationUnit")
    public String addNewAccommodationUnit(@ModelAttribute AccommodationUnit newAccommodationUnit,
                                          @RequestParam int destinationId,
                                          @RequestParam(required = false, defaultValue = "false") boolean checkWifi,
                                          @RequestParam(required = false, defaultValue = "false") boolean checkBathroom,
                                          @RequestParam(required = false, defaultValue = "false") boolean checkTv,
                                          @RequestParam(required = false, defaultValue = "false") boolean checkConditioner)
    {
        newAccommodationUnit.setHasWifi(checkWifi);
        newAccommodationUnit.setHasBathroom(checkBathroom);
        newAccommodationUnit.setHasTv(checkTv);
        newAccommodationUnit.setHasConditioner(checkConditioner);
        accommodationUnitService.addNewAccommodationUnit(newAccommodationUnit, destinationId);
        return "redirect:/dashboard/accommodation-units";
    }

    @GetMapping("editAccommodationUnit")
    public String editDestination(@RequestParam int accommodationUnitId, Model model) {
        model.addAttribute("accommodationUnit", accommodationUnitService.findAccommodationUnitById(accommodationUnitId));
        model.addAttribute("accommodationTypesForSelectMenu", accommodationUnitService.findAllAccommodationTypes());
        return "editPages/editAccommodationUnitPage";
    }

    @PostMapping("editAccommodationUnitPost")
    public String editDestinationPost(@ModelAttribute AccommodationUnit accommodationUnit, int destinationId) {
        accommodationUnitService.editAccommodationUnit(accommodationUnit, destinationId);
        return "redirect:/dashboard/accommodation-units";
    }

    @PostMapping("deleteAccommodationUnit")
    public String deleteDestination(@RequestParam int accommodationUnitId) {
        accommodationUnitService.deleteAccommodationUnit(accommodationUnitId);
        return "redirect:/dashboard/accommodation-units";
    }

}
