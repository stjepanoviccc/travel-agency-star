package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.Travel;
import com.sr182022.travelagencystar.service.ITravelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboard/travels")
public class TravelController {

    private final ITravelService travelService;

    public TravelController(ITravelService travelService) {
        this.travelService = travelService;
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
        return "editPages/editTravelPage";
    }

    @PostMapping("editTravelPost")
    public String editTravelPost(@ModelAttribute Travel editTravel) {
        travelService.update(editTravel);
        return "redirect:/dashboard/travels";
    }

    @PostMapping("deleteTravel")
    public String deleteTravel(@RequestParam int travelId) {
        travelService.delete(travelId);
        return "redirect:/dashboard/travels";
    }
}