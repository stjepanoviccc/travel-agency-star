package com.sr182022.travelagencystar.service;

import com.sr182022.travelagencystar.model.Destination;

import java.util.List;

public interface IDestinationService {
    List<Destination> findAll();
    Destination findOne(int destinationId);
    void save(Destination newDestination);
    void update(Destination editDestination);
    void delete(int destinationId);
    String checkImageValueOnChange(Destination editDestination, String imageValue);
    boolean tryValidate(Destination destination);
}
