package com.sr182022.travelagencystar.utils;

import com.sr182022.travelagencystar.model.Role;
import com.sr182022.travelagencystar.model.User;
import jakarta.servlet.http.HttpSession;

public class CheckRoleUtil {
    public static boolean RoleAdministrator(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user == null) {
            return false;
        } else {
            if(user.getRole() != Role.Administrator) {
                return false;
            }
        }

        return true;
    }

    public static boolean RoleOrganizer(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user == null) {
            return false;
        } else {
            if(user.getRole() != Role.Organizer) {
                return false;
            }
        }

        return true;
    }

    public static boolean RolePassenger(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user == null) {
            return false;
        } else {
            if(user.getRole() != Role.Passenger) {
                return false;
            }
        }

        return true;
    }

    public static boolean RoleAdministratorOrOrganizer(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user == null) {
            return false;
        } else {
            if((user.getRole() != Role.Administrator) && (user.getRole() != Role.Organizer)) {
                return false;
            }
        }

        return true;
    }

    public static boolean RoleAdministratorOrNull(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user == null) {
            return true;
        } else {
            if(user.getRole() != Role.Administrator) {
                return false;
            }
        }

        return true;
    }

}
