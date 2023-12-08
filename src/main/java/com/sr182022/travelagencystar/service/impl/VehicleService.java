package com.sr182022.travelagencystar.service.impl;

import com.sr182022.travelagencystar.DAO.impl.DatabaseVehicleDAO;
import com.sr182022.travelagencystar.model.Vehicle;
import com.sr182022.travelagencystar.service.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService implements IVehicleService {

    private final DatabaseVehicleDAO databaseVehicleDAO;

    @Autowired
    public VehicleService(DatabaseVehicleDAO databaseVehicleDAO) {
        this.databaseVehicleDAO = databaseVehicleDAO;
    }

    @Override
    public List<Vehicle> findAll() {
        return databaseVehicleDAO.findAll();
    }

    @Override
    public Vehicle findOne(int vehicleId) {
        return databaseVehicleDAO.findOne(vehicleId);
    }

    public List<String> findAllVehicleTypes() {
        return databaseVehicleDAO.findAllVehicleTypes();
    }

    @Override
    public void save(Vehicle newVehicle, int finalDestinationId) {
        databaseVehicleDAO.save(newVehicle, finalDestinationId);
    }

    @Override
    public void update(Vehicle editVehicle, int finalDestinationId) {
        databaseVehicleDAO.update(editVehicle, finalDestinationId);
    }

    @Override
    public void delete(int vehicleId) {
        databaseVehicleDAO.delete(vehicleId);
    }
}
