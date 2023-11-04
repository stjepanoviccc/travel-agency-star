package com.sr182022.travelagencystar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("demo")
public class DemoController {

    @GetMapping("sveOsobe")
    @ResponseBody
    public String demoChildren() {
        String htmlPrikaz = "<!DOCTYPE html>\r\n"
                + "<html>\r\n"
                + "<head>\r\n"
                + "<meta charset=\"UTF-8\">\r\n"
                + "<title>Insert title here</title>\r\n"
                + "</head>\r\n"
                + "<body>\r\n"
                + "<a href='/dodaj-osobu.html'>Dodaj osobu</a>"
                + "</body>\r\n"
                + "</html>";
        return htmlPrikaz;
    }
}
