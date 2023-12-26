package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.AccommodationUnit;
import com.sr182022.travelagencystar.model.Travel;
import com.sr182022.travelagencystar.model.Vehicle;
import com.sr182022.travelagencystar.service.IAccommodationUnitService;
import com.sr182022.travelagencystar.service.IDestinationService;
import com.sr182022.travelagencystar.service.ITravelService;
import com.sr182022.travelagencystar.service.IVehicleService;
import com.sr182022.travelagencystar.utils.CheckRoleUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/dashboard/travels/travel-validation")
    public String getTravelValidationInfo() {
        try {
            return "/validationPages/travelValidationInfo";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
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

    @GetMapping(value = "/travel/filterTravel", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Travel> filterTravels(@RequestParam(required = false) String destination, @RequestParam(required = false) String travelCategory,
                                    @RequestParam(required = false) String travelVehicleType, @RequestParam(required = false) String travelAccUnitType,
                                    @RequestParam(required = false) Float minPrice, @RequestParam(required = false) Float maxPrice,
                                    @RequestParam(required = false) LocalDate startDate, @RequestParam(required = false) LocalDate endDate
                                    ) {
/*
        if(clearFilter == true) {
            return userService.findAll();
        }
        if(StringUtils.isEmpty(username) && StringUtils.isEmpty(role)) {
            return userService.findAll(sortOrder);
        }
        if(StringUtils.isEmpty(username) && role.trim().length() > 0) {
            return userService.findByRole(role, sortOrder);
        }
        if(username.trim().length() > 0 && StringUtils.isEmpty(role)) {
            return userService.findByUsername(username, sortOrder);
        }
        if(username.trim().length() > 0 && role.trim().length() > 0) {
            return userService.findByUsernameAndRole(username, role, sortOrder);
        }

        // just to cancel error show(one of things from up must happen). */
        return travelService.findAll();
    }

    @GetMapping(value = "/dashboard/travels/filterVehiclesByDestination", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Vehicle> filterVehiclesByDestination(@RequestParam int destinationId) {
        List<Vehicle> filteredVehicles = vehicleService.findAll(destinationId);
        return filteredVehicles;
    }

    @GetMapping(value = "/dashboard/travels/filterAccommodationUnitsByDestination", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<AccommodationUnit> filterAccommodationUnitsByDestination(@RequestParam int destinationId) {
        List<AccommodationUnit> filteredUnits = accommodationUnitService.findAll(destinationId);
        return filteredUnits;
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
            boolean validation = travelService.tryValidate(newTravel, destinationId, vehicleId, accommodationUnitId);
            if(!validation) {
                return "redirect:/dashboard/travels/travel-validation";
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

            model.addAttribute("travel", travelService.findOne(travel.getId()));
            model.addAttribute("destinationsForSelectMenu", destinationService.findAll());
            model.addAttribute("vehiclesForSelectMenu", vehicleService.findAll(travel.getDestination().getId()));
            model.addAttribute("accommodationUnitsForSelectMenu", accommodationUnitService.findAll(travel.getDestination().getId()));
            model.addAttribute("travelCategoriesForSelectMenu", travelService.findAllTravelCategories());
            return "editPages/editTravelPage";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }

    @PostMapping("/dashboard/travels/editTravelPost")
    public String editTravelPost(HttpSession session, @ModelAttribute Travel editTravel, int destinationId, Integer accommodationUnitId, Integer vehicleId) {
        try {
            if(!CheckRoleUtil.RoleOrganizer(session)) {
                return ErrorController.permissionErrorReturn;
            }

            if(accommodationUnitId == null || vehicleId == null) {
                return "redirect:/dashboard/travels/travel-validation";
            }

            boolean validation = travelService.tryValidate(editTravel, destinationId, vehicleId, accommodationUnitId);
            if(!validation) {
                return "redirect:/dashboard/travels/travel-validation";
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