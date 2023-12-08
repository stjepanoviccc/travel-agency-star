package com.sr182022.travelagencystar.DAO;

import com.sr182022.travelagencystar.model.Role;
import com.sr182022.travelagencystar.model.User;

import java.util.List;

public interface IUserDao {
    List<User> findAll();
    List<User> findAll(Role role);
    User findOne(int userId);
    User findOne(String username);
    void save(User newUser);
    void update(User editUser);
    void delete(int userId);
}