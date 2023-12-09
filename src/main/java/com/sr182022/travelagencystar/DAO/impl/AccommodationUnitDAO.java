package com.sr182022.travelagencystar.DAO.impl;

import com.sr182022.travelagencystar.DAO.IAccommodationUnitDAO;
import com.sr182022.travelagencystar.model.*;
import com.sr182022.travelagencystar.service.impl.DestinationService;
import com.sr182022.travelagencystar.service.impl.ReviewService;
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

/*
@Repository
public class AccommodationUnitDAO implements IAccommodationUnitDAO {
    @Value("${accommodationUnits.pathToFile}")
    private String pathToFile;
    private final DestinationService destinationService;
    private final ReviewService reviewService;

    @Autowired
    public AccommodationUnitDAO(DestinationService destinationService, ReviewService reviewService) {
        this.destinationService = destinationService;
        this.reviewService = reviewService;
    }

    public Map<Integer, AccommodationUnit> Load() {
        try {
            Map<Integer, AccommodationUnit> accommodationUnits = new HashMap<>();
            Path path = Paths.get(pathToFile);
            List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));

            for (String line : lines) {
                line = line.trim();
                if (line.equals("") || line.indexOf('#') == 0)
                    continue;

                String[] data = line.split("\\|");
                AccommodationUnit accommodationUnit = new AccommodationUnit();

                accommodationUnit.setId(Integer.parseInt(data[0]));
                // im getting all reviews based on my accommodation unit id
                accommodationUnit.setReviews(reviewService.findAllReviewsForSpecificAccommodationUnit(Integer.parseInt(data[0])));

                accommodationUnit.setName(data[1]);
                accommodationUnit.setCapacity(Integer.parseInt(data[2]));
                accommodationUnit.setDescription(data[3]);

                int destinationId = Integer.parseInt(data[4]);
                accommodationUnit.setDestination(destinationService.findOne(destinationId));

                accommodationUnit.setAccommodationType(AccommodationType.valueOf(data[5]));
                accommodationUnit.setHasWifi(Boolean.parseBoolean(data[6]));
                accommodationUnit.setHasBathroom(Boolean.parseBoolean(data[7]));
                accommodationUnit.setHasTv(Boolean.parseBoolean(data[8]));
                accommodationUnit.setHasConditioner(Boolean.parseBoolean(data[9]));

                accommodationUnits.put(accommodationUnit.getId(), accommodationUnit);
            }

            return accommodationUnits;

        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Error processing file accommodationUnits.txt", e);
        }
    }

    public void Save(Map<Integer, AccommodationUnit> accommodationUnits) {

        try {
            Path path = Paths.get(pathToFile);
            List<String> lines = new ArrayList<>();

            for (AccommodationUnit au : accommodationUnits.values()) {
                lines.add(au.toFileString());
            }

            Files.write(path, lines, Charset.forName("UTF-8"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<AccommodationUnit> findAll() {
        Map<Integer, AccommodationUnit> accommodationUnits = Load();
        return new ArrayList<>(accommodationUnits.values());
    }

    @Override
    public AccommodationUnit findOne(int accommodationUnitId) {
        Map<Integer, AccommodationUnit> accommodationUnits = Load();
        return accommodationUnits.get(accommodationUnitId);
    }

    @Override
    public void save(AccommodationUnit newAccommodationUnit, int destinationId) {
        Map<Integer, AccommodationUnit> accommodationUnits = Load();
        newAccommodationUnit.setId(generateNextId());
        newAccommodationUnit.setDestination(destinationService.findOne(destinationId));
        accommodationUnits.put(newAccommodationUnit.getId(), newAccommodationUnit);
        Save(accommodationUnits);
    }

    @Override
    public void update(AccommodationUnit editAccommodationUnit, int destinationId) {
        Map<Integer, AccommodationUnit> accommodationUnits = Load();
        AccommodationUnit existingAccommodationUnit = findOne(editAccommodationUnit.getId());

        existingAccommodationUnit.setName(editAccommodationUnit.getName());
        existingAccommodationUnit.setCapacity(editAccommodationUnit.getCapacity());
        existingAccommodationUnit.setReviews(editAccommodationUnit.getReviews());
        existingAccommodationUnit.setDescription(editAccommodationUnit.getDescription());
        existingAccommodationUnit.setDestination(destinationService.findOne(destinationId));
        existingAccommodationUnit.setAccommodationType(editAccommodationUnit.getAccommodationType());
        existingAccommodationUnit.setHasWifi(editAccommodationUnit.isWifi());
        existingAccommodationUnit.setHasBathroom(editAccommodationUnit.isBathroom());
        existingAccommodationUnit.setHasTv(editAccommodationUnit.isTv());
        existingAccommodationUnit.setHasConditioner(editAccommodationUnit.isConditioner());

        accommodationUnits.put(editAccommodationUnit.getId(), existingAccommodationUnit);
        Save(accommodationUnits);
    }

    @Override
    public void delete(int accommodationUnitId) {
        Map<Integer, AccommodationUnit> accommodationUnits = Load();
        accommodationUnits.remove(accommodationUnitId);
        Save(accommodationUnits);
    }

    public int generateNextId() {
        Map<Integer, AccommodationUnit> accommodationUnits = Load();
        int nextId = 1;
        for (int id : accommodationUnits.keySet()) {
            if(nextId<id)
                nextId=id;
        }
        return ++nextId;
    }
}
*/