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
public class Invoice {
    private int invoiceID;
    private Date invoiceDate;
    private double totalAmount;
    private double subTotalAmount;
    private double bookingAdvance;
    private boolean isPaid;
    private Date paidDate;
    private double taxRate;
    private double taxAmount;
    private double discountRate;
    private double discountAmount;

    public Invoice(int invoiceID, Date invoiceDate, double totalAmount, double subTotalAmount, double bookingAdvance, boolean isPaid, Date paidDate, double taxRate, double taxAmount, double discountRate, double discountAmount) {
        this.invoiceID = invoiceID;
        this.invoiceDate = invoiceDate;
        this.totalAmount = totalAmount;
        this.subTotalAmount = subTotalAmount;
        this.bookingAdvance = bookingAdvance;
        this.isPaid = isPaid;
        this.paidDate = paidDate;
        this.taxRate = taxRate;
        this.taxAmount = taxAmount;
        this.discountRate = discountRate;
        this.discountAmount = discountAmount;
    }
    
    public Invoice(){}
    
    public String getInvoiceIDString() { 
        return "INV - "+String.format("%05d", invoiceID);
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public double getBookingAdvance() {
        return bookingAdvance;
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

    
}
