package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TravelController {
    private final TestService testService;

    @Autowired
    public TravelController(TestService testService) {
        this.testService = testService;
    }
    @GetMapping
    public String getHomePage(Model model) {
        return "index";
    }

    // TESTING PURPOSES ONLY!
    @GetMapping("test")
    public String getTestPage(Model model) {
        model.addAttribute("destination", testService.getDestination1());
        return "test";
    }

}