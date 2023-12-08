package com.sr182022.travelagencystar.service.impl;

import com.sr182022.travelagencystar.DAO.impl.VehicleDAO;
import com.sr182022.travelagencystar.model.Vehicle;
import com.sr182022.travelagencystar.service.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService implements IVehicleService {

    private final VehicleDAO vehicleDAO;

    @Autowired
    public VehicleService(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    @Override
    public List<Vehicle> findAll() {
        return vehicleDAO.findAll();
    }

    @Override
    public Vehicle findOne(int vehicleId) {
        return vehicleDAO.findOne(vehicleId);
    }

    public List<String> findAllVehicleTypes() {
        return vehicleDAO.findAllVehicleTypes();
    }

    @Override
    public void save(Vehicle newVehicle, int finalDestinationId) {
        vehicleDAO.save(newVehicle, finalDestinationId);
    }

    @Override
    public void update(Vehicle editVehicle, int finalDestinationId) {
        vehicleDAO.update(editVehicle, finalDestinationId);
    }

    @Override
    public void delete(int vehicleId) {
        vehicleDAO.delete(vehicleId);
    }

    @Override
    public int generateNextId() {
        return vehicleDAO.generateNextId();
    }
}
