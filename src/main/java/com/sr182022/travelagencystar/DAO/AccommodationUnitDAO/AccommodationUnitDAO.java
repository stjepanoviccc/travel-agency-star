package com.sr182022.travelagencystar.DAO.AccommodationUnitDAO;

import com.sr182022.travelagencystar.model.AccommodationUnit;
import com.sr182022.travelagencystar.model.Destination;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AccommodationUnitDAO implements IAccommodationUnitDAO {
    private final ResourceLoader resourceLoader;

    public AccommodationUnitDAO(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
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
                accommodationUnit.setName(data[1]);
                accommodationUnit.setCapacity(Integer.parseInt(data[2]));
                accommodationUnit.setDescription(data[3]);
               // accommodationUnit.setReviews();
               // accommodationUnit.setDestination();
                // accommodationUnit.setServices(); -> vjv napraviti enumeraciju za servise.
                // accommodationUnit.setAccommodationType();
                accommodationUnitsList.add(accommodationUnit);
            }

            return accommodationUnitsList;

        } catch (IOException e) {
            throw new RuntimeException("Error reading file from destinations.txt", e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Error parsing id from destinations.txt", e);
        }
    }

    @Override
    public void Save(List<AccommodationUnit> accommodationUnitsList) {

    }
}
