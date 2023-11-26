package com.sr182022.travelagencystar.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class MyConfiguration {
    @Bean (name={"AppMemory"}, initMethod = "", destroyMethod = "")
    public AppMemory getAppMemory() {
        return new AppMemory();
    }

    public class AppMemory extends HashMap {}
}
