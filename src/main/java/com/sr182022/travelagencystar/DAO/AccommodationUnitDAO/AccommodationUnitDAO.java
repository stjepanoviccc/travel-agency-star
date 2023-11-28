package com.sr182022.travelagencystar.DAO.AccommodationUnitDAO;

import com.sr182022.travelagencystar.model.AccommodationUnit;
import com.sr182022.travelagencystar.model.AccommodationType;
import com.sr182022.travelagencystar.model.Service;
import com.sr182022.travelagencystar.service.DestinationService.DestinationService;
import com.sr182022.travelagencystar.service.ReviewService.ReviewService;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class AccommodationUnitDAO implements IAccommodationUnitDAO {
    private final DestinationService destinationService;
    private final ReviewService reviewService;
    private final ResourceLoader resourceLoader;
    ServletContext servletContext;
    public static final String ACCOMMODATION_UNITS_LIST_KEY = "accommodationUnitsList";

    @Autowired
    public AccommodationUnitDAO(ResourceLoader resourceLoader, DestinationService destinationService, ReviewService reviewService, ServletContext servletContext) {
        this.destinationService = destinationService;
        this.reviewService = reviewService;
        this.resourceLoader = resourceLoader;
        this.servletContext = servletContext;
    }

    @PostConstruct
    public void init() {
        List<AccommodationUnit> accommodationUnitsList = (List<AccommodationUnit>) servletContext.getAttribute(ACCOMMODATION_UNITS_LIST_KEY);
        accommodationUnitsList.addAll(Load());
    }

    @Override
    public List<AccommodationUnit> Load() {
        try {
            List<AccommodationUnit> accommodationUnitsList = new ArrayList<>();
            Resource resource = resourceLoader.getResource("classpath:static/testingTextFiles/accommodationUnits.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
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

                accommodationUnitsList.add(accommodationUnit);
            }

            return accommodationUnitsList;

        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Error processing file accommodationUnits.txt", e);
        }
    }

    @Override
    public List<AccommodationUnit> findAllAccommodationUnits() {
        List<AccommodationUnit> accommodationUnitsList = (List<AccommodationUnit>) servletContext.getAttribute(ACCOMMODATION_UNITS_LIST_KEY);
        return accommodationUnitsList;
    }

    @Override
    public AccommodationUnit findAccommodationUnitById(int accommodationUnitId) {
        List<AccommodationUnit> accommodationUnitsList = (List<AccommodationUnit>) servletContext.getAttribute(ACCOMMODATION_UNITS_LIST_KEY);
        Optional<AccommodationUnit> accommodationUnitOptional = accommodationUnitsList.stream().filter(au -> au.getId() == accommodationUnitId).findFirst();
        return accommodationUnitOptional.orElse(null);
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
        List<AccommodationUnit> accommodationUnitsList = (List<AccommodationUnit>) servletContext.getAttribute(ACCOMMODATION_UNITS_LIST_KEY);
        accommodationUnitsList.remove(findAccommodationUnitById(accommodationUnitId));
    }

    @Override
    public int generateNextId() {
        List<AccommodationUnit> accommodationUnitsList = (List<AccommodationUnit>) servletContext.getAttribute(ACCOMMODATION_UNITS_LIST_KEY);
        return accommodationUnitsList.stream().mapToInt(AccommodationUnit::getId).max().orElse(0)+1;
    }
}
