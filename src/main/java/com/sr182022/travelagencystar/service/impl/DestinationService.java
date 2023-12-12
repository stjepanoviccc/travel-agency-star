package com.sr182022.travelagencystar.service.impl;

import com.sr182022.travelagencystar.DAO.IDestinationDAO;
import com.sr182022.travelagencystar.model.Destination;
import com.sr182022.travelagencystar.service.IDestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationService implements IDestinationService {

    private final IDestinationDAO databaseDestinationDAO;

    @Autowired
    public DestinationService(IDestinationDAO databaseDestinationDAO) {
        this.databaseDestinationDAO = databaseDestinationDAO;
    }

    @Override
    public List<Destination> findAll() {
        return databaseDestinationDAO.findAll();
    }

    @Override
    public Destination findOne(int destinationId) {
        return databaseDestinationDAO.findOne(destinationId);
    }

    @Override
    public void save(Destination newDestination) {
        databaseDestinationDAO.save(newDestination);
    }

    @Override
    public void update(Destination editDestination) {
        databaseDestinationDAO.update(editDestination);
    }

    @Override
    public void delete(int destinationId) {
        databaseDestinationDAO.delete(destinationId);
    }
}
