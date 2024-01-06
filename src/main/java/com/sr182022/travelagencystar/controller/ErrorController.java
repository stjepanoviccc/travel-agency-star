package com.sr182022.travelagencystar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    public static final String loginErrorReturn = "redirect:/login-error";
    public static final String permissionErrorReturn = "redirect:/permission-error";
    public static final String routeErrorReturn = "redirect:/route-error";
    public static final String internalErrorReturn = "redirect:/internal-error";
    public static final String availableSpaceErrorReturn = "redirect:/space-error";
    public static final String couponErrorReturn = "redirect:/coupon-error";

    @GetMapping("/login-error")
    public String getLoginErrorPage() {
        return "/errorPages/loginErrorPage";
    }

    @GetMapping("/permission-error")
    public String getPermissionErrorPage() { return "/errorPages/permissionErrorPage"; }

    @GetMapping("/route-error")
    public String getRouteErrorPage() { return "/errorPages/routeErrorPage"; }

    @GetMapping("/internal-error")
    public String getInternalErrorPage() { return "/errorPages/internalErrorPage"; }

    @GetMapping("/space-error")
    public String getAvailableSpaceErrorPage() { return "/errorPages/availableSpaceErrorPage"; }

    @GetMapping("/coupon-error")
    public String getCouponErrorPage() { return "/errorPages/couponErrorPage"; }
}
