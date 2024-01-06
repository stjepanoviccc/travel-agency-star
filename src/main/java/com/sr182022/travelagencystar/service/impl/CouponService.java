package com.sr182022.travelagencystar.service.impl;

import com.sr182022.travelagencystar.DAO.ICouponDAO;
import com.sr182022.travelagencystar.model.Coupon;
import com.sr182022.travelagencystar.service.ICouponService;
import com.sr182022.travelagencystar.service.ITravelService;
import com.sr182022.travelagencystar.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CouponService implements ICouponService {
    private final ICouponDAO couponDAO;
    private final ITravelService travelService;

    @Autowired
    public CouponService(ICouponDAO couponDAO, ITravelService travelService) {
        this.couponDAO = couponDAO;
        this.travelService = travelService;
    }

    @Override
    public List<Coupon> findAll() {
        return couponDAO.findAll();
    }

    @Override
    public void save(Coupon c) {
        couponDAO.save(c);
    }

    @Override
    public boolean validate(Coupon c, List<String> selectedCategories, List<String> selectedTravelIDs) {
        if(c.getStartDate()==null || c.getDiscount() == null) {
            return false;
        }
        if(selectedCategories == null && selectedTravelIDs == null) {
            return false;
        }
        return true;
    }

    @Override
    public void createCouponsBasedOnArguments(Coupon coupon, List<String> selectedCategories, List<String> selectedTravelIDs) {
        List<Coupon> couponsToSave = new ArrayList<Coupon>();

        // it will save coupon for each travel id differently so it will be more relations in table not only one travel, this happens if travel is actually selected
        if(selectedTravelIDs != null) {
            for(String strTravelId : selectedTravelIDs) {
                Coupon newCoupon = new Coupon();
                setDefaultCoupon(newCoupon, coupon);
                setSelectedCategories(selectedCategories, newCoupon);
                int travelId = Integer.parseInt(strTravelId);
                newCoupon.setTravel(travelService.findOne(travelId));

                save(newCoupon);
            }
        }

        // if none travels are selected
        else {
            Coupon newCoupon = new Coupon();
            setDefaultCoupon(newCoupon, coupon);
            setSelectedCategories(selectedCategories, newCoupon);
            newCoupon.setTravel(null);

            save(newCoupon);
        }
    }

    @Override
    public void setDefaultCoupon(Coupon returnCoupon, Coupon contextCoupon) {
        returnCoupon.setDiscount(contextCoupon.getDiscount());
        returnCoupon.setStartDate(contextCoupon.getStartDate());
        if(contextCoupon.getEndDate() == null || (DateTimeUtil.calculateDaysBetween(contextCoupon.getStartDate(), contextCoupon.getEndDate()) < 0)) {
            returnCoupon.setEndDate(contextCoupon.getStartDate());
        } else {
            returnCoupon.setEndDate(contextCoupon.getEndDate());
        }
        returnCoupon.setSummer(false);
        returnCoupon.setWinter(false);
        returnCoupon.setLast_min(false);
        returnCoupon.setNew_year(false);
    }

    @Override
    public void setSelectedCategories(List<String> selectedCategories, Coupon newCoupon) {
        if(selectedCategories != null) {
            for(String category : selectedCategories) {
                switch(category) {
                    case "Winter":
                        newCoupon.setWinter(true);
                        break;
                    case "Summer":
                        newCoupon.setSummer(true);
                        break;
                    case "Last_min":
                        newCoupon.setLast_min(true);
                        break;
                    case "New_year":
                        newCoupon.setNew_year(true);
                        break;
                }
            }
        }
    }

    @Override
    public void delete(int couponId) {
        couponDAO.delete(couponId);
    }
}

