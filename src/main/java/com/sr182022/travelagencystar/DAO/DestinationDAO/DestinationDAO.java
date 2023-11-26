package com.sr182022.travelagencystar.DAO.DestinationDAO;

import com.sr182022.travelagencystar.model.Destination;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DestinationDAO implements IDestinationDAO {
    private final ResourceLoader resourceLoader;

    public DestinationDAO(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public List<Destination> Load() {

        try {
            List<Destination> destinationsList = new ArrayList<>();
            Resource resource = resourceLoader.getResource("classpath:static/testingTextFiles/destinations.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                Destination destination = new Destination();
                destination.setId(Integer.parseInt(data[0]));
                destination.setCity(data[1]);
                destination.setCountry(data[2]);
                destination.setContinent(data[3]);
                destination.setImage(data[4]);
                destinationsList.add(destination);
            }

            return destinationsList;

        } catch (IOException e) {
            throw new RuntimeException("Error reading file from destinations.txt", e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Error parsing id from users.txt", e);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Error parsing registered date from users.txt", e);
        } catch (EnumConstantNotPresentException e) {
            throw new RuntimeException("Enum Role didn't parse well", e);
        }
    };

    @Override
    public void Save(List<Destination> destinationsList) {

    };
};