/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models;

import main.models.users.K2635685_Guest;
import java.util.Date;
import main.functions.HelperFunctions;
import main.managers.K2635685_BookingManager;

/**
 *
 * @author N I L S H A D
 */
public class K2635685_Booking {
    private int bookingID;
    private K2635685_Room room;
    private K2635685_Guest guest;
    private Date checkInDate;
    private Date checkOutDate;
    private Date bookingDate;
    private int numberOfGuest;
    private double advncAmount;
    private K2635685_Invoice invoice;
    private String status;

    public K2635685_Booking(K2635685_Room room, K2635685_Guest guest, Date checkInDate, Date checkOutDate, Date bookingDate, int numberOfGeust, String status, double advncAmount) {
        this.bookingID = K2635685_BookingManager.generateBookingId();
        this.room = room;
        this.guest = guest;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingDate = bookingDate;
        this.numberOfGuest = numberOfGeust;
        this.advncAmount = advncAmount;
        this.invoice = new K2635685_Invoice(
                bookingDate, advncAmount, room.getRoomPackage().getDailyRoomRate(), 
                HelperFunctions.getNoOfNights(checkInDate, checkOutDate), 
                room.getDiscountRate(), room.getRoomPackage().getTaxRate(), 
                numberOfGeust, room.getCapacity(), room.getRoomPackage().getExtraGuestCharge()
        );//composition
        this.status = status;
    }
    
    public K2635685_Booking(int bookingID, int invoiceID, Date invoiceDate, K2635685_Room room, K2635685_Guest guest, Date checkInDate, Date checkOutDate, Date bookingDate, int numberOfGeust, String status, double advncAmount) {
        this.bookingID = bookingID;
        this.room = room;
        this.guest = guest;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingDate = bookingDate;
        this.numberOfGuest = numberOfGeust;
        this.advncAmount = advncAmount;
        this.invoice = new K2635685_Invoice(
                invoiceID, invoiceDate, advncAmount, room.getRoomPackage().getDailyRoomRate(), 
                HelperFunctions.getNoOfNights(checkInDate, checkOutDate), 
                room.getDiscountRate(), room.getRoomPackage().getTaxRate(), 
                numberOfGeust, room.getCapacity(), room.getRoomPackage().getExtraGuestCharge()
        );//composition
        this.status = status;
    }
    
    public K2635685_Booking(){}
    
    public String getBookingIDString() { 
        return "BK_"+String.format("%04d", bookingID);
    }

    public int getBookingID() {
        return bookingID;
    }

    public K2635685_Room getRoom() {
        return room;
    }
    
    public K2635685_Guest getGuest() {
        return guest;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public K2635685_Invoice getInvoice() {
        return invoice;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public int getNumberOfGeust() {
        return numberOfGuest;
    }

    public String getStatus() {
        return status;
    }

    public double getAdvncAmount() {
        return advncAmount;
    }
    
    
    public void setStatus(String status) { this.status = status; }

    public void setInvoice(K2635685_Invoice invoice) {
        this.invoice = invoice;
    }
    
    
}
