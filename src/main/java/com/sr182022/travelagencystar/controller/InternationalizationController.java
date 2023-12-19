package com.sr182022.travelagencystar.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@Controller
@RequestMapping(value="/i18n")
public class InternationalizationController {

    public static final String LOCALIZATION_KEY = "localization";

    @Autowired
    private LocaleResolver localeResolver;

    @GetMapping("changeLanguage")
    public String changeLanguage(@RequestParam String lang, HttpServletRequest request, HttpServletResponse response) {
        try {
            Locale newLocale = null;
            if (lang.equals("sr")) {
                newLocale = Locale.forLanguageTag("sr");
            } else if (lang.equals("en")) {
                newLocale = Locale.ENGLISH;
            }

            localeResolver.setLocale(request, response, newLocale);

            return "redirect:/";
        } catch(Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }
}