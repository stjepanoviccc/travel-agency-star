package com.sr182022.travelagencystar.DAO.VehicleDAO;

import com.sr182022.travelagencystar.model.Vehicle;
import com.sr182022.travelagencystar.model.VehicleType;
import com.sr182022.travelagencystar.service.DestinationService.DestinationService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class VehicleDAO implements IVehicleDAO {
    private final DestinationService destinationService;
    private final ResourceLoader resourceLoader;
    ServletContext servletContext;
    public static final String VEHICLES_LIST_KEY = "vehiclesList";

    @Autowired
    public VehicleDAO(DestinationService destinationService, ResourceLoader resourceLoader, ServletContext servletContext) {
        this.destinationService = destinationService;
        this.resourceLoader = resourceLoader;
        this.servletContext = servletContext;
    }

    @PostConstruct
    public void init() {
        List<Vehicle> vehiclesList = (List<Vehicle>) servletContext.getAttribute(VEHICLES_LIST_KEY);
        vehiclesList.addAll(Load());
    }

    @Override
    public List<Vehicle> Load() {
        try {
            List<Vehicle> vehiclesList = new ArrayList<>();

            Resource resource = resourceLoader.getResource("classpath:static/testingTextFiles/vehicles.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                Vehicle vehicle = new Vehicle();
                vehicle.setId(Integer.parseInt(data[0]));
                vehicle.setNumberOfSeats(Integer.parseInt(data[1]));

                int finalDestinationId = Integer.parseInt(data[2]);
                vehicle.setFinalDestination(destinationService.findDestinationById(finalDestinationId));

                vehicle.setDescription(data[3]);
                vehicle.setVehicleType(VehicleType.valueOf(data[4]));

                vehiclesList.add(vehicle);
            }

            return vehiclesList;

        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Error processing file vehicles.txt", e);
        }
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

    @Override
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
    };
}
