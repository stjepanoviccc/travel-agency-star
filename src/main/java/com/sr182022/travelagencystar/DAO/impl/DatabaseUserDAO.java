package com.sr182022.travelagencystar.DAO.impl;

import com.sr182022.travelagencystar.DAO.IUserDao;
import com.sr182022.travelagencystar.model.Role;
import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.utils.DateTimeUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DatabaseUserDAO implements IUserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class UserRowCallBackHandler implements RowCallbackHandler {

        private Map<Integer, User> users = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;
            int id_user = resultSet.getInt(index++);
            String username = resultSet.getString(index++);
            String password = resultSet.getString(index++);
            String email = resultSet.getString(index++);
            String surname = resultSet.getString(index++);
            String name = resultSet.getString(index++);
            LocalDate birth_date = DateTimeUtil.convertTimestampToLocalDate(resultSet.getTimestamp(index++));
            String user_address = resultSet.getString(index++);
            int user_phone = resultSet.getInt(index++);
            LocalDateTime user_registered_date = DateTimeUtil.convertTimestampToLocalDateTime(resultSet.getTimestamp(index++));
            String user_role = resultSet.getString(index++);
            Role role = Role.valueOf(user_role);

            User user = users.get(id_user);
            if (user == null) {
                user = new User(id_user, username, password, email, surname, name, birth_date, user_address, user_phone, user_registered_date, role);
                users.put(user.getId(), user);
            }
        }

        public List<User> getUsers() {
            return new ArrayList<>(users.values());
        }
    }

    @Override
    public List<User> findAll() {
        String sql =
                "SELECT u.id_user, u.username, u.password, u.email, u.surname, u.name, u.birth_date, u.user_address, u.user_phone, u.user_registered_date," +
                        "u.user_role " +
                        "FROM user u ORDER BY u.id_user";

        UserRowCallBackHandler rowCallBackHandler = new UserRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler);
        return rowCallBackHandler.getUsers();
    }

    @Override
    public List<User> findAll(Role role) {
        String sql =
                "SELECT u.id_user, u.username, u.password, u.email, u.surname, u.name, u.birth_date, u.user_address, u.user_phone, u.user_registered_date," +
                        "u.user_role " +
                        "FROM user u WHERE u.user_role = role ORDER BY u.id_user";

        UserRowCallBackHandler rowCallBackHandler = new UserRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, role);
        return rowCallBackHandler.getUsers();
    }

    @Override
    public User findOne(int userId) {
        String sql =
                "SELECT u.id_user, u.username, u.password, u.email, u.surname, u.name, u.birth_date, u.user_address, u.user_phone, u.user_registered_date," +
                        "u.user_role " +
                        "FROM user u WHERE u.id_user = ?";

        UserRowCallBackHandler rowCallBackHandler = new UserRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, userId);
        return rowCallBackHandler.getUsers().get(0);
    }

    @Override
    public User findOne(String username) {
        String sql =
                "SELECT u.id_user, u.username, u.password, u.email, u.surname, u.name, u.birth_date, u.user_address, u.user_phone, u.user_registered_date," +
                        "u.user_role " +
                        "FROM user u WHERE u.username = ?";

        UserRowCallBackHandler rowCallBackHandler = new UserRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, username);

        // handle index out of bound
        List<User> userList = rowCallBackHandler.getUsers();
        if (!userList.isEmpty()) {
            return userList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public void save(User newUser) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "INSERT INTO user (username, email, password, surname, name, birth_date, user_address, user_phone, user_registered_date, user_role)" +
                        " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setString(index++, newUser.getUsername());
                preparedStatement.setString(index++, newUser.getEmail());
                preparedStatement.setString(index++, newUser.getPassword());
                preparedStatement.setString(index++, newUser.getSurname());
                preparedStatement.setString(index++, newUser.getName());
                preparedStatement.setTimestamp(index++, DateTimeUtil.convertLocalDateToTimestamp(newUser.getBirthDate()));
                preparedStatement.setString(index++, newUser.getAddress());
                preparedStatement.setInt(index++, newUser.getPhone());
                preparedStatement.setTimestamp(index++, DateTimeUtil.convertLocalDateTimeToTimestamp(LocalDateTime.now()));
                preparedStatement.setString(index++, Role.Passenger.name());

                return preparedStatement;
            }
        };

        jdbcTemplate.update(preparedStatementCreator);
    }

    @Transactional
    @Override
    public void update(User editUser) {
        String sql =
                "UPDATE user u " +
                        "SET u.username = ?, u.email = ?, u.password = ?, u.surname = ?, u.name = ?, u.birth_date = ?, u.user_address = ?, u.user_phone = ? " +
                        "WHERE u.id_user = ?";

        jdbcTemplate.update(sql, editUser.getUsername(), editUser.getEmail(), editUser.getPassword(), editUser.getSurname(), editUser.getName(),
                DateTimeUtil.convertLocalDateToTimestamp(editUser.getBirthDate()), editUser.getAddress(), editUser.getPhone(), editUser.getId());
    }

    @Transactional
    @Override
    public void delete(int userId) {
        String sql = "DELETE FROM user u WHERE u.id_user = ?";
        jdbcTemplate.update(sql, userId);
    }
}
