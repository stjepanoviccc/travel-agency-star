package com.sr182022.travelagencystar.service.impl;

import com.sr182022.travelagencystar.DAO.IReviewDAO;
import com.sr182022.travelagencystar.model.Review;
import com.sr182022.travelagencystar.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService implements IReviewService {

    private final IReviewDAO databaseReviewDAO;

    @Autowired
    public ReviewService(IReviewDAO databaseReviewDAO) {
        this.databaseReviewDAO = databaseReviewDAO;
    }

    @Override
    public List<Review> findAll() {
        return databaseReviewDAO.findAll();
    }

    @Override
    public List<Review> findAll(int accUnitId) {
        return databaseReviewDAO.findAll(accUnitId);
    }

    @Override
    public List<Review> findAllReviewsForSpecificAccommodationUnit(int accommodationUnitId) {
        return databaseReviewDAO.findAllReviewsForSpecificAccommodationUnit(accommodationUnitId);
    }

    @Override
    public List<Review> findAllReviewsForSpecificUser(int userId) {
        return databaseReviewDAO.findAllReviewsForSpecificUser(userId);
    }

    @Override
    public Review findOne(int reviewId) {
        return databaseReviewDAO.findOne(reviewId);
    }

    @Override
    public void save(Review newReview) {
        databaseReviewDAO.save(newReview);
    }

    @Override
    public void update(Review editReview) {
        databaseReviewDAO.update(editReview);
    }

    @Override
    public void delete(int reviewId) {
        databaseReviewDAO.delete(reviewId);
    }
}
