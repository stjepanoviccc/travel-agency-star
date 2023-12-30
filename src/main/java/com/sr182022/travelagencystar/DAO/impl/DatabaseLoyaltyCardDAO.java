package com.sr182022.travelagencystar.DAO.impl;

import com.sr182022.travelagencystar.DAO.ILoyaltyCardDAO;
import com.sr182022.travelagencystar.model.LoyaltyCard;
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
}
