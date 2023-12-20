package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.AccommodationUnit;
import com.sr182022.travelagencystar.service.IAccommodationUnitService;
import com.sr182022.travelagencystar.utils.CheckRoleUtil;
import jakarta.servlet.http.HttpSession;
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
    public String addNewAccommodationUnit(HttpSession session, @ModelAttribute AccommodationUnit newAccommodationUnit,
                                          @RequestParam int destinationId,
                                          @RequestParam(required = false, defaultValue = "false") boolean checkWifi,
                                          @RequestParam(required = false, defaultValue = "false") boolean checkBathroom,
                                          @RequestParam(required = false, defaultValue = "false") boolean checkTv,
                                          @RequestParam(required = false, defaultValue = "false") boolean checkConditioner)
    {
        try {
            if(!CheckRoleUtil.RoleAdministrator(session)) {
                return ErrorController.permissionErrorReturn;
            }
            accommodationUnitService.setServicesChecking(newAccommodationUnit, checkWifi, checkBathroom, checkTv, checkConditioner);
            accommodationUnitService.save(newAccommodationUnit, destinationId);
            return "redirect:/dashboard/accommodation-units";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }

    @GetMapping("editAccommodationUnit")
    public String editDestination(HttpSession session, @RequestParam int accommodationUnitId, Model model) {
        try {
            if(!CheckRoleUtil.RoleAdministrator(session)) {
                return ErrorController.permissionErrorReturn;
            }
            AccommodationUnit accommodationUnit = accommodationUnitService.findOne(accommodationUnitId);
            if(accommodationUnit == null) {
                return ErrorController.routeErrorReturn;
            }
            model.addAttribute("accommodationUnit", accommodationUnitService.findOne(accommodationUnitId));
            model.addAttribute("accommodationTypesForSelectMenu", accommodationUnitService.findAllAccommodationTypes());
            return "editPages/editAccommodationUnitPage";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }

    }

    @PostMapping("editAccommodationUnitPost")
    public String editDestinationPost(HttpSession session, @ModelAttribute AccommodationUnit accommodationUnit, int destinationId,
                                      boolean checkWifi, boolean checkBathroom, boolean checkTv, boolean checkConditioner) {
        try {
            if(!CheckRoleUtil.RoleAdministrator(session)) {
                return ErrorController.permissionErrorReturn;
            }
            accommodationUnitService.setServicesChecking(accommodationUnit, checkWifi, checkBathroom, checkTv, checkConditioner);
            accommodationUnitService.update(accommodationUnit, destinationId);
            return "redirect:/dashboard/accommodation-units";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }

    @PostMapping("deleteAccommodationUnit")
    public String deleteDestination(HttpSession session, @RequestParam int accommodationUnitId) {
        try {
            if(!CheckRoleUtil.RoleAdministrator(session)) {
                return ErrorController.permissionErrorReturn;
            }
            accommodationUnitService.delete(accommodationUnitId);
            return "redirect:/dashboard/accommodation-units";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }
}
