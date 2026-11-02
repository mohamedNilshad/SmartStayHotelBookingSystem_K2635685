/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models.users;

/**
 *
 * @author N I L S H A D
 */
abstract public class User {
    protected String contactNumber;
    protected String fullName;
    protected String emailAddress;
    
    
    abstract public String getContactNumber();

    abstract public String getEmailAddress();

    abstract public String getFullName();
}
