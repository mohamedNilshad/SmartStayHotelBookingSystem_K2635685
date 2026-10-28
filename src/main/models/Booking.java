/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models;

import java.util.Date;
import main.managers.BookingManager;

/**
 *
 * @author N I L S H A D
 */
public class Booking {
    private int bookingID;
    private Room room;
    private Guest guest;
    private Date checkInDate;
    private Date checkOutDate;
    private Date bookingDate;
    private int numberOfGeust;
    private double bookingAdvance;
    private boolean isPaid;
    private double totalAmount;
    
    private String status;

    public Booking(Room room, Guest guest, Date checkInDate, Date checkOutDate, Date bookingDate, int numberOfGeust, double bookingAdvance, boolean isPaid, double totalAmount, String status) {
        this.bookingID = BookingManager.generateBookingId();
        this.room = room;
        this.guest = guest;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingDate = bookingDate;
        this.numberOfGeust = numberOfGeust;
        this.bookingAdvance = bookingAdvance;
        this.isPaid = isPaid;
        this.totalAmount = totalAmount;
        this.status = status;
    }
    
    public Booking(int bookingID, Room room, Guest guest, Date checkInDate, Date checkOutDate, Date bookingDate, int numberOfGeust, double bookingAdvance, boolean isPaid, double totalAmount, String status) {
        this.bookingID = bookingID;
        this.room = room;
        this.guest = guest;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingDate = bookingDate;
        this.numberOfGeust = numberOfGeust;
        this.bookingAdvance = bookingAdvance;
        this.isPaid = isPaid;
        this.totalAmount = totalAmount;
        this.status = status;
    }
    
     public Booking(){}

    public int getBookingID() {
        return bookingID;
    }

    public Room getRoom() {
        return room;
    }
    
    public Guest getGuest() {
        return guest;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public double getBookingAdvance() {
        return bookingAdvance;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public int getNumberOfGeust() {
        return numberOfGeust;
    }

    public boolean getPaidStatus() {
        return isPaid;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }
    
    
    public void setStatus(String status) { this.status = status; }
}
