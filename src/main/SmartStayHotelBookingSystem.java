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
public class SmartStayHotelBookingSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        K2635685_RoomManager _ = new K2635685_RoomManager();
        K2635685_AdminManager _ = new K2635685_AdminManager();
        K2635685_BookingManager _ = new K2635685_BookingManager();
        
        java.awt.EventQueue.invokeLater(() -> {
        new MainFrame().setVisible(true);
//        new AdminDashboardFrame().setVisible(true);
//        new GuestDashboardFrame().setVisible(true);
    });
    }
    
}
