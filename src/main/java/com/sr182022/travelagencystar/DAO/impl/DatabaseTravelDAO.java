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
import org.springframework.jdbc.core.RowMapper;
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

    private class TravelRowMapper implements RowMapper<Travel> {
        @Override
        public Travel mapRow(ResultSet resultSet, int rowNum) throws SQLException {
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

            Travel travel = new Travel(id_travel, destination, vehicle, accommodationUnit, travel_start_date, travel_end_date, number_of_nights, travelCategory, travel_price);
            return travel;
        }
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
    public List<Travel> findAll(int destinationId) {
        String sql =
                "SELECT t.id_travel, t.travel_start_date, t.travel_end_date, t.number_of_nights, t.travel_category, t.id_destination, t.id_accommodation_unit," +
                        "t.id_vehicle, t.travel_price " +
                        "FROM travel t WHERE t.id_destination = ? ORDER BY t.id_travel";

        TravelRowCallBackHandler rowCallBackHandler = new TravelRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, destinationId);
        return rowCallBackHandler.getTravels();
    }


    @Override
    public List<Travel> findAll(String destination, String destinationSort, String travelCategory, String travelCategorySort, String travelVehicleType,
                                String travelVehicleTypeSort, String travelAccUnitType, String travelAccUnitTypeSort,
                                Float minPrice, Float maxPrice, String priceSort,
                                LocalDate startDate, LocalDate endDate, String dateSort, Integer nightsFrom, Integer nightsTo, String sortNights,
                                Integer passengerAvailability, String sortTravelPassengerAvailability, String inputID, String sortTravelByID) {
        ArrayList<Object> argList = new ArrayList<Object>();
        String sql = "SELECT t.id_travel, t.travel_start_date, t.travel_end_date, t.number_of_nights, t.travel_category, " +
                "t.id_destination, t.id_accommodation_unit, t.id_vehicle, t.travel_price FROM travel t " +
                "LEFT JOIN destination d on d.id_destination = t.id_destination " +
                "LEFT JOIN vehicle v on v.id_vehicle = t.id_vehicle " +
                "LEFT JOIN accommodation_unit a on a.id_accommodation_unit = t.id_accommodation_unit " +
                "LEFT JOIN travel_reservation tr on t.id_travel = tr.id_travel ";

        StringBuffer whereSql = new StringBuffer(" WHERE ");
        boolean hasArgs = false;

        if (!destination.isEmpty()) {
            if(hasArgs) {
                whereSql.append(" AND ");
            }
            whereSql.append("d.destination_city like ? ");
            hasArgs = true;
            argList.add("%" + destination + "%");
        }
        if (!travelVehicleType.isEmpty()) {
            if(hasArgs) {
                whereSql.append(" AND ");
            }
            whereSql.append("v.vehicle_type like ? ");
            hasArgs = true;
            argList.add(travelVehicleType);
        }
        if (!travelAccUnitType.isEmpty()) {
            if(hasArgs) {
                whereSql.append(" AND ");
            }
            whereSql.append("a.accommodation_type like ? ");
            hasArgs = true;
            argList.add(travelAccUnitType);
        }

        if(!travelCategory.isEmpty()) {
            if(hasArgs) {
                whereSql.append(" AND ");
            }
            whereSql.append("t.travel_category like ?");
            hasArgs = true;
            argList.add(travelCategory);
        }

        if(minPrice > 0 || (maxPrice > 0 && maxPrice != 999999F)) {
            if(hasArgs) {
                whereSql.append(" AND ");
            }
            whereSql.append("t.travel_price >= ? AND t.travel_price <= ? ");
            hasArgs = true;
            argList.add(minPrice);
            argList.add(maxPrice);
        }

        if (startDate.isAfter(LocalDate.of(1999, 1, 1)) || endDate.isBefore(LocalDate.of(2099, 1, 1))) {
            if (hasArgs) {
                whereSql.append(" AND ");
            }
            whereSql.append("t.travel_start_date >= ? AND t.travel_end_date <= ? ");
            hasArgs = true;
            argList.add(startDate);
            argList.add(endDate);
        }

        if(nightsFrom > 0 || (nightsTo > 0 && nightsTo != 999999)) {
            if(hasArgs) {
                whereSql.append(" AND ");
            }
            whereSql.append("t.number_of_nights >= ? AND t.number_of_nights <= ? ");
            hasArgs = true;
            argList.add(nightsFrom);
            argList.add(nightsTo);
        }

        if(passengerAvailability > 0) {
            if(hasArgs) {
                whereSql.append(" AND ");
            }
            whereSql.append("a.accommodation_unit_capacity - COALESCE(tr.available_space, 0) >= ? ");
            hasArgs = true;
            argList.add(passengerAvailability);
        }

        if(!inputID.isEmpty()) {
            if(hasArgs) {
                whereSql.append(" AND ");
            }
            whereSql.append("t.id_travel like ? ");
            hasArgs = true;
            argList.add(inputID);
            // argList.add('%' + inputID + '%');
        }

        if(hasArgs) {
            sql += whereSql;
        }

        boolean hasSort = false;

        if (!destinationSort.equals("Default")) {
            sql += " ORDER BY d.destination_city " + destinationSort;
            hasSort = true;
        }

        if (!travelCategorySort.equals("Default")) {
            if (hasSort) {
                sql += ", t.travel_category " + travelCategorySort;
            } else {
                sql += " ORDER BY t.travel_category " + travelCategorySort;
                hasSort = true;
            }
        }

        if (!travelVehicleTypeSort.equals("Default")) {
            if (hasSort) {
                sql += ", v.vehicle_type " + travelVehicleTypeSort;
            } else {
                sql += " ORDER BY v.vehicle_type " + travelVehicleTypeSort;
                hasSort = true;
            }
        }

        if (!travelAccUnitTypeSort.equals("Default")) {
            if (hasSort) {
                sql += ", a.accommodation_type " + travelAccUnitTypeSort;
            } else {
                sql += " ORDER BY a.accommodation_type " + travelAccUnitTypeSort;
                hasSort = true;
            }
        }

        if (!priceSort.equals("Default")) {
            if (hasSort) {
                sql += ", t.travel_price " + priceSort;
            } else {
                sql += " ORDER BY t.travel_price " + priceSort;
                hasSort = true;
            }
        }

        if (!dateSort.equals("Default")) {
            if (hasSort) {
                sql += ", t.travel_start_date " + dateSort;
            } else {
                sql += " ORDER BY t.travel_start_date " + dateSort;
                hasSort = true;
            }
        }

        if (!sortNights.equals("Default")) {
            if (hasSort) {
                sql += ", t.number_of_nights " + sortNights;
            } else {
                sql += " ORDER BY t.number_of_nights " + sortNights;
                hasSort = true;
            }
        }

        if (!sortTravelPassengerAvailability.equals("Default")) {
            if (hasSort) {
                sql += ", a.accommodation_unit_capacity" + sortTravelPassengerAvailability;
            } else {
                sql += " ORDER BY a.accommodation_unit_capacity " + sortTravelPassengerAvailability;
                hasSort = true;
            }
        }

        if (!sortTravelByID.equals("Default")) {
            sql += " ORDER BY t.id_travel " + sortTravelByID;
            hasSort = true;
        }

        if (!hasSort) {
            sql += " ORDER BY t.id_travel";
        }

        return jdbcTemplate.query(sql, argList.toArray(), new TravelRowMapper());
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
