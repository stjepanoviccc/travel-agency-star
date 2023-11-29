package com.sr182022.travelagencystar.DAO.VehicleDAO;

import com.sr182022.travelagencystar.model.Vehicle;

import java.util.List;

public interface IVehicleDAO {
    List<Vehicle> findAllVehicles();
    List<String> findAllVehicleTypes();
    Vehicle findVehicleById(int vehicleId);
    void addNewVehicle(Vehicle newVehicle, int destinationId);
    void editVehicle(Vehicle editVehicle, int finalDestinationId);
    void deleteVehicle(int vehicleId);
}
