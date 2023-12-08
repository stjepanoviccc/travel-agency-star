package com.sr182022.travelagencystar.service.impl;

import com.sr182022.travelagencystar.DAO.impl.ReviewDAO;
import com.sr182022.travelagencystar.model.Review;
import com.sr182022.travelagencystar.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService implements IReviewService {

    private final ReviewDAO reviewDAO;

    @Autowired
    public ReviewService(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }

    @Override
    public List<Review> findAll() {
        return reviewDAO.findAll();
    }

    @Override
    public List<Review> findAllReviewsForSpecificAccommodationUnit(int accommodationUnitId) {
        return reviewDAO.findAllReviewsForSpecificAccommodationUnit(accommodationUnitId);
    }

    @Override
    public Review findOne(int reviewId) {
        return reviewDAO.findOne(reviewId);
    }

    @Override
    public void save(Review newReview) {
        reviewDAO.save(newReview);
    }

    @Override
    public void update(Review editReview) {
        reviewDAO.update(editReview);
    }

    @Override
    public void delete(int reviewId) {
        reviewDAO.delete(reviewId);
    }

    @Override
    public int generateNextId() {
        return reviewDAO.generateNextId();
    }
}
