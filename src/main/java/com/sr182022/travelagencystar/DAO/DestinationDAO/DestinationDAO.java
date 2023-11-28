package com.sr182022.travelagencystar.DAO.DestinationDAO;

import com.sr182022.travelagencystar.model.Destination;
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

@Repository
public class DestinationDAO implements IDestinationDAO {
    private final ResourceLoader resourceLoader;
    ServletContext servletContext;
    public static final String DESTINATIONS_LIST_KEY = "destinationsList";

    @Autowired
    public DestinationDAO(ResourceLoader resourceLoader, ServletContext servletContext) {
        this.resourceLoader = resourceLoader;
        this.servletContext = servletContext;
    }

    @PostConstruct
    public void init() {
        List<Destination> destinationsList = (List<Destination>) servletContext.getAttribute(DESTINATIONS_LIST_KEY);
        destinationsList.addAll(Load());
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

        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Error processing file destinations.txt", e);
        }
    }

    @Override
    public List<Destination> findAllDestinations() {
        List<Destination> destinationsList = (List<Destination>) servletContext.getAttribute(DESTINATIONS_LIST_KEY);
        return destinationsList;
    }

    @Override
    public Destination findDestinationById(int destinationId) {
        List<Destination> destinationsList = (List<Destination>) servletContext.getAttribute(DESTINATIONS_LIST_KEY);
        Optional<Destination> destinationOptional = destinationsList.stream().filter(d -> d.getId() == destinationId).findFirst();
        return destinationOptional.orElse(null);
    }

    @Override
    public void addNewDestination(Destination newDestination) {
        List<Destination> destinationsList = (List<Destination>) servletContext.getAttribute(DESTINATIONS_LIST_KEY);
        newDestination.setId(generateNextId());
        destinationsList.add(newDestination);
    }

    @Override
    public void editDestination(Destination editDestination) {
        Destination existingDestination = findDestinationById(editDestination.getId());
        existingDestination.setCity(editDestination.getCity());
        existingDestination.setCountry(editDestination.getCountry());
        existingDestination.setContinent(editDestination.getContinent());
        existingDestination.setImage(editDestination.getImage());
    }

    @Override
    public void deleteDestination(int destinationId) {
        List<Destination> destinationsList = (List<Destination>) servletContext.getAttribute(DESTINATIONS_LIST_KEY);
        destinationsList.remove(findDestinationById(destinationId));
    }

    @Override
    public int generateNextId() {
        List<Destination> destinationsList = (List<Destination>) servletContext.getAttribute(DESTINATIONS_LIST_KEY);
        return destinationsList.stream().mapToInt(Destination::getId).max().orElse(0)+1;
    };
};