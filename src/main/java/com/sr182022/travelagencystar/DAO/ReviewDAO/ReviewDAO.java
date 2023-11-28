package com.sr182022.travelagencystar.DAO.ReviewDAO;

import com.sr182022.travelagencystar.model.Review;
import com.sr182022.travelagencystar.service.UserService.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ReviewDAO implements IReviewDAO {
    private final ResourceLoader resourceLoader;
    private final UserService userService;
    ServletContext servletContext;
    public static final String REVIEWS_LIST_KEY = "reviewsList";

    @Autowired
    public ReviewDAO(ResourceLoader resourceLoader, UserService userService, ServletContext servletContext) {
        this.resourceLoader = resourceLoader;
        this.userService = userService;
        this.servletContext = servletContext;
    }

    @PostConstruct
    public void init() {
        List<Review> reviewsList = (List<Review>) servletContext.getAttribute(REVIEWS_LIST_KEY);
        reviewsList.addAll(Load());
    }

    @Override
    public List<Review> Load() {
        try {
            List<Review> reviewsList = new ArrayList<>();
            Resource resource = resourceLoader.getResource("classpath:static/testingTextFiles/reviews.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                Review review = new Review();
                review.setId(Integer.parseInt(data[0]));
                review.setAccommodationUnitId(Integer.parseInt(data[1]));
            //    review.setAccommodationUnit(accommodationUnitService.findAccommodationUnitById(accommodationUnitId));

                int userId = Integer.parseInt(data[2]);
                review.setCreator(userService.findUserById(userId));

                review.setMessage(data[3]);
                review.setStars(Integer.parseInt(data[4]));

                reviewsList.add(review);
            }

            return reviewsList;

        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Error processing file reviews.txt", e);
        }
    }

    @Override
    public List<Review> findAllReviews() {
        List<Review> reviewsList = (List<Review>) servletContext.getAttribute(REVIEWS_LIST_KEY);
        return reviewsList;
    }

    @Override
    public List<Review> findAllReviewsForSpecificAccommodationUnit(int accommodationUnitId) {
        return findAllReviews().stream().filter(review -> review.getId() == accommodationUnitId).collect(Collectors.toList());
    }

    @Override
    public Review findReviewById(int reviewId) {
        List<Review> reviewsList = (List<Review>) servletContext.getAttribute(REVIEWS_LIST_KEY);
        Optional<Review> reviewOptional = reviewsList.stream().filter(d -> d.getId() == reviewId).findFirst();
        return reviewOptional.orElse(null);
    }

    @Override
    public void addNewReview(Review newReview) {
//
    }

    @Override
    public void editReview(Review editReview) {
//
    }

    @Override
    public void deleteReview(int reviewId) {
        List<Review> reviewsList = (List<Review>) servletContext.getAttribute(REVIEWS_LIST_KEY);
        reviewsList.remove(findReviewById(reviewId));
    }

    @Override
    public int generateNextId() {
        List<Review> reviewsList = (List<Review>) servletContext.getAttribute(REVIEWS_LIST_KEY);
        return reviewsList.stream().mapToInt(Review::getId).max().orElse(0)+1;
    }
}
