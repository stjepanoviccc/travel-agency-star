package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.TravelReservation;
import com.sr182022.travelagencystar.service.ITravelReservation;
import com.sr182022.travelagencystar.service.ITravelService;
import com.sr182022.travelagencystar.utils.CheckRoleUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/report")
public class ReportController {
    private final ITravelService travelService;
    private final ITravelReservation trService;

    @Autowired
    public ReportController(ITravelService travelService, ITravelReservation trService) {
        this.travelService = travelService;
        this.trService = trService;
    }

    @GetMapping
    public String getReportPage(HttpSession session, Model model) {
        try {
            if(!CheckRoleUtil.RoleAdministrator(session)) {
                return ErrorController.permissionErrorReturn;
            }

            List<TravelReservation> trs = trService.findAll();
            model.addAttribute("trList", trs);

            float totalPriceForAll = 0;
            int totalSold = 0;
            for(TravelReservation trr: trs) {
                totalSold += trr.getSoldSpace();
                totalPriceForAll += trr.getTravel().getPrice() * trr.getSoldSpace();
            }

            model.addAttribute("totalPriceForAll", totalPriceForAll);
            model.addAttribute("totalSold", totalSold);

            return "report";
        }
        catch(Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }

    @GetMapping(value = "filterTravelReportItem", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<TravelReservation> filterTravelReportItem(HttpSession session, @RequestParam(required = false) LocalDate startDate, @RequestParam(required = false) LocalDate endDate,
                                         @RequestParam(required = false) String sortDate, @RequestParam(required=false) String sortTravelForReportsByDestination,
                                         @RequestParam(required = false) String sortTravelForReportsByVehicle,
                                         @RequestParam(required = false) String sortTravelForReportsByTotalSpace,
                                         @RequestParam(required = false) String sortTravelForReportsBySoldSpace,
                                         @RequestParam(required = false) String sortTravelForReportsByTotalPrice) {
        List<TravelReservation> trsAfterSorting = trService.findAll(startDate, endDate, sortDate, sortTravelForReportsByDestination, sortTravelForReportsByVehicle,
                sortTravelForReportsByTotalSpace, sortTravelForReportsBySoldSpace, sortTravelForReportsByTotalPrice);
        return trsAfterSorting;
    };
}
