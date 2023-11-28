package com.sr182022.travelagencystar.listener;

import com.sr182022.travelagencystar.DAO.AccommodationUnitDAO.AccommodationUnitDAO;
import com.sr182022.travelagencystar.DAO.DestinationDAO.DestinationDAO;
import com.sr182022.travelagencystar.DAO.ReviewDAO.ReviewDAO;
import com.sr182022.travelagencystar.DAO.UserDAO.UserDAO;
import com.sr182022.travelagencystar.DAO.VehicleDAO.VehicleDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class InitServletContextInitializer implements ServletContextInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        System.out.println("Initializaton ServletContextInitializer");

        servletContext.setAttribute(UserDAO.USERS_LIST_KEY, new ArrayList<>());
        servletContext.setAttribute(DestinationDAO.DESTINATIONS_LIST_KEY, new ArrayList<>());
        servletContext.setAttribute(VehicleDAO.VEHICLES_LIST_KEY, new ArrayList<>());
        servletContext.setAttribute(ReviewDAO.REVIEWS_LIST_KEY, new ArrayList<>());
        servletContext.setAttribute(AccommodationUnitDAO.ACCOMMODATION_UNITS_LIST_KEY, new ArrayList<>());

        System.out.println("Success! ServletContextInitializer");
    }
}
