package com.sr182022.travelagencystar.service;


import com.sr182022.travelagencystar.model.Vehicle;

import java.util.List;

public interface IVehicleService {
    List<Vehicle> findAll();
    Vehicle findOne(int vehicleId);
    List<String> findAllVehicleTypes();
    void save(Vehicle newVehicle, int destinationId);
    void update(Vehicle editVehicle, int finalDestinationId);
    void delete(int vehicleId);
}
