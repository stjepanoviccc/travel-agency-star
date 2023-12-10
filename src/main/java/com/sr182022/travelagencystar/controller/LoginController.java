package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    /*
    @GetMapping("/login")
    public void getLogin(@RequestParam(required = false) String usernameLogin, @RequestParam(required = false) String passwordLogin,
                         HttpSession session, Model model) {
        postLogin(usernameLogin, passwordLogin, session, model);
    } */

    @PostMapping("/login")
    public String postLogin(@RequestParam(required = false) String usernameLogin, @RequestParam(required = false) String passwordLogin,
                          HttpSession session, Model model) {
        User user = userService.findOne(usernameLogin);
        String errorMessage = "";

        // validations
        if(user == null) {
            errorMessage = "User not found.";
            model.addAttribute("errorMessage", errorMessage);
        }

        if(session.getAttribute(USER_KEY) != null) {
            errorMessage = "You are already logged in. Please log out.";
            model.addAttribute("errorMessage", errorMessage);
        }

        if(user != null) {
            if(user.getUsername().equals(usernameLogin) && user.getPassword().equals(passwordLogin)) {
                session.setAttribute(USER_KEY, user);
                errorMessage = "Login successful!";
                model.addAttribute("errorMessage", errorMessage);
            } else {
                errorMessage = "Username and password doesn't match!";
                model.addAttribute("errorMessage", errorMessage);
            }
        }

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        User user = (User) session.getAttribute(USER_KEY);
        String errorMessage = "";

        if(user == null) {
            errorMessage = "User not logged in.";
            model.addAttribute("errorMessage", errorMessage);
        } else {
            session.removeAttribute(USER_KEY);
            session.invalidate();
        }

        return "redirect:/index";
    }
}