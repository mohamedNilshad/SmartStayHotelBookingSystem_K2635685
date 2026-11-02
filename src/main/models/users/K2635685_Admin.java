/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models.users;

/**
 *
 * @author N I L S H A D
 */
public class K2635685_Admin extends K2635685_User  {
    private String password;

    public K2635685_Admin() {}
    
    public K2635685_Admin(String password, String contactNumber, String fullName, String emailAddress) {
        this.password = password;
        this.contactNumber = contactNumber;
        this.fullName = fullName;
        this.emailAddress = emailAddress;
    }

    @Override
    public String getContactNumber() {
        return contactNumber;
    }

    @Override
    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

}
