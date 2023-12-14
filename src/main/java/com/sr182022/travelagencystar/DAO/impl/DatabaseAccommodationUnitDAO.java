package com.sr182022.travelagencystar.DAO.impl;

import com.sr182022.travelagencystar.DAO.IAccommodationUnitDAO;
import com.sr182022.travelagencystar.model.AccommodationType;
import com.sr182022.travelagencystar.model.AccommodationUnit;
import com.sr182022.travelagencystar.model.Destination;
import com.sr182022.travelagencystar.service.IDestinationService;
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
public class DatabaseAccommodationUnitDAO implements IAccommodationUnitDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final IDestinationService destinationService;

    public DatabaseAccommodationUnitDAO(IDestinationService destinationService) {
        this.destinationService = destinationService;
    }

    private class AccommodationUnitRowCallBackHandler implements RowCallbackHandler {

        private Map<Integer, AccommodationUnit> accommodationUnits = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;
            int id_accommodation_unit = resultSet.getInt(index++);
            String accommodation_unit_name = resultSet.getString(index++);
            int accommodation_unit_capacity = resultSet.getInt(index++);
            String accommodation_type = resultSet.getString(index++);
            AccommodationType accommodationType = AccommodationType.valueOf(accommodation_type);
            boolean wifi = resultSet.getBoolean(index++);
            boolean bathroom = resultSet.getBoolean(index++);
            boolean tv = resultSet.getBoolean(index++);
            boolean conditioner = resultSet.getBoolean(index++);
            int id_destination = resultSet.getInt(index++);
            Destination destination = destinationService.findOne(id_destination);
            String accommodation_unit_description = resultSet.getString(index++);

            AccommodationUnit accommodationUnit = accommodationUnits.get(id_accommodation_unit);
            if (accommodationUnit == null) {
                accommodationUnit = new AccommodationUnit(id_accommodation_unit, accommodation_unit_name, accommodation_unit_capacity,
                        accommodationType, wifi, bathroom, tv, conditioner, destination, accommodation_unit_description);
                accommodationUnits.put(accommodationUnit.getId(), accommodationUnit);
            }
        }

        public List<AccommodationUnit> getAccommodationUnits() {
            return new ArrayList<>(accommodationUnits.values());
        }
    }

    @Override
    public List<AccommodationUnit> findAll() {
        String sql =
                "SELECT au.id_accommodation_unit, au.accommodation_unit_name, au.accommodation_unit_capacity, au.accommodation_type, " +
                        "au.wifi, au.bathroom, au.tv, au.conditioner, au.id_destination, au.accommodation_unit_description " +
                        "FROM accommodation_unit au ORDER BY au.id_accommodation_unit";

        AccommodationUnitRowCallBackHandler rowCallBackHandler = new AccommodationUnitRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler);
        return rowCallBackHandler.getAccommodationUnits();
    }

    @Override
    public AccommodationUnit findOne(int accommodationUnitId) {
        String sql =
                "SELECT au.id_accommodation_unit, au.accommodation_unit_name, au.accommodation_unit_capacity, au.accommodation_type, " +
                        "au.wifi, au.bathroom, au.tv, au.conditioner, au.id_destination, au.accommodation_unit_description " +
                        "FROM accommodation_unit au WHERE au.id_accommodation_unit = ?";

        AccommodationUnitRowCallBackHandler rowCallBackHandler = new AccommodationUnitRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, accommodationUnitId);
        List<AccommodationUnit> accommodationUnitsList = rowCallBackHandler.getAccommodationUnits();
        if (!accommodationUnitsList.isEmpty()) {
            return accommodationUnitsList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public void save(AccommodationUnit newAccommodationUnit, int destinationId) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "INSERT INTO accommodation_unit (accommodation_unit_name, accommodation_unit_capacity, accommodation_type, " +
                        "wifi, bathroom, tv, conditioner, id_destination, accommodation_unit_description) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setString(index++, newAccommodationUnit.getName());
                preparedStatement.setInt(index++, newAccommodationUnit.getCapacity());
                preparedStatement.setString(index++, newAccommodationUnit.getAccommodationType().name());
                preparedStatement.setBoolean(index++, newAccommodationUnit.isWifi());
                preparedStatement.setBoolean(index++, newAccommodationUnit.isBathroom());
                preparedStatement.setBoolean(index++, newAccommodationUnit.isTv());
                preparedStatement.setBoolean(index++, newAccommodationUnit.isConditioner());
                preparedStatement.setInt(index++, destinationId);
                preparedStatement.setString(index++, newAccommodationUnit.getDescription());

                return preparedStatement;
            }
        };

        jdbcTemplate.update(preparedStatementCreator);
    }

    @Transactional
    @Override
    public void update(AccommodationUnit editAccommodationUnit, int destinationId) {
        String sql =
                "UPDATE accommodation_unit au " +
                        "SET au.accommodation_unit_name = ?, au.accommodation_unit_capacity = ?, au.accommodation_type = ?, wifi = ?, bathroom = ?, tv = ?" +
                        ", conditioner = ?, id_destination = ?, accommodation_unit_description = ? " +
                        "WHERE au.id_accommodation_unit = ?";

        jdbcTemplate.update(sql, editAccommodationUnit.getName(), editAccommodationUnit.getCapacity(), editAccommodationUnit.getAccommodationType().name(),
                editAccommodationUnit.isWifi(), editAccommodationUnit.isBathroom(), editAccommodationUnit.isTv(), editAccommodationUnit.isConditioner(),
                destinationId, editAccommodationUnit.getDescription(), editAccommodationUnit.getId());
    }

    @Transactional
    @Override
    public void delete(int accommodationUnitId) {
        String sql = "DELETE FROM accommodation_unit au WHERE au.id_accommodation_unit = ?";
        jdbcTemplate.update(sql, accommodationUnitId);
    }
}
