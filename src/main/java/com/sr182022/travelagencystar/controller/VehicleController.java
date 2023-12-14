package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.model.Vehicle;
import com.sr182022.travelagencystar.service.IDestinationService;
import com.sr182022.travelagencystar.service.IVehicleService;
import com.sr182022.travelagencystar.utils.CheckRoleUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboard/vehicles")
public class VehicleController {

    private final IVehicleService vehicleService;
    private final IDestinationService destinationService;

    @Autowired
    public VehicleController(IVehicleService vehicleService, IDestinationService destinationService) {
        this.vehicleService = vehicleService;
        this.destinationService = destinationService;
    }

    @PostMapping("addNewVehicle")
    public String addNewVehicle(HttpSession session, @ModelAttribute Vehicle newVehicle, @RequestParam int finalDestinationId) {
        if(!CheckRoleUtil.RoleAdministrator(session)) {
            return "redirect:/permission-error";
        }
        vehicleService.save(newVehicle, finalDestinationId);
        return "redirect:/dashboard/vehicles";
    }

    @GetMapping("editVehicle")
    public String editVehicle(HttpSession session, @RequestParam int vehicleId, Model model) {
        if(!CheckRoleUtil.RoleAdministrator(session)) {
            return "redirect:/permission-error";
        }
        Vehicle vehicle = vehicleService.findOne(vehicleId);
        if(vehicle == null) {
            return "redirect:/route-error";
        }
        model.addAttribute("vehicle", vehicleService.findOne(vehicleId));
        model.addAttribute("vehicleTypesForSelectMenu", vehicleService.findAllVehicleTypes());
        model.addAttribute("destinationsForSelectMenu", destinationService.findAll());
        return "editPages/editVehiclePage";
    }

    @PostMapping("editVehiclePost")
    public String editVehiclePost(HttpSession session, @ModelAttribute Vehicle editVehicle, @RequestParam int finalDestinationId) {
        if(!CheckRoleUtil.RoleAdministrator(session)) {
            return "redirect:/permission-error";
        }
        vehicleService.update(editVehicle, finalDestinationId);
        return "redirect:/dashboard/vehicles";
    }

    @PostMapping("deleteVehicle")
    public String deleteVehicle(HttpSession session, @RequestParam int vehicleId) {
        if(!CheckRoleUtil.RoleAdministrator(session)) {
            return "redirect:/permission-error";
        }
        vehicleService.delete(vehicleId);
        return "redirect:/dashboard/vehicles";
    }
}
