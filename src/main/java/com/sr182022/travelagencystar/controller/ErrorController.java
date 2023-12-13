package com.sr182022.travelagencystar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/login-error")
    public String getLoginErrorPage() {
        return "/errorPages/loginErrorPage";
    }

    @GetMapping("/permission-error")
    public String getPermissionErrorPage() { return "/errorPages/permissionErrorPage"; }

    @GetMapping("/route-error")
    public String getRouteErrorPage() { return "/errorPages/routeErrorPage"; }

}
