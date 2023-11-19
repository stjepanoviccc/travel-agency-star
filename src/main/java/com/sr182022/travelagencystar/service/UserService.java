package com.sr182022.travelagencystar.service;

import com.sr182022.travelagencystar.DAO.UserDAO.UserDAO;
import com.sr182022.travelagencystar.model.Role;
import com.sr182022.travelagencystar.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserDAO userDAO;
    private final List<User> usersList;

    public UserService(UserDAO userDAO, List<User> usersList) {
        this.userDAO = userDAO;
        this.usersList = usersList;
        this.usersList.addAll(userDAO.Load());
    }

    public List<User> getAllUsers() {
        return usersList;
    }

    public void addNewUser(User newUser) {
        newUser.setId(this.getNextId());
        newUser.setRegisteredDate(LocalDateTime.now());
        newUser.setRole(Role.Passenger);
        usersList.add(newUser);
    }

    public void editUser(User editUser) {
        User existingUser = usersList.get(editUser.getId());
        existingUser.setUsername(editUser.getUsername());
        existingUser.setPassword(editUser.getPassword());
        existingUser.setEmail(editUser.getEmail());
        existingUser.setSurname(editUser.getSurname());
        existingUser.setName(editUser.getName());
        existingUser.setAddress(editUser.getAddress());
        existingUser.setPhone(editUser.getPhone());
        existingUser.setBirthDate(editUser.getBirthDate());
    }

    public void deleteUser(int userId) {
        usersList.remove(getUserById(userId));
    }

    public int getNextId() {
        return usersList.stream().mapToInt(User::getId).max().orElse(0)+1;
    }

    public User getUserById(int userId) {
        Optional<User> userOptional = usersList.stream().filter(u -> u.getId() == userId).findFirst();
        return userOptional.orElse(null);
    }
}
