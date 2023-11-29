package com.sr182022.travelagencystar.DAO.DestinationDAO;

import com.sr182022.travelagencystar.model.Destination;
import com.sr182022.travelagencystar.model.User;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Repository
public class DestinationDAO implements IDestinationDAO {
    @Value("${destinations.pathToFile}")
    private String pathToFile;

    public Map<Integer, Destination> Load() {

        try {
            Map<Integer, Destination> destinations = new HashMap<>();
            Path path = Paths.get(pathToFile);
            List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));

            for (String line : lines) {
                line = line.trim();
                if (line.equals("") || line.indexOf('#') == 0)
                    continue;

                String[] data = line.split("\\|");
                Destination destination = new Destination();

                destination.setId(Integer.parseInt(data[0]));
                destination.setCity(data[1]);
                destination.setCountry(data[2]);
                destination.setContinent(data[3]);
                destination.setImage(data[4]);

                destinations.put(Integer.parseInt(data[0]),destination);
            }

            return destinations;

        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Error processing file destinations.txt", e);
        }
    }

    public void Save(Map<Integer, Destination> destinations) {

        try {
            Path path = Paths.get(pathToFile);
            List<String> lines = new ArrayList<>();

            for (Destination d : destinations.values()) {
                lines.add(d.toFileString());
            }

            Files.write(path, lines, Charset.forName("UTF-8"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Destination> findAllDestinations() {
        Map<Integer, Destination> destinations = Load();
        return new ArrayList<>(destinations.values());
    }

    @Override
    public Destination findDestinationById(int destinationId) {
        Map<Integer, Destination> destinations = Load();
        return destinations.get(destinationId);
    }

    @Override
    public void addNewDestination(Destination newDestination) {
        Map<Integer, Destination> destinations = Load();
        newDestination.setId(generateNextId());
        destinations.put(newDestination.getId(), newDestination);
        Save(destinations);
    }

    @Override
    public void editDestination(Destination editDestination) {
        Map<Integer, Destination> destinations = Load();
        Destination existingDestination = findDestinationById(editDestination.getId());
        existingDestination.setCity(editDestination.getCity());
        existingDestination.setCountry(editDestination.getCountry());
        existingDestination.setContinent(editDestination.getContinent());
        existingDestination.setImage(editDestination.getImage());
        destinations.put(editDestination.getId(), existingDestination);
        Save(destinations);
    }

    @Override
    public void deleteDestination(int destinationId) {
        Map<Integer, Destination> destinations = Load();
        destinations.remove(destinationId);
        Save(destinations);
    }

    public int generateNextId() {
        Map<Integer, Destination> destinations = Load();
        int nextId = 1;
        for (int id : destinations.keySet()) {
            if(nextId<id)
                nextId=id;
        }
        return ++nextId;
    };
};