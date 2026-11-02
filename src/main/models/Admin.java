/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models;

/**
 *
 * @author N I L S H A D
 */
public class Admin {
    private String password;
    private String contactNumber;
    private String fullName;
    private String emailAddress;

    public Admin() {}
    
    

    public Admin(String password, String contactNumber, String fullName, String emailAddress) {
        this.password = password;
        this.contactNumber = contactNumber;
        this.fullName = fullName;
        this.emailAddress = emailAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

}
