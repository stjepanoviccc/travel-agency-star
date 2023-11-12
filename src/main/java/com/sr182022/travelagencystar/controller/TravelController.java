package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.AccommodationUnit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TravelController {

    // this is for testing purposes only
    AccommodationUnit accommodation1 = new AccommodationUnit();

    @GetMapping
    public String getHomePage() { return "index"; }

}
