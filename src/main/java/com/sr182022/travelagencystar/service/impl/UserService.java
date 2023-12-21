package com.sr182022.travelagencystar.service.impl;

import com.sr182022.travelagencystar.DAO.IUserDao;
import com.sr182022.travelagencystar.model.Role;
import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.service.IUserService;
import com.sr182022.travelagencystar.utils.DateTimeUtil;
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

    @Override
    public boolean tryValidate(User user, boolean editing) {
        if(user.getUsername().length() <= 2 || user.getUsername().length() >= 20) {
            return false;
        }
        if(user.getPassword().length() <= 2 || user.getPassword().length() >= 20) {
            return false;
        }
        if (!user.getEmail().matches("^.+@.+\\..+$")) {
            return false;
        }
        if(user.getSurname().length() <= 2 || user.getPassword().length() >= 20 || !Character.isUpperCase(user.getSurname().charAt(0))) {
            return false;
        }
        if(user.getName().length() <= 2 || user.getPassword().length() >= 20 || !Character.isUpperCase(user.getSurname().charAt(0))) {
            return false;
        }
        if(user.getAddress().length() <= 2 || user.getAddress().length() >= 100) {
            return false;
        }
        String phoneStr = String.valueOf(user.getPhone());
        if (phoneStr.length() < 8 || phoneStr.length() > 10) {
            return false;
        }
        if(DateTimeUtil.convertLocalDateToInt(user.getBirthDate()) < 12) {
            return false;
        }

        // now checking does username and email already exist if not editing
        if(!editing) {
            if(databaseUserDAO.doesUsernameExist(user.getUsername()) || databaseUserDAO.doesEmailExist(user.getEmail())) {
                return false;
            }
        }

        // if editing i will only check does  username and email exist IF:
        if(editing) {
            User userBeforeEdit = databaseUserDAO.findOne(user.getId());

            // if my username before edit isnt same as username now(if its same i wont search does it exist bcuz it will exist, and same for email.
            if(!userBeforeEdit.getUsername().equals(user.getUsername())) {
                if(databaseUserDAO.doesUsernameExist(user.getUsername())) {
                    return false;
                }
            }
            if(!userBeforeEdit.getEmail().equals(user.getEmail())) {
                if(databaseUserDAO.doesEmailExist(user.getEmail())) {
                    return false;
                }
            }
        }


        return true;
    }

}
