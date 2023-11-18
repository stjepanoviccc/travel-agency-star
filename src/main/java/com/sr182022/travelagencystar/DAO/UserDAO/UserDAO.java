package com.sr182022.travelagencystar.DAO.UserDAO;

import com.sr182022.travelagencystar.model.Role;
import com.sr182022.travelagencystar.model.User;
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

@Repository
public class UserDAO implements IUserDao {
    private final ResourceLoader resourceLoader;

    public UserDAO(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public List<User> Load() {
        // code for load
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
        } catch (IOException e) {
            throw new RuntimeException("Error reading file from users.txt", e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Error parsing id from users.txt", e);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Error parsing registered date from users.txt", e);
        } catch (EnumConstantNotPresentException e) {
            throw new RuntimeException("Enum Role didn't parse well", e);
        }
    };

    @Override
    public void Save(List<User> usersList) {

    };
};