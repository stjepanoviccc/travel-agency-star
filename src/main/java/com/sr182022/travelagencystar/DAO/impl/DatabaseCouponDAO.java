package com.sr182022.travelagencystar.DAO.impl;

import com.sr182022.travelagencystar.DAO.ICouponDAO;
import com.sr182022.travelagencystar.model.*;
import com.sr182022.travelagencystar.service.ITravelService;
import com.sr182022.travelagencystar.utils.DateTimeUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DatabaseCouponDAO implements ICouponDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final ITravelService travelService;

    public DatabaseCouponDAO(ITravelService travelService) {
        this.travelService = travelService;
    }

    private class CouponRowCallBackHandler implements RowCallbackHandler {

        private Map<Integer, Coupon> coupons = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;
            int id_coupon = resultSet.getInt(index++);
            Integer id_travel = resultSet.getInt(index++);

            Travel travel = new Travel();
            if(id_travel == null) {
                travel = null;
            } else {
                travel = travelService.findOne(id_travel);
            }

            boolean Winter = resultSet.getBoolean(index++);
            boolean Summer = resultSet.getBoolean(index++);
            boolean New_year = resultSet.getBoolean(index++);
            boolean Last_min = resultSet.getBoolean(index++);

            LocalDate coupon_start_date = DateTimeUtil.convertTimestampToLocalDate(resultSet.getTimestamp(index++));
            LocalDate coupon_end_date = DateTimeUtil.convertTimestampToLocalDate(resultSet.getTimestamp(index++));

            float coupon_discount = resultSet.getFloat(index++);

            Coupon coupon = coupons.get(id_coupon);
            if (coupon == null) {
                coupon = new Coupon(id_coupon, travel, Winter, Summer, New_year, Last_min, coupon_start_date, coupon_end_date, coupon_discount);
                coupons.put(coupon.getId(), coupon);
            }
        }

        public List<Coupon> getCoupons() {
            return new ArrayList<>(coupons.values());
        }
    }

    @Override
    public List<Coupon> findAll() {
        String sql =
                "SELECT c.id_coupon, c.id_travel, c.Winter, c.Summer, c.New_year, c.Last_min, c.coupon_start_date, c.coupon_end_date, c.coupon_discount " +
                        "FROM coupon c ORDER BY c.id_coupon";

        CouponRowCallBackHandler rowCallBackHandler = new CouponRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler);
        return rowCallBackHandler.getCoupons();
    }

    @Override
    public int save(Coupon c) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                Integer id_travel;
                if(c.getTravel() == null) {
                    id_travel = null;
                } else {
                    id_travel = c.getTravel().getId();
                }

                String sql = "INSERT INTO coupon (id_travel, Winter, Summer, Last_min, New_year, coupon_start_date, coupon_end_date, coupon_discount) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                if (id_travel == null) {
                    preparedStatement.setNull(index++, Types.INTEGER);
                } else {
                    preparedStatement.setInt(index++, id_travel);
                }
                preparedStatement.setBoolean(index++, c.isWinter());
                preparedStatement.setBoolean(index++, c.isSummer());
                preparedStatement.setBoolean(index++, c.isLast_min());
                preparedStatement.setBoolean(index++, c.isNew_year());
                preparedStatement.setTimestamp(index++, DateTimeUtil.convertLocalDateToTimestamp(c.getStartDate()));
                preparedStatement.setTimestamp(index++, DateTimeUtil.convertLocalDateToTimestamp(c.getEndDate()));
                preparedStatement.setFloat(index++, c.getDiscount());

                return preparedStatement;
            }
        };

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Transactional
    @Override
    public void delete(int couponId) {
        String sql = "DELETE FROM coupon c WHERE c.id_coupon = ?";
        jdbcTemplate.update(sql, couponId);
    }
}
