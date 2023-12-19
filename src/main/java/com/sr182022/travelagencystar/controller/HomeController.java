package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.service.ITravelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private final ITravelService travelService;

    public HomeController(ITravelService travelService) {
        this.travelService = travelService;
    }

    @GetMapping
    public String getHomePage(Model model) {
        try {
            model.addAttribute("travelsForCards", travelService.findAll());
            return "index";
        } catch (Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }
}