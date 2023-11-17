package com.sr182022.travelagencystar.DAO.UserDAO;

import com.sr182022.travelagencystar.model.User;
import org.springframework.core.io.ResourceLoader;

import java.util.List;

public interface IUserDao {
    List<User> Load();

    // this will be only while reading from files;
    // List<User> Load(ResourceLoader resourceLoader);

    void Save(List<User> usersList);
}