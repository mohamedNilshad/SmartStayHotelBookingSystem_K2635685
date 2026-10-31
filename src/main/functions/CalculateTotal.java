/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.functions;

import java.util.Date;
import main.managers.GuestManager;
import main.models.Invoice;
import main.models.Room;

/**
 *
 * @author N I L S H A D
 */
public class CalculateTotal {
    
    public static Invoice calculation(Room room, Date checkInDate, Date checkOutDate, String numberOfGuest, String advanceAmount) {
        long noOfNights = HelperFunctions.getNoOfNights(checkInDate, checkOutDate);
        int noOfGuest = Integer.parseInt(numberOfGuest);
        int extGuestCount = (noOfGuest > room.getCapacity()) ?  (noOfGuest - room.getCapacity()) : 0;

        double dailyRoomRate = room.getRoomPackage().getDailyRoomRate();
        double extraGuestCharge = room.getRoomPackage().getExtraGuestCharge();
        double extAmount = extGuestCount * extraGuestCharge * noOfNights;
        double advncAmount = Double.parseDouble(advanceAmount);
        double totalAmount;
        double subtotalAmount;

        double taxRate = room.getRoomPackage().getTaxRate();
        double discountRate = 0.0;
        if (noOfNights >= 5) {
            discountRate = room.getDiscountRate();
        }

        totalAmount = dailyRoomRate * noOfNights;
        totalAmount += extAmount;
        subtotalAmount = totalAmount;

        double discountAmount = totalAmount * (discountRate / 100);
        totalAmount -= discountAmount;

        double taxAmount = totalAmount * (taxRate / 100);
        totalAmount += taxAmount;

        totalAmount -= advncAmount;
        
        return new Invoice(HelperFunctions.getCurrentDate(), totalAmount, subtotalAmount, advncAmount, false, null, taxRate, taxAmount, discountRate, discountAmount);
        
    }
}
