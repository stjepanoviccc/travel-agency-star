package com.sr182022.travelagencystar.service.VehicleService;

import com.sr182022.travelagencystar.DAO.VehicleDAO.VehicleDAO;
import com.sr182022.travelagencystar.model.Vehicle;
import com.sr182022.travelagencystar.model.VehicleType;
import com.sr182022.travelagencystar.service.DestinationService.DestinationService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleService implements IVehicleService {

    private final VehicleDAO vehicleDAO;
    private final DestinationService destinationService;
    ServletContext servletContext;
    public static final String VEHICLES_LIST_KEY = "vehiclesList";

    @Autowired
    public VehicleService(VehicleDAO vehicleDAO, DestinationService destinationService, ServletContext servletContext) {
        this.vehicleDAO = vehicleDAO;
        this.destinationService = destinationService;
        this.servletContext = servletContext;
    }

    @PostConstruct
    public void init() {
        List<Vehicle> vehiclesList = (List<Vehicle>) servletContext.getAttribute(VEHICLES_LIST_KEY);
        vehiclesList.addAll(vehicleDAO.Load());
    }

    @Override
    public List<Vehicle> findAllVehicles() {
        List<Vehicle> vehiclesList = (List<Vehicle>) servletContext.getAttribute(VEHICLES_LIST_KEY);
        return vehiclesList;
    }

    @Override
    public Vehicle findVehicleById(int vehicleId) {
        List<Vehicle> vehiclesList = (List<Vehicle>) servletContext.getAttribute(VEHICLES_LIST_KEY);
        Optional<Vehicle> vehicleOptional = vehiclesList.stream().filter(v -> v.getId() == vehicleId).findFirst();
        return vehicleOptional.orElse(null);
    }

    public List<String> findAllVehicleTypes() {
        return Arrays.stream(VehicleType.values()).map(Enum::name).collect(Collectors.toList());
    }

    @Override
    public void addNewVehicle(Vehicle newVehicle, int finalDestinationId) {
        List<Vehicle> vehiclesList = (List<Vehicle>) servletContext.getAttribute(VEHICLES_LIST_KEY);
        newVehicle.setId(generateNextId());
        newVehicle.setFinalDestination(destinationService.findDestinationById(finalDestinationId));
        vehiclesList.add(newVehicle);
    }

    @Override
    public void editVehicle(Vehicle editVehicle, int finalDestinationId) {
        Vehicle existingVehicle = findVehicleById(editVehicle.getId());
        existingVehicle.setNumberOfSeats(editVehicle.getNumberOfSeats());
        existingVehicle.setFinalDestination(destinationService.findDestinationById(finalDestinationId));
        existingVehicle.setDescription(editVehicle.getDescription());
        existingVehicle.setVehicleType(editVehicle.getVehicleType());
    }

    @Override
    public void deleteVehicle(int vehicleId) {
        List<Vehicle> vehiclesList = (List<Vehicle>) servletContext.getAttribute(VEHICLES_LIST_KEY);
        vehiclesList.remove(findVehicleById(vehicleId));
    }

    @Override
    public int generateNextId() {
        List<Vehicle> vehiclesList = (List<Vehicle>) servletContext.getAttribute(VEHICLES_LIST_KEY);
        return vehiclesList.stream().mapToInt(Vehicle::getId).max().orElse(0)+1;

    }
}
