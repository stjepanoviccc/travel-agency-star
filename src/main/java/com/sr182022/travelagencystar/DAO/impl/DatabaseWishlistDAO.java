package com.sr182022.travelagencystar.DAO.impl;

import com.sr182022.travelagencystar.DAO.IWishlistDAO;
import com.sr182022.travelagencystar.model.WishlistItem;
import com.sr182022.travelagencystar.service.ITravelService;
import com.sr182022.travelagencystar.service.IUserService;
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
public class DatabaseWishlistDAO implements IWishlistDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final IUserService userService;
    private final ITravelService travelService;

    public DatabaseWishlistDAO(IUserService userService, ITravelService travelService) {
        this.userService = userService;
        this.travelService = travelService;
    }

    private class WishlistRowCallBackHandler implements RowCallbackHandler {

        private Map<Integer, WishlistItem> wishlist = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;
            int id_user = resultSet.getInt(index++);
            int id_travel = resultSet.getInt(index++);
            int id_wishlist = resultSet.getInt(index++);
            WishlistItem wishlistItem = wishlist.get(id_wishlist);
            if(wishlistItem == null) {
                wishlistItem = new WishlistItem(userService.findOne(id_user), travelService.findOne(id_travel), id_wishlist);
                wishlist.put(id_wishlist, wishlistItem);
            }
        }

        public List<WishlistItem> getWishlist() {
            return new ArrayList<>(wishlist.values());
        }
    }

    @Transactional
    @Override
    public List<WishlistItem> findAll(int userId) {
        String sql =
                "SELECT w.id_user, w.id_travel, w.id_wishlist FROM wishlist_user_travel w " +
                        "WHERE w.id_user = ? ORDER BY w.id_user, w.id_travel, w.id_wishlist" ;

        WishlistRowCallBackHandler rowCallBackHandler = new WishlistRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, userId);
        List<WishlistItem> wishlistForUser = rowCallBackHandler.getWishlist();
        if(!wishlistForUser.isEmpty()) {
            return wishlistForUser;
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public void save(int userId, int travelId) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "INSERT INTO wishlist_user_travel (id_user, id_travel)" +
                        "VALUES(?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setInt(index++, userId);
                preparedStatement.setInt(index++, travelId);

                return preparedStatement;
            }
        };

        jdbcTemplate.update(preparedStatementCreator);
    }

    @Transactional
    @Override
    public void delete(int userId, int travelId) {
        String sql = "DELETE FROM wishlist_user_travel w WHERE w.id_user = ? and w.id_travel = ?";
        jdbcTemplate.update(sql, userId, travelId);
    }
}
