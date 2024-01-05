package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.utils.CheckRoleUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/coupon")
public class CouponController {

    @GetMapping
    public String getCouponPage(HttpSession session) {
        if(!CheckRoleUtil.RoleAdministrator(session)) {
            return ErrorController.permissionErrorReturn;
        }

        return "coupon";
    }
}
