package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.*;
import com.sr182022.travelagencystar.service.*;
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
    private final ITravelReservation trService;
    private final ICouponService couponService;

    public TravelController(ITravelService travelService, IDestinationService destinationService, IVehicleService vehicleService,
                            IAccommodationUnitService accommodationUnitService, ITravelReservation trService, ICouponService couponService) {
        this.travelService = travelService;
        this.destinationService = destinationService;
        this.vehicleService = vehicleService;
        this.accommodationUnitService = accommodationUnitService;
        this.trService = trService;
        this.couponService = couponService;
    }

    @GetMapping("/dashboard/travels/travel-validation")
    public String getTravelValidationInfo() {
        try {
            return "/validationPages/travelValidationInfo";
        } catch (Exception e) {
            System.out.println(e);
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
            List<Coupon> allCoupons = couponService.findAll();
            travel = travelService.checkForCoupons(travel, allCoupons);
            int destinationId = travel.getDestination().getId();
            List<Travel> travels = travelService.findAll(destinationId);
            if(travels != null) {
                travels = travelService.checkForCoupons(travels, allCoupons);
            }
            List<TravelReservation> trs = trService.findAll();
            if(!CheckRoleUtil.RoleAdministratorOrOrganizer(session)) {
                travelService.returnOnlyAvailableTravels(session, travels, trs);
            }
            travels = travelService.removeSelectedOne(travel.getId(), travels);

            model.addAttribute("travel", travel);
            model.addAttribute("allTravels", travels);
            model.addAttribute("trs", trs);
            return "/viewPages/travel-details";
        } catch (Exception e) {
            System.out.println(e);
            return ErrorController.internalErrorReturn;
        }
    }

    @GetMapping(value = "/travel/filterTravel", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Travel> filterTravels(HttpSession session,
                                    @RequestParam(required = false) String destination,  @RequestParam(required = false) String destinationSort,
                                      @RequestParam(required = false) String travelCategory, @RequestParam(required = false) String travelCategorySort,
                                    @RequestParam(required = false) String travelVehicleType,  @RequestParam(required = false) String travelVehicleTypeSort,
                                      @RequestParam(required = false) String travelAccUnitType, @RequestParam(required = false) String travelAccUnitTypeSort,
                                    @RequestParam(required = false) Float minPrice, @RequestParam(required = false) Float maxPrice,
                                      @RequestParam(required = false) String priceSort,
                                    @RequestParam(required = false) LocalDate startDate,@RequestParam(required = false) LocalDate endDate,
                                      @RequestParam(required = false) String dateSort,
                                      @RequestParam(required = false) Integer nightsFrom, @RequestParam(required = false) Integer nightsTo,
                                      @RequestParam(required = false) String nightsSort,
                                      @RequestParam(required = false) Integer passengersAvailability,
                                      @RequestParam(required = false) String sortTravelByPassengersAvailability,
                                      @RequestParam(required = false) String inputID, @RequestParam(required = false) String sortTravelByID
                                    ) {
        List<TravelReservation> trs = trService.findAll();
        List<Travel> allTravels = travelService.findAll(destination, destinationSort, travelCategory, travelCategorySort, travelVehicleType, travelVehicleTypeSort,
                travelAccUnitType, travelAccUnitTypeSort, minPrice, maxPrice, priceSort, startDate, endDate, dateSort,
                nightsFrom, nightsTo, nightsSort, passengersAvailability, sortTravelByPassengersAvailability, inputID, sortTravelByID);
        if(!CheckRoleUtil.RoleAdministratorOrOrganizer(session)) {
            travelService.returnOnlyAvailableTravels(session, allTravels, trs);
        }

        return allTravels;
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
            System.out.println(e);
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
            System.out.println(e);
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
            System.out.println(e);
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
            System.out.println(e);
            return ErrorController.internalErrorReturn;
        }
    }
}