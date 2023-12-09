package com.sr182022.travelagencystar.DAO.impl;

import com.sr182022.travelagencystar.DAO.IReviewDAO;
import com.sr182022.travelagencystar.model.Review;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DatabaseReviewDAO implements IReviewDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class ReviewRowCallBackHandler implements RowCallbackHandler {

        private Map<Integer, Review> reviews = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;
            int id_review = resultSet.getInt(index++);
            String review_message = resultSet.getString(index++);
            int review_stars = resultSet.getInt(index++);
            int id_user = resultSet.getInt(index++);
            int id_destination = resultSet.getInt(index++);

            Review review = reviews.get(id_review);
            if (review == null) {
                review = new Review(id_review, review_message, review_stars, id_user, id_destination);
                reviews.put(review.getId(), review);
            }
        }

        public List<Review> getReviews() {
            return new ArrayList<>(reviews.values());
        }
    }

    @Override
    public List<Review> findAll() {
        String sql =
                "SELECT r.id_review, r.review_message, r.review_start, r.id_user, r.id_accommodation_unit " +
                        "FROM review r ORDER BY r.id_review";

        ReviewRowCallBackHandler rowCallBackHandler = new ReviewRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler);
        return rowCallBackHandler.getReviews();
    }

    @Override
    public List<Review> findAllReviewsForSpecificAccommodationUnit(int accommodationUnitId) {
        String sql =
                "SELECT r.id_review, r.review_message, r.review_start, r.id_user, r.id_accommodation_unit " +
                        "FROM review r WHERE r.id_accommodation_unit = ? ";

        ReviewRowCallBackHandler rowCallBackHandler = new ReviewRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, accommodationUnitId);
        return rowCallBackHandler.getReviews();
    }

    @Override
    public List<Review> findAllReviewsForSpecificUser(int userId) {
        String sql =
                "SELECT r.id_review, r.review_message, r.review_start, r.id_user, r.id_accommodation_unit " +
                        "FROM review r WHERE r.id_user = ? ";

        ReviewRowCallBackHandler rowCallBackHandler = new ReviewRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, userId);
        return rowCallBackHandler.getReviews();
    }

    @Override
    public Review findOne(int reviewId) {
        String sql =
                "SELECT r.id_review, r.review_message, r.review_stars, r.id_user, r.id_accommodation_unit " +
                        "FROM review r WHERE r.id_review = ?";

        ReviewRowCallBackHandler rowCallBackHandler = new ReviewRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, reviewId);
        return rowCallBackHandler.getReviews().get(0);
    }

    @Transactional
    @Override
    public void save(Review newReview) {
        //
    }

    @Transactional
    @Override
    public void update(Review editReview) {
        //
    }

    @Transactional
    @Override
    public void delete(int reviewId) {
        String sql = "DELETE FROM review r WHERE r.id_review = ?";
        jdbcTemplate.update(sql, reviewId);
    }
}
