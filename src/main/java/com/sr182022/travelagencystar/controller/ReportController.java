package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.utils.CheckRoleUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/report")
public class ReportController {

    @GetMapping
    public String getReportPage(HttpSession session) {
        try {
            if(!CheckRoleUtil.RoleAdministrator(session)) {
                return ErrorController.permissionErrorReturn;
            }
            return "report";
        }
        catch(Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }
}
