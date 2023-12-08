package com.sr182022.travelagencystar.service.impl;

import com.sr182022.travelagencystar.DAO.impl.DestinationDAO;
import com.sr182022.travelagencystar.model.Destination;
import com.sr182022.travelagencystar.service.IDestinationService;
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
    public List<Destination> findAll() {
        return destinationDAO.findAll();
    }

    @Override
    public Destination findOne(int destinationId) {
        return destinationDAO.findOne(destinationId);
    }

    @Override
    public void save(Destination newDestination) {
        destinationDAO.save(newDestination);
    }

    @Override
    public void update(Destination editDestination) {
        destinationDAO.update(editDestination);
    }

    @Override
    public void delete(int destinationId) {
        destinationDAO.delete(destinationId);
    }

    @Override
    public int generateNextId() {
        return destinationDAO.generateNextId();
    }
}
