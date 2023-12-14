package com.sr182022.travelagencystar.DAO.impl;

import com.sr182022.travelagencystar.DAO.IDestinationDAO;
import com.sr182022.travelagencystar.model.Destination;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DatabaseDestinationDAO implements IDestinationDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class DestinationRowCallBackHandler implements RowCallbackHandler {

        private Map<Integer, Destination> destinations = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;
            int id_destination = resultSet.getInt(index++);
            String destination_city = resultSet.getString(index++);
            String destination_country = resultSet.getString(index++);
            String destination_continent = resultSet.getString(index++);
            String destination_image = resultSet.getString("destination_image");

            Destination destination = destinations.get(id_destination);
            if (destination == null) {
                destination = new Destination(id_destination, destination_city, destination_country, destination_continent, destination_image);
                destinations.put(destination.getId(), destination);
            }
        }

        public List<Destination> getDestinations() {
            return new ArrayList<>(destinations.values());
        }
    }

    @Override
    public List<Destination> findAll() {
        String sql =
                "SELECT d.id_destination, d.destination_city, d.destination_country, d.destination_continent, d.destination_image " +
                        "FROM destination d ORDER BY d.id_destination";

        DestinationRowCallBackHandler rowCallBackHandler = new DestinationRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler);
        return rowCallBackHandler.getDestinations();
    }

    @Override
    public Destination findOne(int destinationId) {
        String sql =
                "SELECT d.id_destination, d.destination_city, d.destination_country, d.destination_continent, d.destination_image " +
                        "FROM destination d WHERE d.id_destination = ?";

        DestinationRowCallBackHandler rowCallBackHandler = new DestinationRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, destinationId);
        List<Destination> destinationsList = rowCallBackHandler.getDestinations();
        if (!destinationsList.isEmpty()) {
            return destinationsList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public void save(Destination newDestination) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "INSERT INTO destination (destination_city, destination_country, destination_continent, destination_image) " +
                        "VALUES (?, ?, ?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setString(index++, newDestination.getCity());
                preparedStatement.setString(index++, newDestination.getCountry());
                preparedStatement.setString(index++, newDestination.getContinent());
                preparedStatement.setString(index++, newDestination.getImage());

                return preparedStatement;
            }
        };

        jdbcTemplate.update(preparedStatementCreator);
    }

    @Transactional
    @Override
    public void update(Destination editDestination) {
        String sql =
                "UPDATE destination d " +
                        "SET d.destination_city = ?, d.destination_country = ?, d.destination_continent = ?, d.destination_image = ? " +
                        "WHERE d.id_destination = ?";

        jdbcTemplate.update(sql, editDestination.getCity(), editDestination.getCountry(), editDestination.getContinent(), editDestination.getImage(),
                editDestination.getId());
    }

    @Transactional
    @Override
    public void delete(int destinationId) {
        String sql = "DELETE FROM destination d WHERE d.id_destination = ?";
        jdbcTemplate.update(sql, destinationId);
    }
}
