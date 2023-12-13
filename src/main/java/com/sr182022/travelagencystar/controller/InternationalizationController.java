package com.sr182022.travelagencystar.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Locale;

@Controller
@RequestMapping(value="/i18n")
public class InternationalizationController {

    public static final String LOCALIZATION_KEY = "localization";

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("changeLanguage")
    public void index(@RequestParam(defaultValue="en") String lang, HttpSession session, HttpServletResponse response) throws IOException {
        Locale localization = (Locale) session.getAttribute(LOCALIZATION_KEY);

        if(localization.getLanguage().equals(lang)) {
            response.sendRedirect("/");
            return;
        }
        System.out.println(lang);

        if(lang.equals("sr")) {
            localization = Locale.forLanguageTag("sr");
        } else if (lang.equals("en")) {
            localization = Locale.ENGLISH;
        }

        session.setAttribute(InternationalizationController.LOCALIZATION_KEY, localization);
        System.out.println(session.getAttribute(InternationalizationController.LOCALIZATION_KEY));
        response.sendRedirect("/");
    }
}