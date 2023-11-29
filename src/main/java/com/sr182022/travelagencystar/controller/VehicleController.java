package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.Vehicle;
import com.sr182022.travelagencystar.service.DestinationService.IDestinationService;
import com.sr182022.travelagencystar.service.VehicleService.IVehicleService;
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
    public String addNewVehicle(@ModelAttribute Vehicle newVehicle, @RequestParam int finalDestinationId) {
        vehicleService.addNewVehicle(newVehicle, finalDestinationId);
        return "redirect:/dashboard/vehicles";
    }

    @GetMapping("editVehicle")
    public String editVehicle(@RequestParam int vehicleId, Model model) {
        model.addAttribute("vehicle", vehicleService.findVehicleById(vehicleId));
        model.addAttribute("vehicleTypesForSelectMenu", vehicleService.findAllVehicleTypes());
        model.addAttribute("destinationsForSelectMenu", destinationService.findAllDestinations());
        return "editPages/editVehiclePage";
    }

    @PostMapping("editVehiclePost")
    public String editVehiclePost(@ModelAttribute Vehicle editVehicle, @RequestParam int finalDestinationId) {
        vehicleService.editVehicle(editVehicle, finalDestinationId);
        return "redirect:/dashboard/vehicles";
    }

    @PostMapping("deleteVehicle")
    public String deleteVehicle(@RequestParam int vehicleId) {
        vehicleService.deleteVehicle(vehicleId);
        return "redirect:/dashboard/vehicles";
    }
}
