package com.sr182022.travelagencystar.service.ReviewService;

import com.sr182022.travelagencystar.DAO.ReviewDAO.ReviewDAO;
import com.sr182022.travelagencystar.model.Review;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService implements IReviewService{

    private final ReviewDAO reviewDAO;
    ServletContext servletContext;

    @Autowired
    public ReviewService(ReviewDAO reviewDAO, ServletContext servletContext) {
        this.reviewDAO = reviewDAO;
        this.servletContext = servletContext;
    }

    @Override
    public List<Review> findAllReviews() {
        return reviewDAO.findAllReviews();
    }

    @Override
    public List<Review> findAllReviewsForSpecificAccommodationUnit(int accommodationUnitId) {
        return reviewDAO.findAllReviewsForSpecificAccommodationUnit(accommodationUnitId);
    }

    @Override
    public Review findReviewById(int reviewId) {
        return reviewDAO.findReviewById(reviewId);
    }

    @Override
    public void addNewReview(Review newReview) {
        reviewDAO.addNewReview(newReview);
    }

    @Override
    public void editReview(Review editReview) {
        reviewDAO.editReview(editReview);
    }

    @Override
    public void deleteReview(int reviewId) {
        reviewDAO.deleteReview(reviewId);
    }

    @Override
    public int generateNextId() {
        return reviewDAO.generateNextId();
    }
}
