package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.Coupon;
import com.sr182022.travelagencystar.service.ICouponService;
import com.sr182022.travelagencystar.service.ITravelService;
import com.sr182022.travelagencystar.utils.CheckRoleUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/coupon")
public class CouponController {
    private final ITravelService travelService;
    private final ICouponService couponService;

    public CouponController(ITravelService travelService, ICouponService couponService) {
        this.travelService = travelService;
        this.couponService = couponService;
    }

    @GetMapping
    public String getCouponPage(HttpSession session, Model model) {
        if(!CheckRoleUtil.RoleAdministrator(session)) {
            return ErrorController.permissionErrorReturn;
        }
        List<Coupon> cps = couponService.findAll();
        model.addAttribute("coupons", couponService.findAll());
        model.addAttribute("travelCategories", travelService.findAllTravelCategories());
        model.addAttribute("travels", travelService.findAll());

        return "coupon";
    }

    @PostMapping("deleteCoupon")
    public String deleteCoupon(@RequestParam int couponId) {
        try {
            couponService.delete(couponId);
            return "redirect:/coupon";
        } catch(Exception e) {
            System.out.println(e);
            return ErrorController.couponErrorReturn;
        }
    }

    @PostMapping("addCoupon")
    public String addCoupon(HttpSession session,
                            @RequestParam(required = false) Float discount,
                            @RequestParam(required = false) LocalDate startDate,
                            @RequestParam(required = false) LocalDate endDate,
                            @RequestParam(required = false) String selectedCategories,
                            @RequestParam(required = false) String selectedTravelIDs) {

        try {
            List<String> categoriesList = null;
            List<String> travelIDsList = null;
            if(selectedCategories != null) {
                categoriesList = Arrays.asList(selectedCategories.split(","));
            }
            if(selectedTravelIDs != null) {
                travelIDsList = Arrays.asList(selectedTravelIDs.split(","));
            }

            Coupon coupon = new Coupon(startDate, endDate, discount);
            boolean validation = couponService.validate(coupon, categoriesList, travelIDsList);
            if(!validation) {
                return ErrorController.couponErrorReturn;
            }
            couponService.createCouponsBasedOnArguments(coupon, categoriesList, travelIDsList);

            return "redirect:/coupon";
        } catch(Exception e) {
            System.out.println(e);
            return ErrorController.internalErrorReturn;
        }

    }
}
