package com.sr182022.travelagencystar.DAO;

import com.sr182022.travelagencystar.model.Coupon;

import java.util.List;

public interface ICouponDAO {
    List<Coupon> findAll();
    int save(Coupon c);
    void delete(int couponId);
}
