package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.Travel;
import com.sr182022.travelagencystar.service.IAccommodationUnitService;
import com.sr182022.travelagencystar.service.IDestinationService;
import com.sr182022.travelagencystar.service.ITravelService;
import com.sr182022.travelagencystar.service.IVehicleService;
import com.sr182022.travelagencystar.utils.CheckRoleUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        try {
            Travel travel = travelService.findOne(id);
            if(travel == null) {
                return ErrorController.routeErrorReturn;
            }

            int destinationId = travel.getDestination().getId();
            List<Travel> travels = travelService.findAll(destinationId);
            travels = travelService.removeSelectedOne(travel.getId(), travels);

            model.addAttribute("travel", travel);
            model.addAttribute("allTravels", travels);
            return "/viewPages/travel-details";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }

    @PostMapping("/dashboard/travels/addNewTravel")
    public String addNewDestination(HttpSession session, @ModelAttribute Travel newTravel,
                                    @RequestParam int destinationId,
                                    @RequestParam int accommodationUnitId,
                                    @RequestParam int vehicleId) {
        try {
            if(!CheckRoleUtil.RoleOrganizer(session)) {
                return ErrorController.permissionErrorReturn;
            }
            if(newTravel.getStartDate() == null || newTravel.getEndDate() == null) {
                // handle here
            }
            newTravel.setNumberOfNights(travelService.setNumberOfNights(newTravel.getStartDate(), newTravel.getEndDate()));
            travelService.save(newTravel, destinationId, accommodationUnitId, vehicleId);
            return "redirect:/dashboard/travels";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }

    }

    @GetMapping("/dashboard/travels/editTravel")
    public String editTravel(HttpSession session, @RequestParam int travelId, Model model) {
        try {
            if(!CheckRoleUtil.RoleOrganizer(session)) {
                return ErrorController.permissionErrorReturn;
            }
            Travel travel = travelService.findOne(travelId);
            if(travel == null) {
                return ErrorController.routeErrorReturn;
            }
            model.addAttribute("travel", travelService.findOne(travelId));
            model.addAttribute("destinationsForSelectMenu", destinationService.findAll());
            model.addAttribute("vehiclesForSelectMenu", vehicleService.findAll());
            model.addAttribute("accommodationUnitsForSelectMenu", accommodationUnitService.findAll());
            model.addAttribute("travelCategoriesForSelectMenu", travelService.findAllTravelCategories());
            return "editPages/editTravelPage";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }

    @PostMapping("/dashboard/travels/editTravelPost")
    public String editTravelPost(HttpSession session, @ModelAttribute Travel editTravel, int destinationId, int accommodationUnitId, int vehicleId) {
        try {
            if(!CheckRoleUtil.RoleOrganizer(session)) {
                return ErrorController.permissionErrorReturn;
            }
            editTravel.setNumberOfNights(travelService.setNumberOfNights(editTravel.getStartDate(), editTravel.getEndDate()));
            travelService.update(editTravel, destinationId, accommodationUnitId, vehicleId);
            return "redirect:/dashboard/travels";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }

    @PostMapping("/dashboard/travels/deleteTravel")
    public String deleteTravel(HttpSession session, @RequestParam int travelId) {
        try {
            if(!CheckRoleUtil.RoleOrganizer(session)) {
                return ErrorController.permissionErrorReturn;
            }
            travelService.delete(travelId);
            return "redirect:/dashboard/travels";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }
}