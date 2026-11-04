package main;

import main.frames.AdminDashboardFrame;
import main.frames.GuestDashboardFrame;
import main.frames.MainFrame;
import main.managers.K2635685_AdminManager;
import main.managers.K2635685_BookingManager;
import main.managers.K2635685_RoomManager;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author N I L S H A D
 */
public class K2635685_SmartStayHotelBookingSystem {
    private static K2635685_RoomManager roomManager;
    private static K2635685_AdminManager adminManager;
    private static K2635685_BookingManager bookingManager;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        roomManager = new K2635685_RoomManager();
        adminManager = new K2635685_AdminManager();
        bookingManager = new K2635685_BookingManager();
        
        java.awt.EventQueue.invokeLater(() -> {
        new MainFrame().setVisible(true);
    });
    }
    
}
