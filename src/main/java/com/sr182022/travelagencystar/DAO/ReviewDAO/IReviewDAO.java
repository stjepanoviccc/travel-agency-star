package com.sr182022.travelagencystar.DAO.ReviewDAO;

import com.sr182022.travelagencystar.model.Review;

import java.util.List;

public interface IReviewDAO {
    List<Review> Load();
    List<Review> findAllReviews();
    List<Review> findAllReviewsForSpecificAccommodationUnit(int accommodationUnitId);
    Review findReviewById(int reviewId);
    void addNewReview(Review newReview);
    void editReview(Review editReview);
    void deleteReview(int reviewId);
    int generateNextId();
}
