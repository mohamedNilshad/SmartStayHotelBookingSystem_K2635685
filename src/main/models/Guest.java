/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models;

/**
 *
 * @author N I L S H A D
 */
public class Guest {
    private String nicPassportNumber;
    private String contactNumber;
    private String fullName;
    private String emailAddress;

    public Guest() {}
    
    

    public Guest(String nicPassportNumber, String contactNumber, String fullName, String emailAddress) {
        this.nicPassportNumber = nicPassportNumber;
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

    public String getNicPassportNumber() {
        return nicPassportNumber;
    }

    
    
}
