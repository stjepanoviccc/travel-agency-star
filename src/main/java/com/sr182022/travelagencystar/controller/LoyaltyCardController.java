package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.service.ILoyaltyCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/loyaltyCard")
public class LoyaltyCardController {

    private final ILoyaltyCardService loyaltyCardService;

    @Autowired
    public LoyaltyCardController(ILoyaltyCardService loyaltyCardService) {
        this.loyaltyCardService = loyaltyCardService;
    }

    @PostMapping("askForLoyaltyCard")
    public String askForLoyaltyCard(@RequestParam int userId) {
        // first it is false so it means manager must activate or delete it.
        loyaltyCardService.save(0, userId, false);
        return "redirect:/profile?id=" + userId;
    }

}
