package com.sr182022.travelagencystar.service.UserService;

import com.sr182022.travelagencystar.DAO.UserDAO.UserDAO;
import com.sr182022.travelagencystar.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> findAllUsers() {
        return userDAO.findAllUsers();
    }

    @Override
    public User findUserById(int userId) {
        return userDAO.findUserById(userId);
    }

    @Override
    public void addNewUser(User newUser) {
        userDAO.addNewUser(newUser);
    }

    @Override
    public void editUser(User editUser) {
        userDAO.editUser(editUser);
    }

    @Override
    public void deleteUser(int userId) {
        userDAO.deleteUser(userId);
    }

    @Override
    public int generateNextId() {
        return userDAO.generateNextId();
    }
}
