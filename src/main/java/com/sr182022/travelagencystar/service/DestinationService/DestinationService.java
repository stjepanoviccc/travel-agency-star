package com.sr182022.travelagencystar.service.DestinationService;

import com.sr182022.travelagencystar.DAO.DestinationDAO.DestinationDAO;
import com.sr182022.travelagencystar.model.Destination;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationService implements IDestinationService {

    private final DestinationDAO destinationDAO;

    @Autowired
    public DestinationService(DestinationDAO destinationDAO) {
        this.destinationDAO = destinationDAO;
    }

    @Override
    public List<Destination> findAllDestinations() {
        return destinationDAO.findAllDestinations();
    }

    @Override
    public Destination findDestinationById(int destinationId) {
        return destinationDAO.findDestinationById(destinationId);
    }

    @Override
    public void addNewDestination(Destination newDestination) {
        destinationDAO.addNewDestination(newDestination);
    }

    @Override
    public void editDestination(Destination editDestination) {
        destinationDAO.editDestination(editDestination);
    }

    @Override
    public void deleteDestination(int destinationId) {
        destinationDAO.deleteDestination(destinationId);
    }

    @Override
    public int generateNextId() {
        return destinationDAO.generateNextId();
    }
}
