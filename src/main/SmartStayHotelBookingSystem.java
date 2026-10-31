package main;

import main.frames.AdminDashboardFrame;
import main.frames.GuestDashboardFrame;
import main.frames.MainFrame;
import main.managers.AdminManager;
import main.managers.BookingManager;
import main.managers.RoomManager;

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
        RoomManager _ = new RoomManager();
        AdminManager _ = new AdminManager();
        BookingManager _ = new BookingManager();
        
        java.awt.EventQueue.invokeLater(() -> {
//        new MainFrame().setVisible(true);
//        new AdminDashboardFrame().setVisible(true);
        new GuestDashboardFrame().setVisible(true);
    });
    }
    
}
