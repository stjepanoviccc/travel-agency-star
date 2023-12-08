package com.sr182022.travelagencystar.DAO;

import com.sr182022.travelagencystar.model.Destination;

import java.util.List;

public interface IDestinationDAO {
    List<Destination> findAll();
    Destination findOne(int destinationId);
    void save(Destination newDestination);
    void update(Destination editDestination);
    void delete(int destinationId);
}
