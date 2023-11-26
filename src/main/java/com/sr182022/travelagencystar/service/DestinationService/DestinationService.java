package com.sr182022.travelagencystar.service.DestinationService;

import com.sr182022.travelagencystar.DAO.DestinationDAO.DestinationDAO;
import com.sr182022.travelagencystar.model.Destination;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinationService implements IDestinationService {

    @Autowired
    private DestinationDAO destinationDAO;

    @Autowired
    ServletContext servletContext;

    public static final String DESTINATIONS_LIST_KEY = "destinationsList";

    @PostConstruct
    public void init() {
        List<Destination> destinationsList = (List<Destination>) servletContext.getAttribute(DESTINATIONS_LIST_KEY);
        destinationsList.addAll(destinationDAO.Load());
    }

    public List<Destination> findAllDestinations() {
        List<Destination> destinationsList = (List<Destination>) servletContext.getAttribute(DESTINATIONS_LIST_KEY);
        return destinationsList;
    }

    public Destination findDestinationById(int destinationId) {
        List<Destination> destinationsList = (List<Destination>) servletContext.getAttribute(DESTINATIONS_LIST_KEY);
        Optional<Destination> destinationOptional = destinationsList.stream().filter(d -> d.getId() == destinationId).findFirst();
        return destinationOptional.orElse(null);
    }

    public void addNewDestination(Destination newDestination) {
        List<Destination> destinationsList = (List<Destination>) servletContext.getAttribute(DESTINATIONS_LIST_KEY);
        newDestination.setId(generateNextId());
        destinationsList.add(newDestination);
    }

    public void editDestination(Destination editDestination) {
        Destination existingDestination = findDestinationById(editDestination.getId());
        existingDestination.setCity(editDestination.getCity());
        existingDestination.setCountry(editDestination.getCountry());
        existingDestination.setContinent(editDestination.getContinent());
        existingDestination.setImage(editDestination.getImage());
    }

    public void deleteDestination(int destinationId) {
        List<Destination> destinationsList = (List<Destination>) servletContext.getAttribute(DESTINATIONS_LIST_KEY);
        destinationsList.remove(findDestinationById(destinationId));
    }

    public int generateNextId() {
        List<Destination> destinationsList = (List<Destination>) servletContext.getAttribute(DESTINATIONS_LIST_KEY);
        return destinationsList.stream().mapToInt(Destination::getId).max().orElse(0)+1;
    }
}
