package com.sr182022.travelagencystar.DAO;

import com.sr182022.travelagencystar.model.Review;

import java.util.List;

public interface IReviewDAO {
    List<Review> findAll();
    List<Review> findAllReviewsForSpecificAccommodationUnit(int accommodationUnitId);
    List<Review> findAllReviewsForSpecificUser(int userId);
    Review findOne(int reviewId);
    void save(Review newReview);
    void update(Review editReview);
    void delete(int reviewId);
}
