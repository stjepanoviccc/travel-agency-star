package com.sr182022.travelagencystar.DAO;

import com.sr182022.travelagencystar.model.Role;
import com.sr182022.travelagencystar.model.User;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IUserDao {
    List<User> findAll();
    List<User> findAll(String sortOrder);
   // List<User> findAll(Role role);
    User findOne(int userId);
    User findOne(String username);
    List<User> findByUsername(String username, String sortOrder);
    List<User> findByRole(String role, String sortOrder);
    List<User> findByUsernameAndRole(String username, String usernameSort, String role, String roleSort);
    void save(User newUser);
    void update(User editUser);
    // physical delete
    void delete(int userId);
    // block and reactivate
    void delete(int userId, boolean blocked);
    boolean doesEmailExist(String email);
    boolean doesUsernameExist(String username);

}