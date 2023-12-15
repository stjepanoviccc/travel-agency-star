package com.sr182022.travelagencystar.service.impl;

import com.sr182022.travelagencystar.DAO.IUserDao;
import com.sr182022.travelagencystar.model.Role;
import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private final IUserDao databaseUserDAO;

    @Autowired
    public UserService(IUserDao databaseUserDAO) {
        this.databaseUserDAO = databaseUserDAO;
    }

    @Override
    public List<User> findAll() {
        return databaseUserDAO.findAll();
    }

    @Override
    public List<User> findAll(Role role) { return databaseUserDAO.findAll(role); }

    @Override
    public User findOne(int userId) {
        return databaseUserDAO.findOne(userId);
    }

    @Override
    public User findOne(String username) { return databaseUserDAO.findOne(username); }

    @Override
    public void save(User newUser) {
        databaseUserDAO.save(newUser);
    }

    @Override
    public void update(User editUser) {
        databaseUserDAO.update(editUser);
    }

    @Override
    public void delete(int userId) {
        databaseUserDAO.delete(userId);
    }
    @Override
    public void delete(int userId, boolean blocked) {
        databaseUserDAO.delete(userId, blocked);
    }

}
