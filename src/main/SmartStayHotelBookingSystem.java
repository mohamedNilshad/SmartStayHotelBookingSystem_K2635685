package main;

import main.Frames.DashboardFrame;
import main.Frames.MainFrame;
import main.models.DataModels;

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
        DataModels dataModel = new DataModels();
        java.awt.EventQueue.invokeLater(() -> {
        //new MainFrame().setVisible(true);
        new DashboardFrame().setVisible(true);
    });
    }
    
}
