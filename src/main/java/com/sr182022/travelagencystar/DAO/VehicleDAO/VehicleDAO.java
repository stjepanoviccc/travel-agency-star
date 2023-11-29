package com.sr182022.travelagencystar.DAO.VehicleDAO;

import com.sr182022.travelagencystar.model.Vehicle;
import com.sr182022.travelagencystar.model.VehicleType;
import com.sr182022.travelagencystar.service.DestinationService.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class VehicleDAO implements IVehicleDAO {
    @Value("${vehicles.pathToFile}")
    private String pathToFile;
    private final DestinationService destinationService;


    @Autowired
    public VehicleDAO(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    public Map<Integer, Vehicle> Load() {
        try {
            Map<Integer, Vehicle> vehicles = new HashMap<>();
            Path path = Paths.get(pathToFile);
            List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));

            for (String line : lines) {
                line = line.trim();
                if (line.equals("") || line.indexOf('#') == 0)
                    continue;

                String[] data = line.split("\\|");
                Vehicle vehicle = new Vehicle();

                vehicle.setId(Integer.parseInt(data[0]));
                vehicle.setNumberOfSeats(Integer.parseInt(data[1]));

                int finalDestinationId = Integer.parseInt(data[2]);
                vehicle.setFinalDestination(destinationService.findDestinationById(finalDestinationId));

                vehicle.setDescription(data[3]);
                vehicle.setVehicleType(VehicleType.valueOf(data[4]));

                vehicles.put(Integer.parseInt(data[0]), vehicle);
            }

            return vehicles;

        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Error processing file vehicles.txt", e);
        }
    }

    public void Save(Map<Integer, Vehicle> vehicles) {

        try {
            Path path = Paths.get(pathToFile);
            List<String> lines = new ArrayList<>();

            for (Vehicle v : vehicles.values()) {
                lines.add(v.toFileString());
            }

            Files.write(path, lines, Charset.forName("UTF-8"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Vehicle> findAllVehicles() {
        Map<Integer, Vehicle> vehicles = Load();
        return new ArrayList<>(vehicles.values());
    }

    @Override
    public Vehicle findVehicleById(int vehicleId) {
        Map<Integer, Vehicle> vehicles = Load();
        return vehicles.get(vehicleId);
    }

    @Override
    public List<String> findAllVehicleTypes() {
        return Arrays.stream(VehicleType.values()).map(Enum::name).collect(Collectors.toList());
    }

    @Override
    public void addNewVehicle(Vehicle newVehicle, int finalDestinationId) {
        Map<Integer, Vehicle> vehicles = Load();
        newVehicle.setId(generateNextId());
        newVehicle.setFinalDestination(destinationService.findDestinationById(finalDestinationId));
        vehicles.put(newVehicle.getId(), newVehicle);
        Save(vehicles);
    }

    @Override
    public void editVehicle(Vehicle editVehicle, int finalDestinationId) {
        Map<Integer, Vehicle> vehicles = Load();
        Vehicle existingVehicle = findVehicleById(editVehicle.getId());
        existingVehicle.setNumberOfSeats(editVehicle.getNumberOfSeats());
        existingVehicle.setFinalDestination(destinationService.findDestinationById(finalDestinationId));
        existingVehicle.setDescription(editVehicle.getDescription());
        existingVehicle.setVehicleType(editVehicle.getVehicleType());
        vehicles.put(editVehicle.getId(), existingVehicle);
        Save(vehicles);
    }

    @Override
    public void deleteVehicle(int vehicleId) {
        Map<Integer, Vehicle> vehicles = Load();
        vehicles.remove(vehicleId);
        Save(vehicles);
    }

    public int generateNextId() {
        Map<Integer, Vehicle> vehicles = Load();
        int nextId = 1;
        for (int id : vehicles.keySet()) {
            if(nextId<id)
                nextId=id;
        }
        return ++nextId;
    };
}
