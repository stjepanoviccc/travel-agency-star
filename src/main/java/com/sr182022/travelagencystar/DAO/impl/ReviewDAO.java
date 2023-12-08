package com.sr182022.travelagencystar.DAO.impl;

import com.sr182022.travelagencystar.DAO.IReviewDAO;
import com.sr182022.travelagencystar.model.Review;
import com.sr182022.travelagencystar.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ReviewDAO implements IReviewDAO {
    @Value("${reviews.pathToFile}")
    private String pathToFile;
    private final UserService userService;

    @Autowired
    public ReviewDAO(UserService userService) {
        this.userService = userService;
    }

    public Map<Integer, Review> Load() {
        try {
            Map<Integer, Review> reviews = new HashMap<>();
            Path path = Paths.get(pathToFile);
            List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));

            for (String line : lines) {
                line = line.trim();
                if (line.equals("") || line.indexOf('#') == 0)
                    continue;

                String[] data = line.split("\\|");
                Review review = new Review();

                review.setId(Integer.parseInt(data[0]));
                review.setAccommodationUnitId(Integer.parseInt(data[1]));

                int userId = Integer.parseInt(data[2]);
                review.setCreator(userService.findOne(userId));

                review.setMessage(data[3]);
                review.setStars(Integer.parseInt(data[4]));

                reviews.put(review.getId(), review);
            }

            return reviews;

        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Error processing file reviews.txt", e);
        }
    }

    public void Save(Map<Integer, Review> reviews) {

        try {
            Path path = Paths.get(pathToFile);
            List<String> lines = new ArrayList<>();

            for (Review r : reviews.values()) {
                lines.add(r.toFileString());
            }

            Files.write(path, lines, Charset.forName("UTF-8"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Review> findAll() {
        Map<Integer, Review> reviews = Load();
        return new ArrayList<>(reviews.values());
    }

    @Override
    public List<Review> findAllReviewsForSpecificAccommodationUnit(int accommodationUnitId) {
        return findAll().stream().filter(review -> review.getId() == accommodationUnitId).collect(Collectors.toList());
    }

    @Override
    public Review findOne(int reviewId) {
        Map<Integer, Review> reviews = Load();
        return reviews.get(reviewId);
    }

    @Override
    public void save(Review newReview) {
//
    }

    @Override
    public void update(Review editReview) {
//
    }

    @Override
    public void delete(int reviewId) {
        Map<Integer, Review> reviews = Load();
        reviews.remove(reviewId);
        Save(reviews);
    }

    public int generateNextId() {
        Map<Integer, Review> reviews = Load();
        int nextId = 1;
        for (int id : reviews.keySet()) {
            if(nextId<id)
                nextId=id;
        }
        return ++nextId;
    }
}
