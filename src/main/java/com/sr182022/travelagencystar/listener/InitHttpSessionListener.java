package com.sr182022.travelagencystar.listener;

import com.sr182022.travelagencystar.controller.InternationalizationController;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class InitHttpSessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent arg0) {
        System.out.println("Init session HttpSessionListener...");

        HttpSession session  = arg0.getSession();
        if (session.getAttribute(InternationalizationController.LOCALIZATION_KEY) == null) {
            session.setAttribute(InternationalizationController.LOCALIZATION_KEY, Locale.ENGLISH);
        }

        System.out.println("Success! HttpSessionListener.");
    }

    public void sessionDestroyed(HttpSessionEvent arg0) {
        System.out.println("Deleting session HttpSessionListener...");

        System.out.println("Success! HttpSessionListener.");
    }

}
