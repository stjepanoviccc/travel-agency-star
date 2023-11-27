package com.sr182022.travelagencystar.service.UserService;

import com.sr182022.travelagencystar.DAO.UserDAO.UserDAO;
import com.sr182022.travelagencystar.model.Role;
import com.sr182022.travelagencystar.model.User;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserDAO userDAO;
    ServletContext servletContext;
    public static final String USERS_LIST_KEY = "usersList";

    @Autowired
    public UserService(UserDAO userDAO, ServletContext servletContext) {
        this.userDAO = userDAO;
        this.servletContext = servletContext;
    }

    @PostConstruct
    public void init() {
        List<User> usersList = (List<User>) servletContext.getAttribute(USERS_LIST_KEY);
        usersList.addAll(userDAO.Load());
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
    }

}
