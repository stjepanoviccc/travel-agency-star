package com.sr182022.travelagencystar.DAO.impl;

import com.sr182022.travelagencystar.DAO.ILoyaltyCardDAO;
import com.sr182022.travelagencystar.model.LoyaltyCard;
import com.sr182022.travelagencystar.model.LoyaltyCardJunction;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DatabaseLoyaltyCardDAO implements ILoyaltyCardDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private class LoyaltyCardRowCallBackHandler implements RowCallbackHandler{

        private Map<Integer, LoyaltyCard> loyaltyCards = new LinkedHashMap<>();
        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;
            int id_loyalty_card = resultSet.getInt(index++);
            int points = resultSet.getInt(index++);
            int id_user = resultSet.getInt(index++);
            boolean activated = resultSet.getBoolean(index++);

            LoyaltyCard lc = loyaltyCards.get(id_loyalty_card);
            if(lc == null) {
                lc = new LoyaltyCard(id_loyalty_card, points, id_user, activated);
                loyaltyCards.put(lc.getId(), lc);
            }
        }

        public List<LoyaltyCard> getLoyaltyCards() { return new ArrayList<>(loyaltyCards.values());
        }
    }

    // selecting only inactive (activated = 0);
    @Override
    public List<LoyaltyCard> findAll() {
        String sql =
                "SELECT * FROM loyalty_card lc WHERE lc.activated = 0 ORDER BY lc.id_loyalty_card";
                LoyaltyCardRowCallBackHandler rowCallBackHandler = new LoyaltyCardRowCallBackHandler();
                jdbcTemplate.query(sql, rowCallBackHandler);
                return rowCallBackHandler.getLoyaltyCards();
    }

    @Override
    public LoyaltyCard findOne(int userId) {
        String sql =
                "SELECT l.id_loyalty_card, l.points, l.id_user, l.activated FROM loyalty_card l WHERE l.id_user =  ?";
        LoyaltyCardRowCallBackHandler rowCallBackHandler = new LoyaltyCardRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, userId);
        List<LoyaltyCard> lcList = rowCallBackHandler.getLoyaltyCards();
        if(!lcList.isEmpty()) {
            return lcList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public int save(int points, int userId, boolean activated) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                String sql =
                        "INSERT INTO loyalty_card(points, id_user, activated) VALUES(?, ?, ?)";

                PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setInt(index++, points);
                preparedStatement.setInt(index++, userId);
                preparedStatement.setBoolean(index++, activated);

                return preparedStatement;
            }
        };

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Transactional
    @Override
    public int update(int loyaltyCardId, int points, String casee) {
        String sql = "";
        switch(casee) {
            case "updateInitOrReduce":
                sql = "UPDATE loyalty_card lc SET lc.points = ?, lc.activated = 1 WHERE id_loyalty_card = ?";
                break;
            case "updateAdd":
                sql = "UPDATE loyalty_card lc SET lc.points = lc.points + ?, lc.activated = 1 WHERE id_loyalty_card = ?";
                break;
        }

        jdbcTemplate.update(sql, points, loyaltyCardId);
        return loyaltyCardId;
    }

    @Transactional
    @Override
    public int delete(int loyaltyCardId) {
        String sql =
                "DELETE FROM loyalty_card lc WHERE lc.id_loyalty_card = ?";

        jdbcTemplate.update(sql, loyaltyCardId);
        return loyaltyCardId;
    }

    // this under is like this because spec changed late and had to make it work.

    public void saveJunction(LoyaltyCard loyaltyCard, int addPoints) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                String sql =
                        "INSERT INTO loyalty_card_junction(id_junction, points) VALUES(?, ?)";

                PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setInt(index++, loyaltyCard.getId());
                preparedStatement.setInt(index++, addPoints);

                return preparedStatement;
            }
        };

        jdbcTemplate.update(preparedStatementCreator);
    }

    @Override
    public void deleteJunction(int loyaltyCardId) {
        String sql = "DELETE FROM loyalty_card_junction lc WHERE lc.id_junction = ?";
        jdbcTemplate.update(sql, loyaltyCardId);
    }

    private class LoyaltyCardJunctionRowCallBackHandler implements RowCallbackHandler{

        private Map<Integer, LoyaltyCardJunction> loyaltyCardJunctions = new LinkedHashMap<>();
        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;
            int id = resultSet.getInt(index++);
            int id_junction = resultSet.getInt(index++);
            int points = resultSet.getInt(index++);

            LoyaltyCardJunction lcj = loyaltyCardJunctions.get(id);
            if(lcj == null) {
                lcj = new LoyaltyCardJunction(id, id_junction, points);
                loyaltyCardJunctions.put(lcj.getId(), lcj);
            }
        }

        public List<LoyaltyCardJunction> getLoyaltyCardJunctions() { return new ArrayList<>(loyaltyCardJunctions.values());
        }
    }

    @Override
    public int takePointsFromJunction(int loyaltyCardId) {
        String sql =
                "SELECT lcj.id, lcj.id_junction, lcj.points FROM loyalty_card_junction lcj WHERE lcj.id_junction = ?";
        LoyaltyCardJunctionRowCallBackHandler rowCallBackHandler = new LoyaltyCardJunctionRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, loyaltyCardId);
        List<LoyaltyCardJunction> lcjList = rowCallBackHandler.getLoyaltyCardJunctions();
        if(!lcjList.isEmpty()) {
            return lcjList.get(0).getPoints();
        } else {
            return 0;
        }
    }

}
