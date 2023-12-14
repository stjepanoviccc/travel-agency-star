package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.Destination;
import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.service.IDestinationService;
import com.sr182022.travelagencystar.utils.CheckRoleUtil;
import jakarta.servlet.http.HttpSession;
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
    public String addNewDestination(HttpSession session, @ModelAttribute Destination newDestination) {
        if(!CheckRoleUtil.RoleAdministrator(session)) {
            return "redirect:/permission-error";
        }
        destinationService.save(newDestination);
        return "redirect:/dashboard/destinations";
    }

    @GetMapping("editDestination")
    public String editDestination(HttpSession session, @RequestParam int destinationId, Model model) {
        if(!CheckRoleUtil.RoleAdministrator(session)) {
            return "redirect:/permission-error";
        }
        Destination destination = destinationService.findOne(destinationId);
        if(destination == null) {
            return "redirect:/route-error";
        }
        model.addAttribute("destination", destinationService.findOne(destinationId));
        return "editPages/editDestinationPage";
    }

    @PostMapping("editDestinationPost")
    public String editDestinationPost(HttpSession session, @ModelAttribute Destination editDestination, String imageValue) {
        if(!CheckRoleUtil.RoleAdministrator(session)) {
            return "redirect:/permission-error";
        }
        editDestination.setImage(destinationService.checkImageValueOnChange(editDestination, imageValue));
        destinationService.update(editDestination);
        return "redirect:/dashboard/destinations";
    }

    @PostMapping("deleteDestination")
    public String deleteDestination(HttpSession session, @RequestParam int destinationId) {
        if(!CheckRoleUtil.RoleAdministrator(session)) {
            return "redirect:/permission-error";
        }
        destinationService.delete(destinationId);
        return "redirect:/dashboard/destinations";
    }
}
