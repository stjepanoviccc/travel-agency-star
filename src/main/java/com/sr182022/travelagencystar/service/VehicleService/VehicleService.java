package com.sr182022.travelagencystar.service.VehicleService;

import com.sr182022.travelagencystar.DAO.VehicleDAO.VehicleDAO;
import com.sr182022.travelagencystar.model.Vehicle;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService implements IVehicleService {

    private final VehicleDAO vehicleDAO;
    ServletContext servletContext;

    @Autowired
    public VehicleService(VehicleDAO vehicleDAO, ServletContext servletContext) {
        this.vehicleDAO = vehicleDAO;
        this.servletContext = servletContext;
    }

    @Override
    public List<Vehicle> findAllVehicles() {
        return vehicleDAO.findAllVehicles();
    }

    @Override
    public Vehicle findVehicleById(int vehicleId) {
        return vehicleDAO.findVehicleById(vehicleId);
    }

    public List<String> findAllVehicleTypes() {
        return vehicleDAO.findAllVehicleTypes();
    }

    @Override
    public void addNewVehicle(Vehicle newVehicle, int finalDestinationId) {
        vehicleDAO.addNewVehicle(newVehicle, finalDestinationId);
    }

    @Override
    public void editVehicle(Vehicle editVehicle, int finalDestinationId) {
        vehicleDAO.editVehicle(editVehicle, finalDestinationId);
    }

    @Override
    public void deleteVehicle(int vehicleId) {
        vehicleDAO.deleteVehicle(vehicleId);
    }

    @Override
    public int generateNextId() {
        return vehicleDAO.generateNextId();
    }
}
