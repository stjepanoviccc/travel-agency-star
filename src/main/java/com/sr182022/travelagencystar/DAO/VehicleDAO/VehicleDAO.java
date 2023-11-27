package com.sr182022.travelagencystar.DAO.VehicleDAO;

import com.sr182022.travelagencystar.model.Vehicle;
import com.sr182022.travelagencystar.model.VehicleType;
import com.sr182022.travelagencystar.service.DestinationService.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VehicleDAO implements IVehicleDAO {
    private final DestinationService destinationService;
    private final ResourceLoader resourceLoader;

    @Autowired
    public VehicleDAO(DestinationService destinationService, ResourceLoader resourceLoader) {
        this.destinationService = destinationService;
        this.resourceLoader = resourceLoader;
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

        } catch (IOException e) {
            throw new RuntimeException("Error reading file from vehicles.txt", e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Error parsing id from vehicles.txt", e);
        }
    };

    @Override
    public void Save(List<Vehicle> vehiclesList) {

    };
}
