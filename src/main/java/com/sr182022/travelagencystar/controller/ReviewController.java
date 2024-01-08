package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.AccommodationUnit;
import com.sr182022.travelagencystar.model.Review;
import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.service.IAccommodationUnitService;
import com.sr182022.travelagencystar.service.IReviewService;
import com.sr182022.travelagencystar.utils.CheckRoleUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final IReviewService reviewService;
    private final IAccommodationUnitService accommodationUnitService;

    @Autowired
    public ReviewController(IReviewService reviewService, IAccommodationUnitService accommodationUnitService) {
        this.reviewService = reviewService;
        this.accommodationUnitService = accommodationUnitService;
    }

    @GetMapping
    public String getReviewsPage(HttpSession session, Model model) {
        if(!CheckRoleUtil.RolePassenger(session)) {
          return ErrorController.permissionErrorReturn;
        };
        model.addAttribute("allReviews", reviewService.findAll());
        model.addAttribute("accUnitsForSelectMenu", accommodationUnitService.findAll());
        return "reviews";
    };

    @PostMapping("addReview")
    public String addReview(HttpSession session, @RequestParam int accommodationUnitId, @RequestParam int userId,
                            @RequestParam String message, @RequestParam int stars) {
        try {
            if(!CheckRoleUtil.RolePassenger(session)) {
                return ErrorController.permissionErrorReturn;
            }
            User u = (User) session.getAttribute("user");
            if(u.getId() != userId) {
                return ErrorController.internalErrorReturn;
            }
            AccommodationUnit ac = accommodationUnitService.findOne(accommodationUnitId);
            Review newReview = new Review(message, stars, u, ac);
            reviewService.save(newReview);

            return "redirect:/reviews";
        } catch(Exception e) {
            System.out.println(e);
            return ErrorController.internalErrorReturn;
        }
    };

    @GetMapping(value = "filterReviewsByAccUnit", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Review> filterReviewByAccUnit(@RequestParam int accommodationUnitId) {
        List<Review> reviews = reviewService.findAll(accommodationUnitId);
        if(reviews != null) {
            return reviews;
        }
        else {
            List<Review> list = new ArrayList<>();
            return list;
        }
    }
}
