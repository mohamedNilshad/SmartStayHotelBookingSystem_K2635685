package main.managers;

import java.util.ArrayList;
import java.util.List;
import main.models.users.Admin;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author N I L S H A D
 */
public class AdminManager {

    private static final List<Admin> adminList = new ArrayList<>();
    private static Admin currentAdmin;
    
    
    public AdminManager(){
        adminList.add(new Admin("Test@123", "0771235265", "Nilshad", "nilshad@gmail.com"));
    }

    public static boolean verifyLogin(String email, String password) {
        for (Admin admin : adminList) {
            if (admin.getEmailAddress().equalsIgnoreCase(email) && admin.getPassword().equalsIgnoreCase(password)) {
                setCurrentAdmin(admin);
                return true;
            }
        }
        return false;
    }

   
    public static void setCurrentAdmin(Admin admin) {
        currentAdmin = admin;
    }

    public static Admin getCurrentAdmin() {
        return currentAdmin;
    }

    public static void clearSession() {
        currentAdmin = null;
    }
}
