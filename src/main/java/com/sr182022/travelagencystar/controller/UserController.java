package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.service.IUserService;
import com.sr182022.travelagencystar.utils.CheckRoleUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Controller
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login-info")
    public String getInfoAfterRegistration() {
        try {
            return "/viewPages/login-info";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }

    @GetMapping("/user-validation")
    public String getUserValidationInfo() {
        try {
            return "/validationPages/userValidationInfo";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }

    @PostMapping("/addNewUser")
    public String addNewUser(HttpSession session, @ModelAttribute User newUser) {
        try {
            if(!CheckRoleUtil.RoleAdministratorOrNull(session)) {
                return ErrorController.permissionErrorReturn;
            }

            boolean validation = userService.tryValidate(newUser, false);
            if(!validation) {
                return "redirect:/user-validation";
            }
            userService.save(newUser);

            if(!CheckRoleUtil.RoleAdministrator(session)) {
                return "redirect:/login-info";
            }
            return "redirect:/dashboard";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }

    // in this /editUser im also adding deactivateUser because thats in details page.
    @GetMapping("/editUser")
    public String editUser(HttpSession session, @RequestParam int userId, Model model) {
        try {
            if(!CheckRoleUtil.RoleAdministratorOrNull(session)) {
                return ErrorController.permissionErrorReturn;
            }
            User user = userService.findOne(userId);
            if(user == null) {
                return ErrorController.routeErrorReturn;
            }
            model.addAttribute("user", user);
            return "editPages/editUserPage";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }

    @PostMapping("/editUserPost")
    public String editUserPost(HttpSession session, @ModelAttribute User editUser) {
        try {
            boolean validation = userService.tryValidate(editUser, true);
            if(!validation) {
                return "redirect:/user-validation";
            }

            userService.update(editUser);
            if(!CheckRoleUtil.RoleAdministrator(session)) {
                return "redirect:/profile?id=" + editUser.getId();
            }
            return "redirect:/dashboard";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }

    @PostMapping("/deleteUser")
    public String deleteUser(HttpSession session, @RequestParam int userId) {
        try {
            if(!CheckRoleUtil.RoleAdministratorOrNull(session)) {
                return ErrorController.permissionErrorReturn;
            }
            userService.delete(userId);
            if(!CheckRoleUtil.RoleAdministrator(session)) {
                return "redirect:/";
            }
            return "redirect:/dashboard";
        } catch(Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }

    @PostMapping("/deactivateUser")
    public String deactivateUser(HttpSession session, @RequestParam int userId, @RequestParam boolean userIsBlocked) {
        try {
            if(!CheckRoleUtil.RoleAdministrator(session)) {
                return ErrorController.permissionErrorReturn;
            }
            // deactivating
            userService.delete(userId, userIsBlocked);

            return "redirect:/dashboard";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }

    @PostMapping("/reactivateUser")
    public String reactivateUser(HttpSession session, @RequestParam int userId, @RequestParam boolean userIsBlocked) {
        try {
            if(!CheckRoleUtil.RoleAdministrator(session)) {
                return ErrorController.permissionErrorReturn;
            }
            // reactivating
            userService.delete(userId, userIsBlocked);

            return "redirect:/dashboard";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }

    @GetMapping(value = "/filterDashboardUser", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<User> filterDashboardUsers(@RequestParam(required = false) String username, @RequestParam(required = false) String role,
                                           @RequestParam(required = false) boolean clearFilter, @RequestParam(required = false) String sortOrder) {

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

        // just to cancel error show(one of things from up must happen).
        return userService.findAll();
    }

    @GetMapping(value="/getUserFromSession", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserFromSession(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return ResponseEntity.ok(user);
    }
}