package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.Travel;
import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.service.IAccommodationUnitService;
import com.sr182022.travelagencystar.service.IDestinationService;
import com.sr182022.travelagencystar.service.ITravelService;
import com.sr182022.travelagencystar.service.IVehicleService;
import com.sr182022.travelagencystar.utils.CheckRoleUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
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

    @GetMapping("/travel")
    public String travelDetailsPage(HttpSession session, @RequestParam int id, Model model) {
        if(!CheckRoleUtil.RoleOrganizer(session)) {
            return "redirect:/permission-error";
        }

        Travel travel = travelService.findOne(id);
        /*  if(travel == null) {
            return "redirect:/errorPages/routeErrorPage.html";
        } */
        model.addAttribute("travel", travel);
        return "/viewPages/travel-details";
    }

    @PostMapping("/dashboard/travels/addNewTravel")
    public String addNewDestination(HttpSession session, @ModelAttribute Travel newTravel,
                                    @RequestParam int destinationId,
                                    @RequestParam int accommodationUnitId,
                                    @RequestParam int vehicleId) {
        if(!CheckRoleUtil.RoleOrganizer(session)) {
            return "redirect:/permission-error";
        }
        if(newTravel.getStartDate() == null || newTravel.getEndDate() == null) {
            // handle here
        }
        newTravel.setNumberOfNights(travelService.setNumberOfNights(newTravel.getStartDate(), newTravel.getEndDate()));
        travelService.save(newTravel, destinationId, accommodationUnitId, vehicleId);
        return "redirect:/dashboard/travels";
    }

    @GetMapping("/dashboard/travels/editTravel")
    public String editTravel(HttpSession session, @RequestParam int travelId, Model model) {
        if(!CheckRoleUtil.RoleOrganizer(session)) {
            return "redirect:/permission-error";
        }
        Travel travel = travelService.findOne(travelId);
        if(travel == null) {
            return "redirect:/route-error";
        }
        model.addAttribute("travel", travelService.findOne(travelId));
        model.addAttribute("destinationsForSelectMenu", destinationService.findAll());
        model.addAttribute("vehiclesForSelectMenu", vehicleService.findAll());
        model.addAttribute("accommodationUnitsForSelectMenu", accommodationUnitService.findAll());
        model.addAttribute("travelCategoriesForSelectMenu", travelService.findAllTravelCategories());
        return "editPages/editTravelPage";
    }

    @PostMapping("/dashboard/travels/editTravelPost")
    public String editTravelPost(HttpSession session, @ModelAttribute Travel editTravel, int destinationId, int accommodationUnitId, int vehicleId) {
        if(!CheckRoleUtil.RoleOrganizer(session)) {
            return "redirect:/permission-error";
        }
        editTravel.setNumberOfNights(travelService.setNumberOfNights(editTravel.getStartDate(), editTravel.getEndDate()));
        travelService.update(editTravel, destinationId, accommodationUnitId, vehicleId);
        return "redirect:/dashboard/travels";
    }

    @PostMapping("/dashboard/travels/deleteTravel")
    public String deleteTravel(HttpSession session, @RequestParam int travelId) {
        if(!CheckRoleUtil.RoleOrganizer(session)) {
            return "redirect:/permission-error";
        }
        travelService.delete(travelId);
        return "redirect:/dashboard/travels";
    }
}