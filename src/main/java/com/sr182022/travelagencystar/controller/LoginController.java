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
    public static final String USER_KEY = "user";
    private final IUserService userService;

    @Autowired
    public LoginController(IUserService userService) {
        this.userService = userService;
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

        if(user.getUsername().equals(usernameLogin) && user.getPassword().equals(passwordLogin) && !user.isBlocked()) {
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

        if(user == null) {
            return "redirect:/login-error";
        } else {
            session.removeAttribute(USER_KEY);
            session.invalidate();
        }

        return "redirect:/";
    }
}