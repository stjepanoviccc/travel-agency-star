package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReviewController {

    private final IReviewService reviewService;

    @Autowired
    public ReviewController(IReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("deleteReview")
    public String deleteReview(@RequestParam int reviewId) {
        reviewService.delete(reviewId);
        return "redirect:/dashboard/vehicles";
    }
}
