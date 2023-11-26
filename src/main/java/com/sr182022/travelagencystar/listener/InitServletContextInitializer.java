package com.sr182022.travelagencystar.listener;

import com.sr182022.travelagencystar.service.DestinationService.DestinationService;
import com.sr182022.travelagencystar.service.UserService.UserService;
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

        servletContext.setAttribute(UserService.USERS_LIST_KEY, new ArrayList<>());
        servletContext.setAttribute(DestinationService.DESTINATIONS_LIST_KEY, new ArrayList<>());

        System.out.println("Success! ServletContextInitializer");
    }
}
