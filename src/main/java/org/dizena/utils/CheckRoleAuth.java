package org.dizena.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class CheckRoleAuth {

    public static boolean checkRole(String role) {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            if (subject.hasRole(role)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkAuth(String auth) {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            if (subject.isPermitted(auth)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAdmin() {

        return checkRole("admin") || checkRole("master");
    }

}
