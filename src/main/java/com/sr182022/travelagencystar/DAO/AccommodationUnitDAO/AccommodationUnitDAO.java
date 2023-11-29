package com.sr182022.travelagencystar.DAO.AccommodationUnitDAO;

import com.sr182022.travelagencystar.model.*;
import com.sr182022.travelagencystar.service.DestinationService.DestinationService;
import com.sr182022.travelagencystar.service.ReviewService.ReviewService;
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
                accommodationUnit.setDestination(destinationService.findDestinationById(destinationId));

                List<Service> services = Arrays.stream(data[5].split(",")).map(String::trim).map(Service::valueOf).collect(Collectors.toList());
                accommodationUnit.setServices(services);

                accommodationUnit.setAccommodationType(AccommodationType.valueOf(data[6]));

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
    public List<AccommodationUnit> findAllAccommodationUnits() {
        Map<Integer, AccommodationUnit> accommodationUnits = Load();
        return new ArrayList<>(accommodationUnits.values());
    }

    @Override
    public List<AccommodationType> findAllAccommodationTypes() {
        return null;
    }

    @Override
    public AccommodationUnit findAccommodationUnitById(int accommodationUnitId) {
        Map<Integer, AccommodationUnit> accommodationUnits = Load();
        return accommodationUnits.get(accommodationUnitId);
    }

    @Override
    public void addNewAccommodationUnit(AccommodationUnit newAccommodationUnit) {
        //
    }

    @Override
    public void editAccommodationUnit(AccommodationUnit editAccommodationUnit) {
        //
    }

    @Override
    public void deleteAccommodationUnit(int accommodationUnitId) {
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
