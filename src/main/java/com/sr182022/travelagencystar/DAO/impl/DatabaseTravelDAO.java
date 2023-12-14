package com.sr182022.travelagencystar.DAO.impl;

import com.sr182022.travelagencystar.DAO.ITravelDAO;
import com.sr182022.travelagencystar.model.*;
import com.sr182022.travelagencystar.service.IAccommodationUnitService;
import com.sr182022.travelagencystar.service.IDestinationService;
import com.sr182022.travelagencystar.service.IVehicleService;
import com.sr182022.travelagencystar.utils.DateTimeUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DatabaseTravelDAO implements ITravelDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final IDestinationService destinationService;
    private final IAccommodationUnitService accommodationUnitService;
    private final IVehicleService vehicleService;

    public DatabaseTravelDAO(IDestinationService destinationService, IAccommodationUnitService accommodationUnitService, IVehicleService vehicleService) {
        this.destinationService = destinationService;
        this.accommodationUnitService = accommodationUnitService;
        this.vehicleService = vehicleService;
    }

    private class TravelRowCallBackHandler implements RowCallbackHandler {

        private Map<Integer, Travel> travels = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;
            int id_travel = resultSet.getInt(index++);
            LocalDate travel_start_date = DateTimeUtil.convertTimestampToLocalDate(resultSet.getTimestamp(index++));
            LocalDate travel_end_date = DateTimeUtil.convertTimestampToLocalDate(resultSet.getTimestamp(index++));
            int number_of_nights = resultSet.getInt(index++);
            String travel_category = resultSet.getString(index++);
            TravelCategory travelCategory = TravelCategory.valueOf(travel_category);
            int id_destination = resultSet.getInt(index++);
            Destination destination = destinationService.findOne(id_destination);
            int id_accommodation_unit = resultSet.getInt(index++);
            AccommodationUnit accommodationUnit = accommodationUnitService.findOne(id_accommodation_unit);
            int id_vehicle = resultSet.getInt(index++);
            Vehicle vehicle = vehicleService.findOne(id_vehicle);
            float travel_price = resultSet.getFloat(index++);

            Travel travel = travels.get(id_travel);
            if (travel == null) {
                travel = new Travel(id_travel, destination, vehicle, accommodationUnit, travel_start_date, travel_end_date, number_of_nights, travelCategory,
                        travel_price);
                travels.put(travel.getId(), travel);
            }
        }

        public List<Travel> getTravels() {
            return new ArrayList<>(travels.values());
        }
    }

    @Override
    public List<Travel> findAll() {
        String sql =
                "SELECT t.id_travel, t.travel_start_date, t.travel_end_date, t.number_of_nights, t.travel_category, t.id_destination, t.id_accommodation_unit," +
                        "t.id_vehicle, t.travel_price " +
                        "FROM travel t ORDER BY t.id_travel";

        TravelRowCallBackHandler rowCallBackHandler = new TravelRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler);
        return rowCallBackHandler.getTravels();
    }

    @Override
    public Travel findOne(int travelId) {
        String sql =
                "SELECT t.id_travel, t.travel_start_date, t.travel_end_date, t.number_of_nights, t.travel_category, t.id_destination, t.id_accommodation_unit," +
                        "t.id_vehicle, t.travel_price " +
                        "FROM travel t WHERE t.id_travel = ?";

        TravelRowCallBackHandler rowCallBackHandler = new TravelRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, travelId);
        List<Travel> travelsList = rowCallBackHandler.getTravels();
        if (!travelsList.isEmpty()) {
            return travelsList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public void save(Travel newTravel, int destinationId, int accommodationUnitId, int vehicleId) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "INSERT INTO travel (travel_start_date, travel_end_date, number_of_nights, travel_category, id_destination, id_accommodation_unit," +
                        "id_vehicle, travel_price) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setTimestamp(index++, DateTimeUtil.convertLocalDateToTimestamp(newTravel.getStartDate()));
                preparedStatement.setTimestamp(index++, DateTimeUtil.convertLocalDateToTimestamp(newTravel.getEndDate()));
                preparedStatement.setInt(index++, newTravel.getNumberOfNights());
                preparedStatement.setString(index++, newTravel.getTravelCategory().name());
                preparedStatement.setInt(index++, destinationId);
                preparedStatement.setInt(index++, accommodationUnitId);
                preparedStatement.setInt(index++, vehicleId);
                preparedStatement.setFloat(index++, newTravel.getPrice());

                return preparedStatement;
            }
        };

        jdbcTemplate.update(preparedStatementCreator);
    }

    @Transactional
    @Override
    public void update(Travel editTravel, int destinationId, int accommodationUnitId, int vehicleId) {
        String sql =
                "UPDATE travel t " +
                        "SET t.travel_start_date = ?, t.travel_end_date = ?, t.number_of_nights = ?, t.travel_category = ?, t.id_destination = ?, t.id_vehicle = ?, " +
                        "t.id_accommodation_unit = ?, t.travel_price = ? " +
                        "WHERE t.id_travel = ?";

        jdbcTemplate.update(sql, editTravel.getStartDate(), editTravel.getEndDate(), editTravel.getNumberOfNights(), editTravel.getTravelCategory().name(),
                destinationId, vehicleId, accommodationUnitId, editTravel.getPrice(), editTravel.getId());
    }

    @Transactional
    @Override
    public void delete(int travelId) {
        String sql = "DELETE FROM travel t WHERE t.id_travel = ?";
        jdbcTemplate.update(sql, travelId);
    }
}
