package com.sr182022.travelagencystar.DAO.UserDAO;

import com.sr182022.travelagencystar.model.User;
import org.springframework.core.io.ResourceLoader;

import java.util.List;

public interface IUserDao {
    List<User> Load();
    List<User> findAllUsers();
    User findUserById(int userId);
    void addNewUser(User newUser);
    void editUser(User editUser);
    void deleteUser(int userId);
    int generateNextId();

}