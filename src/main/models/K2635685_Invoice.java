/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models;

import java.util.Date;
import main.managers.K2635685_BookingManager;

/**
 *
 * @author N I L S H A D
 */
public class K2635685_Invoice {

    private final int invoiceID;
    private final Date invoiceDate;
    private final double totalAmount;
    private final double subTotalAmount;
    private boolean isPaid;
    private Date paidDate;
    private final double taxRate;
    private final double taxAmount;
    private final double discountRate;
    private final double discountAmount;

    public K2635685_Invoice(Date invoiceDate, double advncAmount, double dailyRoomRate, long noOfNights, double discountRate, double taxRate, int noOfGuest, int maxCapecity, double extraGuestCharge) {
        
        this.invoiceID = K2635685_BookingManager.generateBookingId();
        this.invoiceDate = invoiceDate;
        this.subTotalAmount = calculateSubTotal(dailyRoomRate, noOfNights, noOfGuest, maxCapecity, extraGuestCharge);
        this.discountRate = discountRate;
        this.discountAmount = calculateDiscount(this.subTotalAmount, discountRate, noOfNights);
        this.taxRate = taxRate;
        this.taxAmount = calculateTax(this.subTotalAmount, taxRate, this.discountAmount);
        this.totalAmount = calculateTotal(advncAmount, this.taxAmount, this.discountAmount, dailyRoomRate, noOfNights, noOfGuest, maxCapecity, extraGuestCharge);

    }
    
    public K2635685_Invoice(int invoiceID, Date invoiceDate, double advncAmount, double dailyRoomRate, long noOfNights, double discountRate, double taxRate, int noOfGuest, int maxCapecity, double extraGuestCharge) {
        
        this.invoiceID = invoiceID;
        this.invoiceDate = invoiceDate;
        this.subTotalAmount = calculateSubTotal(dailyRoomRate, noOfNights, noOfGuest, maxCapecity, extraGuestCharge);
        this.discountRate = discountRate;
        this.discountAmount = calculateDiscount(this.subTotalAmount, discountRate, noOfNights);
        this.taxRate = taxRate;
        this.taxAmount = calculateTax(this.subTotalAmount, taxRate, this.discountAmount);
        this.totalAmount = calculateTotal(advncAmount, this.taxAmount, this.discountAmount, dailyRoomRate, noOfNights, noOfGuest, maxCapecity, extraGuestCharge);

    }



    public String getInvoiceIDString() {
        return "INV - " + String.format("%05d", invoiceID);
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public double getSubTotalAmount() {
        return subTotalAmount;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public boolean isIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    
    //Calculate Tax
    private double calculateTax(double subtotalAmount, double taxRate, double discountAmount){
        return (subtotalAmount - discountAmount) * (taxRate / 100);
    }
    
    //Calculate Discount
    private double calculateDiscount(double subtotalAmount, double discountRate, long noOfNights){
        if (noOfNights < 5) discountRate = 0.0;
        return subtotalAmount * (discountRate / 100);
    }
    
    //Calculate Subtotal
    private double calculateSubTotal(double dailyRoomRate, long noOfNights, int noOfGuest, int maxCapecity, double extraGuestCharge) {
       
        double subtotAmount;
        int extGuestCount = (noOfGuest > maxCapecity) ? (noOfGuest - maxCapecity) : 0;
        double extAmount = extGuestCount * extraGuestCharge * noOfNights;
        
        subtotAmount = dailyRoomRate * noOfNights;
        subtotAmount += extAmount;
        
        return subtotAmount;
    }


    //Calculate Total Amount
    private double calculateTotal(double advncAmount, double taxAmount, double discountAmount, double dailyRoomRate, long noOfNights, int noOfGuest, int maxCapecity, double extraGuestCharge) {
       
        double totAmount;
      
        int extGuestCount = (noOfGuest > maxCapecity) ? (noOfGuest - maxCapecity) : 0;
        double extAmount = extGuestCount * extraGuestCharge * noOfNights;
        
        totAmount = dailyRoomRate * noOfNights;
        totAmount += extAmount;
        
        totAmount -= discountAmount;

        totAmount += taxAmount;

        totAmount -= advncAmount;
        
        return totAmount;
    }

}
