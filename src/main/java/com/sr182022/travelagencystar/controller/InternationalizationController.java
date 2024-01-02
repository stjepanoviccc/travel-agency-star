package com.sr182022.travelagencystar.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;

import java.util.*;

@Controller
@RequestMapping(value="/i18n")
public class InternationalizationController {

    public static final String LOCALIZATION_KEY = "localization";

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private MessageSource msgSource;

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
            System.out.println(e);
            return ErrorController.internalErrorReturn;
        }
    }

    @GetMapping(value = "getMessages", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> getMessages(HttpServletRequest req) {

        Map<String, String> msgList = new HashMap<>();
        Locale currentLocale = localeResolver.resolveLocale(req);

        msgList.put("textFinalDestination", msgSource.getMessage("textFinalDestination", null, currentLocale));
        msgList.put("textAccommodationUnit", msgSource.getMessage("textAccommodationUnit", null, currentLocale));
        msgList.put("textVehicle", msgSource.getMessage("textVehicle", null, currentLocale));
        msgList.put("textCategory", msgSource.getMessage("textCategory", null, currentLocale));
        msgList.put("textPrice", msgSource.getMessage("textPrice", null, currentLocale));
        msgList.put("textAddToWishlist", msgSource.getMessage("textAddToWishlist", null, currentLocale));
        msgList.put("cartPageTotalPrice", msgSource.getMessage("cartPageTotalPrice", null, currentLocale));

        return msgList;
    }
}