package com.sr182022.travelagencystar.service;

import com.sr182022.travelagencystar.model.Review;

import java.util.List;

public interface IReviewService {
    List<Review> findAll();
    List<Review> findAllReviewsForSpecificAccommodationUnit(int accommodationUnitId);
    Review findOne(int reviewId);
    void save(Review newReview);
    void update(Review editReview);
    void delete(int reviewId);
    int generateNextId();
}
