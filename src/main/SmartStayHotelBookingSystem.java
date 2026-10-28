package main;

import main.frames.DashboardFrame;
import main.frames.MainFrame;
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
        RoomManager roomManager = new RoomManager();
        java.awt.EventQueue.invokeLater(() -> {
        new MainFrame().setVisible(true);
//        new DashboardFrame().setVisible(true);
    });
    }
    
}
