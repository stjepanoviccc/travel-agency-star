package com.sr182022.travelagencystar.DAO.UserDAO;

import com.sr182022.travelagencystar.model.Role;
import com.sr182022.travelagencystar.model.User;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAO implements IUserDao {
    private final ResourceLoader resourceLoader;
    ServletContext servletContext;
    public static final String USERS_LIST_KEY = "usersList";

    @Autowired
    public UserDAO(ResourceLoader resourceLoader, ServletContext servletContext) {
        this.resourceLoader = resourceLoader;
        this.servletContext = servletContext;
    }

    @PostConstruct
    public void init() {
        List<User> usersList = (List<User>) servletContext.getAttribute(USERS_LIST_KEY);
        usersList.addAll(Load());
    }

    @Override
    public List<User> Load() {
        try {
            List<User> usersList = new ArrayList<>();
            Resource resource = resourceLoader.getResource("classpath:static/testingTextFiles/users.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                User user = new User();
                user.setId(Integer.parseInt(data[0]));
                user.setUsername(data[1]);
                user.setPassword(data[2]);
                user.setEmail(data[3]);
                user.setSurname(data[4]);
                user.setName(data[5]);
                user.setAddress(data[6]);
                user.setPhone(Integer.parseInt(data[7]));
                user.setBirthDate(LocalDate.parse(data[8]));
                user.setRegisteredDate(LocalDateTime.parse(data[9]));
                user.setRole(Role.valueOf(data[10]));
                usersList.add(user);
            }

            return usersList;

        } catch (IOException | NumberFormatException | DateTimeParseException | EnumConstantNotPresentException  e) {
            throw new RuntimeException("Error processing file users.txt", e);
        }
    }

    @Override
    public List<User> findAllUsers() {
        List<User> usersList = (List<User>) servletContext.getAttribute(USERS_LIST_KEY);
        return usersList;
    }

    @Override
    public User findUserById(int userId) {
        List<User> usersList = (List<User>) servletContext.getAttribute(USERS_LIST_KEY);
        Optional<User> userOptional = usersList.stream().filter(u -> u.getId() == userId).findFirst();
        return userOptional.orElse(null);
    }

    @Override
    public void addNewUser(User newUser) {
        List<User> usersList = (List<User>) servletContext.getAttribute(USERS_LIST_KEY);
        newUser.setId(generateNextId());
        newUser.setRegisteredDate(LocalDateTime.now());
        newUser.setRole(Role.Passenger);
        usersList.add(newUser);
    }

    @Override
    public void editUser(User editUser) {
        User existingUser = findUserById(editUser.getId());
        existingUser.setUsername(editUser.getUsername());
        existingUser.setPassword(editUser.getPassword());
        existingUser.setEmail(editUser.getEmail());
        existingUser.setSurname(editUser.getSurname());
        existingUser.setName(editUser.getName());
        existingUser.setAddress(editUser.getAddress());
        existingUser.setPhone(editUser.getPhone());
        existingUser.setBirthDate(editUser.getBirthDate());
    }

    @Override
    public void deleteUser(int userId) {
        List<User> usersList = (List<User>) servletContext.getAttribute(USERS_LIST_KEY);
        usersList.remove(findUserById(userId));
    }

    @Override
    public int generateNextId() {
        List<User> usersList = (List<User>) servletContext.getAttribute(USERS_LIST_KEY);
        return usersList.stream().mapToInt(User::getId).max().orElse(0)+1;
    };
};