package com.sr182022.travelagencystar.DAO.impl;

import com.sr182022.travelagencystar.DAO.ITravelReservationDAO;
import com.sr182022.travelagencystar.model.Reservation;
import com.sr182022.travelagencystar.model.Travel;
import com.sr182022.travelagencystar.model.TravelReservation;
import com.sr182022.travelagencystar.service.IReservationService;
import com.sr182022.travelagencystar.service.ITravelService;
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
public class DatabaseTravelReservationDAO implements ITravelReservationDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ITravelService travelService;

    public DatabaseTravelReservationDAO(ITravelService travelService) {
        this.travelService = travelService;
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
}
