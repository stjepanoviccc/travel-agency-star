package com.sr182022.travelagencystar.service.ReviewService;

import com.sr182022.travelagencystar.model.Review;

import java.util.List;

public interface IReviewService {
    List<Review> findAllReviews();
    List<Review> findAllReviewsForSpecificAccommodationUnit(int accommodationUnitId);
    Review findReviewById(int reviewId);
    void addNewReview(Review newReview);
    void editReview(Review editReview);
    void deleteReview(int reviewId);
    int generateNextId();
}
