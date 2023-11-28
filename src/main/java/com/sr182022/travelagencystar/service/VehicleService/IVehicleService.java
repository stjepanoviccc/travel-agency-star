package com.sr182022.travelagencystar.service.VehicleService;


import com.sr182022.travelagencystar.model.Vehicle;

import java.util.List;

public interface IVehicleService {
    List<Vehicle> findAllVehicles();
    Vehicle findVehicleById(int vehicleId);
    List<String> findAllVehicleTypes();
    void addNewVehicle(Vehicle newVehicle, int destinationId);
    void editVehicle(Vehicle editVehicle, int finalDestinationId);
    void deleteVehicle(int vehicleId);
    int generateNextId();
}
