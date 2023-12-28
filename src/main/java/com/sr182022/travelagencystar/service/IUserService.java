package com.sr182022.travelagencystar.service;

import com.sr182022.travelagencystar.model.Role;
import com.sr182022.travelagencystar.model.User;

import java.util.List;

public interface IUserService {
    List<User> findAll();
    List<User> findAll(String sortOrder);
    // List<User> findAll(Role role);
    User findOne(int userId);
    User findOne(String username);
    List<User> findByUsername(String username, String sortOrder);
    List<User> findByRole(String role, String sortOrder);
    List<User> findByUsernameAndRole(String username, String role, String sortOrder);
    List<String> findAllRoles();
    void save(User newUser);
    void update(User editUser);
    void delete(int userId);
    void delete(int userId, boolean blocked);
    boolean tryValidate(User user, boolean editing);
    List<User> filterUsersValidation(String username, String role, boolean clearFilter, String sortOrder);
}
