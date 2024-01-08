package com.sr182022.travelagencystar.DAO.impl;

import com.sr182022.travelagencystar.DAO.IReviewDAO;
import com.sr182022.travelagencystar.model.AccommodationUnit;
import com.sr182022.travelagencystar.model.Destination;
import com.sr182022.travelagencystar.model.Review;
import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.service.IAccommodationUnitService;
import com.sr182022.travelagencystar.service.IUserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DatabaseReviewDAO implements IReviewDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final IUserService userService;
    private final IAccommodationUnitService accommodationUnitService;

    public DatabaseReviewDAO(IUserService userService, IAccommodationUnitService accommodationUnitService) {
        this.userService = userService;
        this.accommodationUnitService = accommodationUnitService;
    }

    private class ReviewRowCallBackHandler implements RowCallbackHandler {

        private Map<Integer, Review> reviews = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;
            int id_review = resultSet.getInt(index++);
            String review_message = resultSet.getString(index++);
            int review_stars = resultSet.getInt(index++);
            int id_user = resultSet.getInt(index++);
            int id_accommodation_unit = resultSet.getInt(index++);

            User u = userService.findOne(id_user);
            AccommodationUnit ac = accommodationUnitService.findOne(id_accommodation_unit);
            Review review = reviews.get(id_review);

            if (review == null) {
                review = new Review(id_review, review_message, review_stars, u, ac);
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
                "SELECT r.id_review, r.review_message, r.review_stars, r.id_user, r.id_accommodation_unit " +
                        "FROM review r ORDER BY r.id_review";

        ReviewRowCallBackHandler rowCallBackHandler = new ReviewRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler);
        return rowCallBackHandler.getReviews();
    }

    @Override
    public List<Review> findAll(int accUnitId) {
        String sql =
                "SELECT r.id_review, r.review_message, r.review_stars, r.id_user, r.id_accommodation_unit " +
                        "FROM review r WHERE r.id_accommodation_unit = ? ORDER BY r.id_review";

        ReviewRowCallBackHandler rowCallBackHandler = new ReviewRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, accUnitId);
        List<Review> reviewsList = rowCallBackHandler.getReviews();
        if(!reviewsList.isEmpty()) {
            return reviewsList;
        } else {
            return null;
        }
    }

    @Override
    public List<Review> findAllReviewsForSpecificAccommodationUnit(int accommodationUnitId) {
        String sql =
                "SELECT r.id_review, r.review_message, r.review_stars, r.id_user, r.id_accommodation_unit " +
                        "FROM review r WHERE r.id_accommodation_unit = ? ";

        ReviewRowCallBackHandler rowCallBackHandler = new ReviewRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, accommodationUnitId);
        List<Review> reviewsList = rowCallBackHandler.getReviews();
        if(!reviewsList.isEmpty()) {
            return reviewsList;
        } else {
            return null;
        }
    }

    @Override
    public List<Review> findAllReviewsForSpecificUser(int userId) {
        String sql =
                "SELECT r.id_review, r.review_message, r.review_stars, r.id_user, r.id_accommodation_unit " +
                        "FROM review r WHERE r.id_user = ? ";

        ReviewRowCallBackHandler rowCallBackHandler = new ReviewRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, userId);
        List<Review> reviewsList = rowCallBackHandler.getReviews();
        if(!reviewsList.isEmpty()) {
            return reviewsList;
        } else {
            return null;
        }
    }

    @Override
    public Review findOne(int reviewId) {
        String sql =
                "SELECT r.id_review, r.review_message, r.review_stars, r.id_user, r.id_accommodation_unit " +
                        "FROM review r WHERE r.id_review = ?";

        ReviewRowCallBackHandler rowCallBackHandler = new ReviewRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, reviewId);
        List<Review> reviewsList = rowCallBackHandler.getReviews();
        if(!reviewsList.isEmpty()) {
            return reviewsList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public void save(Review newReview) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "INSERT INTO review (review_message, review_stars, id_user, id_accommodation_unit) " +
                        "VALUES (?, ?, ?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setString(index++, newReview.getMessage());
                preparedStatement.setInt(index++, newReview.getStars());
                preparedStatement.setInt(index++, newReview.getCreator().getId());
                preparedStatement.setInt(index++, newReview.getAccommodationUnit().getId());

                return preparedStatement;
            }
        };

        jdbcTemplate.update(preparedStatementCreator);
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
