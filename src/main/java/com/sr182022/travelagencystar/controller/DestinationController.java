package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.Destination;
import com.sr182022.travelagencystar.service.IDestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboard/destinations")
public class DestinationController {
    private final IDestinationService destinationService;

    @Autowired
    public DestinationController(IDestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @PostMapping("addNewDestination")
    public String addNewDestination(@ModelAttribute Destination newDestination) {
        destinationService.save(newDestination);
        return "redirect:/dashboard/destinations";
    }

    @GetMapping("editDestination")
    public String editDestination(@RequestParam int destinationId, Model model) {
        model.addAttribute("destination", destinationService.findOne(destinationId));
        return "editPages/editDestinationPage";
    }

    @PostMapping("editDestinationPost")
    public String editDestinationPost(@ModelAttribute Destination editDestination) {
        destinationService.update(editDestination);
        return "redirect:/dashboard/destinations";
    }

    @PostMapping("deleteDestination")
    public String deleteDestination(@RequestParam int destinationId) {
        destinationService.delete(destinationId);
        return "redirect:/dashboard/destinations";
    }
}
