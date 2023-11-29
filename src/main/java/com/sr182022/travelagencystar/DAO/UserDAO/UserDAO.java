package com.sr182022.travelagencystar.DAO.UserDAO;

import com.sr182022.travelagencystar.model.Destination;
import com.sr182022.travelagencystar.model.Role;
import com.sr182022.travelagencystar.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

@Repository
public class UserDAO implements IUserDao {
    @Value("${users.pathToFile}")
    private String pathToFile;

    public Map<Integer, User> Load() {
        try {
            Map<Integer, User> users = new HashMap<>();
            Path path = Paths.get(pathToFile);
            List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));

            for (String line : lines) {
                line = line.trim();
                if (line.equals("") || line.indexOf('#') == 0)
                    continue;

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

                users.put(Integer.parseInt(data[0]), user);
            }

            return users;

        } catch (IOException | NumberFormatException | DateTimeParseException | EnumConstantNotPresentException  e) {
            throw new RuntimeException("Error processing file users.txt", e);
        }
    }

    public void Save(Map<Integer, User> users) {

        try {
            Path path = Paths.get(pathToFile);
            List<String> lines = new ArrayList<>();

            for (User u : users.values()) {
                lines.add(u.toFileString());
            }

            Files.write(path, lines, Charset.forName("UTF-8"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> findAllUsers() {
        Map<Integer, User> users = Load();
        return new ArrayList<>(users.values());
    }

    @Override
    public User findUserById(int userId) {
        Map<Integer, User> users = Load();
        return users.get(userId);
    }

    @Override
    public void addNewUser(User newUser) {
        Map<Integer, User> users = Load();
        newUser.setId(generateNextId());
        newUser.setRegisteredDate(LocalDateTime.now());
        newUser.setRole(Role.Passenger);
        users.put(newUser.getId(), newUser);
        Save(users);
    }

    @Override
    public void editUser(User editUser) {
        Map<Integer, User> users = Load();
        User existingUser = findUserById(editUser.getId());
        existingUser.setUsername(editUser.getUsername());
        existingUser.setPassword(editUser.getPassword());
        existingUser.setEmail(editUser.getEmail());
        existingUser.setSurname(editUser.getSurname());
        existingUser.setName(editUser.getName());
        existingUser.setAddress(editUser.getAddress());
        existingUser.setPhone(editUser.getPhone());
        existingUser.setBirthDate(editUser.getBirthDate());
        users.put(editUser.getId(), existingUser);
        Save(users);
    }

    @Override
    public void deleteUser(int userId) {
        Map<Integer, User> users = Load();
        users.remove(userId);
        Save(users);
    }

    public int generateNextId() {
        Map<Integer, User> users = Load();
        int nextId = 1;
        for (int id : users.keySet()) {
            if(nextId<id)
                nextId=id;
        }
        return ++nextId;
    };
};