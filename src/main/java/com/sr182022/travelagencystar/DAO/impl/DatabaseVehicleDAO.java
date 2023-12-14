package com.sr182022.travelagencystar.DAO.impl;

import com.sr182022.travelagencystar.DAO.IVehicleDAO;
import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.model.Vehicle;
import com.sr182022.travelagencystar.model.VehicleType;
import com.sr182022.travelagencystar.service.IDestinationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class DatabaseVehicleDAO implements IVehicleDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private IDestinationService destinationService;

    @Autowired
    public DatabaseVehicleDAO(IDestinationService destinationService) {
        this.destinationService = destinationService;
    }

    private class VehicleRowCallBackHandler implements RowCallbackHandler {

        private Map<Integer, Vehicle> vehicles = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;
            int id_vehicle = resultSet.getInt(index++);
            int number_of_seats = resultSet.getInt(index++);
            int id_destination = resultSet.getInt(index++);
            String vehicle_description = resultSet.getString(index++);
            String vehicle_type = resultSet.getString(index++);
            VehicleType vehicleType = VehicleType.valueOf(vehicle_type);

            Vehicle vehicle = vehicles.get(id_vehicle);
            if (vehicle == null) {
                vehicle = new Vehicle(id_vehicle, number_of_seats, destinationService.findOne(id_destination), vehicle_description, vehicleType);
                vehicles.put(vehicle.getId(), vehicle);
            }
        }

        public List<Vehicle> getVehicles() {
            return new ArrayList<>(vehicles.values());
        }
    }

    @Override
    public List<Vehicle> findAll() {
        String sql =
                "SELECT v.id_vehicle, v.number_of_seats, v.id_destination, v.vehicle_description, v.vehicle_type " +
                        "FROM vehicle v ORDER BY v.id_vehicle";

        VehicleRowCallBackHandler rowCallBackHandler = new VehicleRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler);
        return rowCallBackHandler.getVehicles();
    }

    @Override
    public Vehicle findOne(int vehicleId) {
        String sql =
                "SELECT v.id_vehicle, v.number_of_seats, v.id_destination, v.vehicle_description, v.vehicle_type " +
                        "FROM vehicle v WHERE v.id_vehicle = ?";

        VehicleRowCallBackHandler rowCallBackHandler = new VehicleRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, vehicleId);
        List<Vehicle> vehiclesList = rowCallBackHandler.getVehicles();
        if (!vehiclesList.isEmpty()) {
            return vehiclesList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public void save(Vehicle newVehicle, int finalDestinationId) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "INSERT INTO vehicle (number_of_seats, id_destination, vehicle_description, vehicle_type) " +
                        "VALUES (?, ?, ?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setInt(index++, newVehicle.getNumberOfSeats());
                preparedStatement.setInt(index++, finalDestinationId);
                preparedStatement.setString(index++, newVehicle.getDescription());
                preparedStatement.setString(index++, newVehicle.getVehicleType().name());

                return preparedStatement;
            }
        };

        jdbcTemplate.update(preparedStatementCreator);
    }

    @Transactional
    @Override
    public void update(Vehicle editVehicle, int finalDestinationId) {

        String sql =
                "UPDATE vehicle v " +
                        "SET v.number_of_seats = ?, v.id_destination = ?, v.vehicle_description = ?, v.vehicle_type = ? " +
                        "WHERE v.id_vehicle = ?";

        jdbcTemplate.update(sql, editVehicle.getNumberOfSeats(), finalDestinationId, editVehicle.getDescription(), editVehicle.getVehicleType().name(),
                editVehicle.getId());
    }

    @Transactional
    @Override
    public void delete(int vehicleId) {
        String sql = "DELETE FROM vehicle v WHERE v.id_vehicle = ?";
        jdbcTemplate.update(sql, vehicleId);
    }
}
