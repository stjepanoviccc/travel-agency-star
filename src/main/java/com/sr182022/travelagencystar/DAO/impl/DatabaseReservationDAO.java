package com.sr182022.travelagencystar.DAO.impl;

import com.sr182022.travelagencystar.DAO.IReservationDAO;
import com.sr182022.travelagencystar.model.Destination;
import com.sr182022.travelagencystar.model.Reservation;
import com.sr182022.travelagencystar.model.Travel;
import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.service.ITravelService;
import com.sr182022.travelagencystar.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DatabaseReservationDAO implements IReservationDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final ITravelService travelService;
    private final IUserService userService;

    @Autowired
    public DatabaseReservationDAO(ITravelService travelService, IUserService userService) {
        this.travelService = travelService;
        this.userService = userService;
    }

    private class ReservationRowCallBackHandler implements RowCallbackHandler {

        private Map<Integer, Reservation> reservations = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;
            int id_reservation = resultSet.getInt(index++);
            int id_travel = resultSet.getInt(index++);
            int id_user = resultSet.getInt(index++);
            int passengers = resultSet.getInt(index++);

            Travel travel = travelService.findOne(id_travel);
            User user = userService.findOne(id_user);

            Reservation reservation = reservations.get(id_reservation);
            if (reservation == null) {
                reservation = new Reservation(id_reservation, travel, user, passengers);
            }
        }

        public List<Reservation> getReservations() {
            return new ArrayList<>(reservations.values());
        }
    }

    @Override
    public List<Reservation> findAll() {
        String sql =
                "SELECT r.id_reservation, r.id_travel, r.id_user, r.passengers " +
                        "FROM reservation r ORDER BY r.id_reservation";

        ReservationRowCallBackHandler rowCallBackHandler = new ReservationRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler);
        return rowCallBackHandler.getReservations();
    }

    @Override
    public Reservation findOne(int travelId, int userId) {
        String sql =
                "SELECT r.id_reservation, r.id_travel, r.id_user, r.passengers " +
                        "FROM reservation r WHERE r.id_travel = ? and r.id_user = ?";

        ReservationRowCallBackHandler rowCallBackHandler = new ReservationRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, travelId, userId);
        List<Reservation> reservationList = rowCallBackHandler.getReservations();
        if (!reservationList.isEmpty()) {
            return reservationList.get(0);
        } else {
            return null;
        }
    }

    @Override void save(int travelId, int userId, int passengers) {

    }

    @Override
    public void delete(int travelId, int userId) {
        String sql = "DELETE FROM reservation r WHERE r.id_travel = ? and r.id_user = ?";
        jdbcTemplate.update(sql, travelId, userId);
    }
}
