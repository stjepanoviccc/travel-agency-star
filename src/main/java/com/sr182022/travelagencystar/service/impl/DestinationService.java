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

    @Override
    public String checkImageValueOnChange(Destination destination, String imageValue) {
        // this will check is image value null or empty(not selected) or image value equal to existing image, if not then it will apply new one, otherwise old one.
        if(imageValue.isEmpty()) {
            imageValue = destination.getImage();
        }
        return imageValue;
    }

    @Override
    public boolean tryValidate(Destination destination) {
        if(destination.getCity().length() <= 2 || destination.getCity().length() >= 20) {
            return false;
        }
        if(destination.getCountry().length() <= 2 || destination.getCountry().length() >= 20) {
            return false;
        }
        if(destination.getContinent().length() <= 2 || destination.getContinent().length() >= 20) {
            return false;
        }

        return true;
    }
}
