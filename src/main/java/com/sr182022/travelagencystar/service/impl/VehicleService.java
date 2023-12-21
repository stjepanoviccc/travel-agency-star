package com.sr182022.travelagencystar.service.impl;

import com.sr182022.travelagencystar.DAO.IVehicleDAO;
import com.sr182022.travelagencystar.model.Vehicle;
import com.sr182022.travelagencystar.model.VehicleType;
import com.sr182022.travelagencystar.service.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService implements IVehicleService {

    private final IVehicleDAO databaseVehicleDAO;

    @Autowired
    public VehicleService(IVehicleDAO databaseVehicleDAO) {
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
        return Arrays.stream(VehicleType.values())
                .map(Enum::name)
                .collect(Collectors.toList());
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

    @Override
    public boolean tryValidate(Vehicle vehicle) {
        if(vehicle.getNumberOfSeats() < 0) {
            return false;
        }
        if(vehicle.getDescription().length() < 2 || vehicle.getDescription().length() > 100) {
            return false;
        }

        return true;
    }
}
