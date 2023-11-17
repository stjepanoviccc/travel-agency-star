package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.Role;
import com.sr182022.travelagencystar.model.User;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final ResourceLoader resourceLoader;
    public UserController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/dashboard")
    public String getDashboardPage(Model model) {
        // try reading from files, if success then add to model and use in dashboard.html
        try {
            List<User> usersList = new ArrayList<>();
            // Retrieve the resource using ResourceLoader
            // Retrieve the File object from the resource
            // Use BufferedReader to read from the file
            Resource resource = resourceLoader.getResource("classpath:static/testingTextFiles/users.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                User user = new User();
                user.setId(Integer.parseInt(data[0]));
                user.setUsername(data[1]);
                user.setName(data[5]);
                user.setSurname(data[4]);
                user.setRegisteredDate(LocalDateTime.parse(data[8]));
                user.setRole(Role.valueOf(data[9]));
                usersList.add(user);
            }
            model.addAttribute("dashboardContent", usersList);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file from users.txt", e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Error parsing id from users.txt", e);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Error parsing registered date from users.txt", e);
        }

        return "dashboard";
    }
}
