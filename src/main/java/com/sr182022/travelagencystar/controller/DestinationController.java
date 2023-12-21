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

    @GetMapping("destination-validation")
    public String getDestinationValidationInfo() {
        try {
            return "/validationPages/destinationValidationInfo";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }

    @PostMapping("addNewDestination")
    public String addNewDestination(HttpSession session, @ModelAttribute Destination newDestination) {
        try {
            if(!CheckRoleUtil.RoleAdministrator(session)) {
                return ErrorController.permissionErrorReturn;
            }

            boolean validation = destinationService.tryValidate(newDestination);
            if(!validation) {
                return "redirect:/dashboard/destinations/destination-validation";
            }
            destinationService.save(newDestination);

            return "redirect:/dashboard/destinations";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }

    @GetMapping("editDestination")
    public String editDestination(HttpSession session, @RequestParam int destinationId, Model model) {
        try {
            if(!CheckRoleUtil.RoleAdministrator(session)) {
                return ErrorController.permissionErrorReturn;
            }
            Destination destination = destinationService.findOne(destinationId);
            if(destination == null) {
                return ErrorController.routeErrorReturn;
            }
            model.addAttribute("destination", destinationService.findOne(destinationId));
            return "editPages/editDestinationPage";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }

    @PostMapping("editDestinationPost")
    public String editDestinationPost(HttpSession session, @ModelAttribute Destination editDestination, String imageValue) {
        try {
            if(!CheckRoleUtil.RoleAdministrator(session)) {
                return ErrorController.permissionErrorReturn;
            }
            editDestination.setImage(destinationService.checkImageValueOnChange(editDestination, imageValue));

            boolean validation = destinationService.tryValidate(editDestination);
            if(!validation) {
                return "redirect:/dashboard/destinations/destination-validation";
            }
            destinationService.update(editDestination);

            return "redirect:/dashboard/destinations";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }

    @PostMapping("deleteDestination")
    public String deleteDestination(HttpSession session, @RequestParam int destinationId) {
        try {
            if(!CheckRoleUtil.RoleAdministrator(session)) {
                return ErrorController.permissionErrorReturn;
            }
            destinationService.delete(destinationId);
            return "redirect:/dashboard/destinations";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }
}
