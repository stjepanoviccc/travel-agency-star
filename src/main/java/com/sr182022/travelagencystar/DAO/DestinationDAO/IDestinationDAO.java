package com.sr182022.travelagencystar.DAO.DestinationDAO;

import com.sr182022.travelagencystar.model.Destination;

import java.util.List;

public interface IDestinationDAO {
    List<Destination> findAllDestinations();
    Destination findDestinationById(int destinationId);
    void addNewDestination(Destination newDestination);
    void editDestination(Destination editDestination);
    void deleteDestination(int destinationId);
}
