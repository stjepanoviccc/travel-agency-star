package com.sr182022.travelagencystar.service.impl;

import com.sr182022.travelagencystar.DAO.impl.DatabaseUserDAO;
import com.sr182022.travelagencystar.model.Role;
import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private final DatabaseUserDAO userDAO;

    @Autowired
    public UserService(DatabaseUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public List<User> findAll(Role role) { return userDAO.findAll(role); }

    @Override
    public User findOne(int userId) {
        return userDAO.findOne(userId);
    }

    @Override
    public User findOne(String username) { return userDAO.findOne(username); }

    @Override
    public void save(User newUser) {
        userDAO.save(newUser);
    }

    @Override
    public void update(User editUser) {
        userDAO.update(editUser);
    }

    @Override
    public void delete(int userId) {
        userDAO.delete(userId);
    }

}
