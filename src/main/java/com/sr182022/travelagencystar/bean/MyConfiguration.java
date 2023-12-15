package com.sr182022.travelagencystar.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.HashMap;
import java.util.Locale;

@Configuration
public class MyConfiguration {
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.ENGLISH);
        return slr;
    }

    @Bean (name={"AppMemory"}, initMethod = "", destroyMethod = "")
    public AppMemory getAppMemory() {
        return new AppMemory();
    }

    public class AppMemory extends HashMap {}
}
