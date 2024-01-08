package com.sr182022.travelagencystar.service;

import com.sr182022.travelagencystar.model.Coupon;

import java.util.List;

public interface ICouponService {
    List<Coupon> findAll();
    int save(Coupon c);
    Coupon validate(Coupon c, List<String> selectedCategories, List<String> selectedTravelIDs);
    void createCouponsBasedOnArguments(Coupon coupon, List<String> selectedCategories, List<String> selectedTravelIDs);
    void setDefaultCoupon(Coupon returnCoupon, Coupon contextCoupon);
    void setSelectedCategories(List<String> selectedCategories, Coupon newCoupon);
    void delete(int couponId);
}
