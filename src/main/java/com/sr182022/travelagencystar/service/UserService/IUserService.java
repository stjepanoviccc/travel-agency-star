package com.sr182022.travelagencystar.service.UserService;

import com.sr182022.travelagencystar.model.User;

import java.util.List;

public interface IUserService {
    List<User> findAllUsers();
    User findUserById(int userId);
    void addNewUser(User newUser);
    void editUser(User editUser);
    void deleteUser(int userId);
    int generateNextId();
}
