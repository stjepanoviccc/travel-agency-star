package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private final IUserService userService;
    private static final String USER_KEY = "user";

    @Autowired
    public LoginController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login-error")
    public String getInformationPage() {
        return "/errorPages/loginErrorPage";
    }

    @PostMapping("/login")
    public String postLogin(@RequestParam(required = false) String usernameLogin, @RequestParam(required = false) String passwordLogin, HttpSession session) {
        User user = userService.findOne(usernameLogin);

        // validations
        if(user == null || session.getAttribute(USER_KEY) != null) {
            return "redirect:/login-error";
        }

        if(session.getAttribute(USER_KEY) != null) {
            return "redirect:/login-error";
        }

        if(user.getUsername().equals(usernameLogin) && user.getPassword().equals(passwordLogin)) {
            session.setAttribute(USER_KEY, user);
            return "redirect:/";
        } else {
            // username and password doesn't match
            return "redirect:/login-error";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        User user = (User) session.getAttribute(USER_KEY);
        String infoMessage = "";

        if(user == null) {
            infoMessage = "User not logged in.";
            session.setAttribute("infoMessage", infoMessage);
        } else {
            infoMessage = "Logged out.";
            session.setAttribute("infoMessage", infoMessage);
            session.removeAttribute(USER_KEY);
            session.invalidate();
        }

        return "redirect:/login-information";
    }
}