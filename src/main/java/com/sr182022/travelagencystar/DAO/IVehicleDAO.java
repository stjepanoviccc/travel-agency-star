package com.sr182022.travelagencystar.DAO;

import com.sr182022.travelagencystar.model.Vehicle;

import java.util.List;

public interface IVehicleDAO {
    List<Vehicle> findAll();
    Vehicle findOne(int vehicleId);
    void save(Vehicle newVehicle, int finalDestinationId);
    void update(Vehicle editVehicle, int finalDestinationId);
    void delete(int vehicleId);
}
