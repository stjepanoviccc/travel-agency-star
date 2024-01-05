package com.sr182022.travelagencystar.DAO.impl;

import com.sr182022.travelagencystar.DAO.ITravelReservationDAO;
import com.sr182022.travelagencystar.model.*;
import com.sr182022.travelagencystar.service.ITravelService;
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
public class DatabaseTravelReservationDAO implements ITravelReservationDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ITravelService travelService;

    public DatabaseTravelReservationDAO(ITravelService travelService) {
        this.travelService = travelService;
    }

    private class TravelReservationRowMapper implements RowMapper<TravelReservation> {
        @Override
        public TravelReservation mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            int index = 1;
            int tr_id = resultSet.getInt(index++);
            int id_travel = resultSet.getInt(index++);
            Travel t = travelService.findOne(id_travel);
            int total_space = resultSet.getInt(index++);
            int sold_space = resultSet.getInt(index++);
            int available_space = resultSet.getInt(index++);

            TravelReservation tr = new TravelReservation(tr_id, t, total_space, sold_space, available_space);
            return tr;
        }
    }

    private class TravelReservationRowCallBackHandler implements RowCallbackHandler {

        private Map<Integer, TravelReservation> travelReservations = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;
            int tr_id = resultSet.getInt(index++);
            int id_travel = resultSet.getInt(index++);
            int total_space = resultSet.getInt(index++);
            int sold_space = resultSet.getInt(index++);
            int available_space = resultSet.getInt(index++);
            Travel travel = travelService.findOne(id_travel);

            TravelReservation tr = travelReservations.get(tr_id);
            if (tr == null) {
                tr = new TravelReservation(tr_id, travel, total_space, sold_space, available_space);
                travelReservations.put(tr.getTr_id(), tr);
            }
        }

        public List<TravelReservation> getTravelReservation() {
            return new ArrayList<>(travelReservations.values());
        }
    }

    @Override
    public List<TravelReservation> findAll() {
        String sql =
                "SELECT tr.tr_id, tr.id_travel, tr.total_space, tr.sold_space, tr.available_space " +
                        "FROM travel_reservation tr ORDER BY tr.tr_id";

        TravelReservationRowCallBackHandler rowCallBackHandler = new TravelReservationRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler);
        return rowCallBackHandler.getTravelReservation();
    }

    @Override
    public List<TravelReservation> findAll(int travelId) {
        String sql =
                "SELECT tr.tr_id, tr.id_travel, tr.total_space, tr.sold_space, tr.available_space " +
                        "FROM travel_reservation tr WHERE tr.id_travel = ? ORDER BY tr.tr_id";

        TravelReservationRowCallBackHandler rowCallBackHandler = new TravelReservationRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, travelId);
        return rowCallBackHandler.getTravelReservation();
    }

    @Override
    public TravelReservation findOne(int travelId) {
        String sql =
                "SELECT tr.tr_id, tr.id_travel, tr.total_space, tr.sold_space, tr.available_space " +
                        "FROM travel_reservation tr WHERE tr.id_travel = ?";

        TravelReservationRowCallBackHandler rowCallBackHandler = new TravelReservationRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, travelId);
        List<TravelReservation> trList = rowCallBackHandler.getTravelReservation();
        if (!trList.isEmpty()) {
            return trList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public void save(TravelReservation tr) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "INSERT INTO travel_reservation (id_travel, total_space, sold_space, available_space) " +
                        "VALUES (?, ?, ?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setInt(index++, tr.getTravel().getId());
                preparedStatement.setInt(index++, tr.getTotalSpace());
                preparedStatement.setInt(index++, tr.getSoldSpace());
                preparedStatement.setInt(index++, tr.getAvailableSpace());
                return preparedStatement;
            }
        };

        jdbcTemplate.update(preparedStatementCreator);
    }

    @Transactional
    @Override
    public void update(TravelReservation tr) {
        String sql =
                "UPDATE travel_reservation tr " +
                        "SET tr.sold_space = ?, tr.available_space = ? " +
                        "WHERE tr.tr_id = ?";

        jdbcTemplate.update(sql, tr.getSoldSpace(), tr.getAvailableSpace(), tr.getTr_id());
    }

    @Override
    public List<TravelReservation> findAll(LocalDate startDate, LocalDate endDate, String sortDate, String sortTravelForReportsByDestination,
                                           String sortTravelForReportsByVehicle, String sortTravelForReportsByTotalSpace, String sortTravelForReportsBySoldSpace,
                                           String sortTravelForReportsByTotalPrice) {

        ArrayList<Object> argList = new ArrayList<Object>();
        String sql = "SELECT tr.tr_id, tr.id_travel, tr.total_space, tr.sold_space, tr.available_space FROM travel_reservation tr " +
                "LEFT JOIN travel t on tr.id_travel = t.id_travel " +
                "LEFT JOIN destination d on d.id_destination = t.id_destination " +
                "LEFT JOIN vehicle v on v.id_vehicle = t.id_vehicle " +
                "LEFT JOIN accommodation_unit a on a.id_accommodation_unit = t.id_accommodation_unit ";

        StringBuffer whereSql = new StringBuffer(" WHERE ");
        boolean hasArgs = false;

        if (startDate.isAfter(LocalDate.of(1999, 1, 1)) || endDate.isBefore(LocalDate.of(2099, 1, 1))) {
            if(hasArgs) {
                sql += whereSql;
            }
            whereSql.append("t.travel_start_date >= ? AND t.travel_end_date <= ? ");
            hasArgs = true;
            argList.add(startDate);
            argList.add(endDate);
        }

        if(hasArgs) {
            sql += whereSql;
        }

        boolean hasSort = false;

        if (!sortTravelForReportsByDestination.equals("Default")) {
            sql += " ORDER BY d.destination_city " + sortTravelForReportsByDestination;
            hasSort = true;
        }

        if (!sortDate.equals("Default")) {
            if (hasSort) {
                sql += ", t.travel_start_date " + sortDate;
            } else {
                sql += " ORDER BY t.travel_start_date " + sortDate;
                hasSort = true;
            }
        }

        if (!sortTravelForReportsByVehicle.equals("Default")) {
            if (hasSort) {
                sql += ", v.vehicle_description " + sortTravelForReportsByVehicle;
            } else {
                sql += " ORDER BY v.vehicle_description " + sortTravelForReportsByVehicle;
                hasSort = true;
            }
        }

        if (!sortTravelForReportsByTotalSpace.equals("Default")) {
            if (hasSort) {
                sql += ", tr.total_space " + sortTravelForReportsByTotalSpace;
            } else {
                sql += " ORDER BY tr.total_space " + sortTravelForReportsByTotalSpace;
                hasSort = true;
            }
        }

        if (!sortTravelForReportsBySoldSpace.equals("Default")) {
            if (hasSort) {
                sql += ", tr.sold_space " + sortTravelForReportsBySoldSpace;
            } else {
                sql += " ORDER BY tr.sold_space " + sortTravelForReportsBySoldSpace;
                hasSort = true;
            }
        }

        if (!sortTravelForReportsByTotalPrice.equals("Default")) {
            if (hasSort) {
                sql += ", (t.travel_price * tr.sold_space) " + sortTravelForReportsByTotalPrice;
            } else {
                sql += " ORDER BY (t.travel_price * tr.sold_space) " + sortTravelForReportsByTotalPrice;
                hasSort = true;
            }
        }

        if (!hasSort) {
            sql += " ORDER BY t.id_travel";
        }

        return jdbcTemplate.query(sql, argList.toArray(), new TravelReservationRowMapper());
    }
}
