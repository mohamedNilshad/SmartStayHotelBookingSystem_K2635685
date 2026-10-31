/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main.components;

import java.util.Date;
import main.functions.HelperFunctions;
import main.models.Guest;
import main.models.Invoice;
import main.models.Room;

/**
 *
 * @author N I L S H A D
 */
public class InvoiceComponent extends javax.swing.JPanel {

    /**
     * Creates new form InvoicePanel
     * @param invoice
     * @param room
     * @param checkInDate
     * @param checkOutDate
     * @param noOfGuest
     * @param guest
     */
    public InvoiceComponent(Invoice invoice, Room room, Date checkInDate, Date checkOutDate, int noOfGuest, Guest guest) {
        initComponents();
        
        String invoiceId = invoice.getInvoiceIDString();
        String invoiceDate = HelperFunctions.formatDate(invoice.getInvoiceDate());
        
        String roomId = room.getRoomIDString();
        String packageName = room.getRoomPackage().getPackageName();
        String bedType = room.getBedType();

        String guestName = guest.getFullName();
        String guestEmail = guest.getEmailAddress();
        String guestPhone = guest.getContactNumber();

        String checkIn = HelperFunctions.formatDate(checkInDate);
        String checkOut = HelperFunctions.formatDate(checkOutDate);

        long noOfNights = HelperFunctions.getNoOfNights(checkInDate, checkOutDate);
        int extGuestCount = (noOfGuest > room.getCapacity()) ? (noOfGuest - room.getCapacity()) : 0;

        double dailyRoomRate = room.getRoomPackage().getDailyRoomRate();
        double extraGuestCharge = room.getRoomPackage().getExtraGuestCharge();
        double extAmount = extGuestCount * extraGuestCharge * noOfNights;
        
        double totalRoomCharge = dailyRoomRate * noOfNights;
        
        invoiceNoLbl.setText("No: " + invoiceId);
        invoiceDateLbl.setText(invoiceDate);
        roomNoLbl.setText("Room "+roomId+"   •  "+ packageName);
        
        roomInfoLbl.setText("🛏 " + bedType + "  |  👥 x " + noOfGuest);
        dateInfoLbl.setText("📅 " + checkIn + "  →  " + checkOut);
        nightCountLbl.setText("🌙 " + noOfNights + (noOfNights == 1 ? " night" : " nights"));
        
        guestNameLbl.setText(guestName);
        guestEmailLbl.setText("✉ " + guestEmail);
        guestPhoneLbl.setText("📞 " + guestPhone);
        
        roomChargeLbl.setText(HelperFunctions.formatMoney(dailyRoomRate)+"  ×  "+ noOfNights +(noOfNights == 1 ? " night" : " nights"));
        totRoomChargeLbl.setText(HelperFunctions.formatMoney(totalRoomCharge));
        
        //for extra guests
        if(extAmount == 0){
            extraGuestChargeLbl.setVisible(false);
            totExtraGuestChargeLbl.setVisible(false);
            extraGuestChargeCap.setVisible(false);
        }else{
            extraGuestChargeLbl.setVisible(true);
            totExtraGuestChargeLbl.setVisible(true);
            extraGuestChargeCap.setVisible(true);
            
            extraGuestChargeLbl.setText(HelperFunctions.formatMoney(extraGuestCharge) + "  ×  "+extGuestCount+" guest(s)  ×  "+noOfNights+" Nights");
            totExtraGuestChargeLbl.setText(HelperFunctions.formatMoney(extAmount));
        }
        
        subtotalLbl.setText(HelperFunctions.formatMoney(invoice.getSubTotalAmount()));
        
        //for discount
        if(invoice.getDiscountAmount()== 0){
            discountCap.setVisible(false);
            discountRateLbl.setVisible(false);
            discountAmountLbl.setVisible(false);
        }else{
            discountCap.setVisible(true);
            discountRateLbl.setVisible(true);
            discountAmountLbl.setVisible(true);
            
            discountRateLbl.setText("("+invoice.getDiscountRate()+"%)");
            discountAmountLbl.setText(HelperFunctions.formatMoney(invoice.getDiscountAmount()));
        }
        
        
        taxRateLbl.setText("("+invoice.getTaxRate()+"%)");
        totTaxRateLbl.setText(HelperFunctions.formatMoney(invoice.getTaxAmount()));
        
        totalAmountLbl.setText(HelperFunctions.formatMoney((invoice.getTotalAmount() + invoice.getBookingAdvance())));
        
        advanceAmountLbl.setText("-"+HelperFunctions.formatMoney(invoice.getBookingAdvance()));
        
        
        if(invoice.getTotalAmount() <= 0){
            double balance = Math.abs(invoice.getTotalAmount());
            footerTextLbl.setVisible(true);
            footerTextLbl.setText("Balance Amount Due to Guest: " + HelperFunctions.formatMoney(balance));
            payableTotalAmountLbl.setText(HelperFunctions.formatMoney(0));
            
        }else{
            footerTextLbl.setVisible(false);
            payableTotalAmountLbl.setText(HelperFunctions.formatMoney(invoice.getTotalAmount()));
        }
        
        
        
        
        
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel17 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        invoiceNoLbl = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        invoiceDateLbl = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        roomNoLbl = new javax.swing.JLabel();
        roomInfoLbl = new javax.swing.JLabel();
        dateInfoLbl = new javax.swing.JLabel();
        nightCountLbl = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        guestNameLbl = new javax.swing.JLabel();
        guestEmailLbl = new javax.swing.JLabel();
        guestPhoneLbl = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        roomChargeLbl = new javax.swing.JLabel();
        totRoomChargeLbl = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        totExtraGuestChargeLbl = new javax.swing.JLabel();
        extraGuestChargeCap = new javax.swing.JLabel();
        discountRateLbl = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        discountCap = new javax.swing.JLabel();
        extraGuestChargeLbl = new javax.swing.JLabel();
        subtotalLbl = new javax.swing.JLabel();
        discountAmountLbl = new javax.swing.JLabel();
        taxRateLbl = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        totTaxRateLbl = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel30 = new javax.swing.JLabel();
        totalAmountLbl = new javax.swing.JLabel();
        advanceAmountLbl = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        payableTotalAmountLbl = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        footerTextLbl = new javax.swing.JLabel();

        jLabel17.setForeground(new java.awt.Color(95, 110, 130));
        jLabel17.setText("Rs. 6000.00   ×  10 Nights");

        jLabel36.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel36.setText("Discount");

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(37, 99, 235));
        jLabel1.setText("INVOICE");

        invoiceNoLbl.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        invoiceNoLbl.setForeground(new java.awt.Color(95, 110, 130));
        invoiceNoLbl.setText("No:  INV-0000");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel3.setForeground(java.awt.Color.gray);
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Date issued");

        invoiceDateLbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        invoiceDateLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        invoiceDateLbl.setText("29 Oct 2026");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel5.setForeground(java.awt.Color.gray);
        jLabel5.setText("STAY DETAILS");

        roomNoLbl.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        roomNoLbl.setText("Room R100   •  Standard");

        roomInfoLbl.setFont(new java.awt.Font("Segoe UI Emoji", 0, 13)); // NOI18N
        roomInfoLbl.setForeground(new java.awt.Color(95, 110, 130));
        roomInfoLbl.setText("🛏  Single |  👥 x 5");

        dateInfoLbl.setFont(new java.awt.Font("Segoe UI Emoji", 0, 13)); // NOI18N
        dateInfoLbl.setForeground(new java.awt.Color(95, 110, 130));
        dateInfoLbl.setText("📅  02 Nov 2026   →  12 Nov 2026");

        nightCountLbl.setFont(new java.awt.Font("Segoe UI Emoji", 0, 13)); // NOI18N
        nightCountLbl.setForeground(new java.awt.Color(95, 110, 130));
        nightCountLbl.setText("🌙  10 Nights");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel10.setForeground(java.awt.Color.gray);
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("BILLED TO");

        guestNameLbl.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        guestNameLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        guestNameLbl.setText("Nilshad");

        guestEmailLbl.setFont(new java.awt.Font("Segoe UI Emoji", 0, 13)); // NOI18N
        guestEmailLbl.setForeground(new java.awt.Color(95, 110, 130));
        guestEmailLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        guestEmailLbl.setText("✉  nilshad@gmail.com");

        guestPhoneLbl.setFont(new java.awt.Font("Segoe UI Emoji", 0, 13)); // NOI18N
        guestPhoneLbl.setForeground(new java.awt.Color(95, 110, 130));
        guestPhoneLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        guestPhoneLbl.setText("📞  0777192608");

        jLabel14.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel14.setForeground(java.awt.Color.gray);
        jLabel14.setText("CHARGES");

        jLabel15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel15.setText("Room charge");

        roomChargeLbl.setForeground(new java.awt.Color(95, 110, 130));
        roomChargeLbl.setText("Rs. 6000.00   ×  10 Nights");

        totRoomChargeLbl.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        totRoomChargeLbl.setForeground(java.awt.Color.gray);
        totRoomChargeLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totRoomChargeLbl.setText("Rs. 60000.00");

        totExtraGuestChargeLbl.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        totExtraGuestChargeLbl.setForeground(java.awt.Color.gray);
        totExtraGuestChargeLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totExtraGuestChargeLbl.setText("Rs. 2000.00");

        extraGuestChargeCap.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        extraGuestChargeCap.setText("Extra guest charge");

        discountRateLbl.setForeground(new java.awt.Color(95, 110, 130));
        discountRateLbl.setText("(12.0 %)");

        jLabel22.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel22.setText("Subtotal");

        discountCap.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        discountCap.setText("Discount");

        extraGuestChargeLbl.setForeground(new java.awt.Color(95, 110, 130));
        extraGuestChargeLbl.setText("Rs. 1000.00   ×   2 guest(s)  ×  10 Nights");

        subtotalLbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        subtotalLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        subtotalLbl.setText("Rs. 62000.00");

        discountAmountLbl.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        discountAmountLbl.setForeground(java.awt.Color.gray);
        discountAmountLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        discountAmountLbl.setText("Rs. 1400.00");

        taxRateLbl.setForeground(new java.awt.Color(95, 110, 130));
        taxRateLbl.setText("(10.0 %)");

        jLabel28.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel28.setText("Tax");

        totTaxRateLbl.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        totTaxRateLbl.setForeground(java.awt.Color.gray);
        totTaxRateLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totTaxRateLbl.setText("Rs. 6200.00");

        jLabel30.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel30.setText("Total before advance");

        totalAmountLbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        totalAmountLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalAmountLbl.setText("Rs. 62000.00");

        advanceAmountLbl.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        advanceAmountLbl.setForeground(java.awt.Color.red);
        advanceAmountLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        advanceAmountLbl.setText("- Rs. 10000.00");

        jPanel1.setBackground(new java.awt.Color(239, 246, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 16, 12, 16));

        jLabel34.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel34.setText("Total payable");

        payableTotalAmountLbl.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        payableTotalAmountLbl.setForeground(new java.awt.Color(37, 99, 235));
        payableTotalAmountLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        payableTotalAmountLbl.setText("Rs. 52000.00");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(payableTotalAmountLbl))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(payableTotalAmountLbl)
                    .addComponent(jLabel34))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel37.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel37.setText("Booking advance");

        jLabel32.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("SmartStay");

        jLabel38.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("Hotel Booking System");

        footerTextLbl.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        footerTextLbl.setForeground(new java.awt.Color(95, 110, 130));
        footerTextLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        footerTextLbl.setText("Balance Amount Due to Guest:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(invoiceNoLbl))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel38)
                                .addGap(45, 45, 45))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel32)
                                .addGap(67, 67, 67)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(invoiceDateLbl, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(roomNoLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(guestNameLbl))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(roomInfoLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(guestEmailLbl))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dateInfoLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(guestPhoneLbl))
                    .addComponent(jSeparator2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(roomChargeLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(totRoomChargeLbl))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(extraGuestChargeLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(totExtraGuestChargeLbl))
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(subtotalLbl))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(discountRateLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(discountAmountLbl))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(taxRateLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(totTaxRateLbl))
                    .addComponent(jSeparator4)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(totalAmountLbl))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(advanceAmountLbl))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nightCountLbl)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(extraGuestChargeCap)
                            .addComponent(discountCap)
                            .addComponent(jLabel28)
                            .addComponent(footerTextLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 2, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, 0)
                        .addComponent(invoiceNoLbl))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel32))
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(invoiceDateLbl)
                            .addComponent(jLabel38))))
                .addGap(10, 10, 10)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel10))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roomNoLbl)
                    .addComponent(guestNameLbl))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roomInfoLbl)
                    .addComponent(guestEmailLbl))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateInfoLbl)
                    .addComponent(guestPhoneLbl))
                .addGap(2, 2, 2)
                .addComponent(nightCountLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel14)
                .addGap(10, 10, 10)
                .addComponent(jLabel15)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roomChargeLbl)
                    .addComponent(totRoomChargeLbl))
                .addGap(15, 15, 15)
                .addComponent(extraGuestChargeCap)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totExtraGuestChargeLbl)
                    .addComponent(extraGuestChargeLbl))
                .addGap(10, 10, 10)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(subtotalLbl))
                .addGap(6, 6, 6)
                .addComponent(discountCap)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(discountRateLbl)
                    .addComponent(discountAmountLbl))
                .addGap(15, 15, 15)
                .addComponent(jLabel28)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(taxRateLbl)
                    .addComponent(totTaxRateLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(totalAmountLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(advanceAmountLbl)
                    .addComponent(jLabel37))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(footerTextLbl)
                .addContainerGap(11, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel advanceAmountLbl;
    private javax.swing.JLabel dateInfoLbl;
    private javax.swing.JLabel discountAmountLbl;
    private javax.swing.JLabel discountCap;
    private javax.swing.JLabel discountRateLbl;
    private javax.swing.JLabel extraGuestChargeCap;
    private javax.swing.JLabel extraGuestChargeLbl;
    private javax.swing.JLabel footerTextLbl;
    private javax.swing.JLabel guestEmailLbl;
    private javax.swing.JLabel guestNameLbl;
    private javax.swing.JLabel guestPhoneLbl;
    private javax.swing.JLabel invoiceDateLbl;
    private javax.swing.JLabel invoiceNoLbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel nightCountLbl;
    private javax.swing.JLabel payableTotalAmountLbl;
    private javax.swing.JLabel roomChargeLbl;
    private javax.swing.JLabel roomInfoLbl;
    private javax.swing.JLabel roomNoLbl;
    private javax.swing.JLabel subtotalLbl;
    private javax.swing.JLabel taxRateLbl;
    private javax.swing.JLabel totExtraGuestChargeLbl;
    private javax.swing.JLabel totRoomChargeLbl;
    private javax.swing.JLabel totTaxRateLbl;
    private javax.swing.JLabel totalAmountLbl;
    // End of variables declaration//GEN-END:variables
}
