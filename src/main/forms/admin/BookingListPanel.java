/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main.forms.admin;

import java.awt.*;
import javax.swing.*;
import java.util.List;
import main.components.BookingRowComponent;
import main.components.CompleteBookingComponent;
import main.components.InvoiceComponent;
import main.functions.HelperFunctions;
import main.managers.BookingManager;
import main.models.Booking;

/**
 *
 * @author N I L S H A D
 */
public class BookingListPanel extends javax.swing.JPanel {

    private final JPanel listPanel;
    private final JScrollPane scrollPane;

    /**
     * Creates new form RoomListPanel
     *
     * @param bookingList
     */
    public BookingListPanel(List<Booking> bookingList) {
        initComponents();
        
        setLayout(new BorderLayout());
        listPanel = new JPanel();

        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        scrollPane = new JScrollPane(listPanel);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, java.awt.BorderLayout.CENTER);

        updateBookingList(bookingList);
    }

    public void updateBookingList(List<Booking> bookingList) {

        listPanel.removeAll();
        if (bookingList.isEmpty()) {
            JPanel row = BookingRowComponent.createBookingRow(
                    new Booking(), true, true, null, null, null, null
            );
            listPanel.add(row);
        } else {
            for (Booking bookingItem : bookingList) {
                JPanel row = BookingRowComponent.createBookingRow(
                        bookingItem, false, true,
                        // Edit
                        null,
                        // Cancel
                        booking -> {

                            int result = HelperFunctions.showConfirmDialog(
                                    "Are you sure you want to cancel this booking?",
                                    "Confirm Booking Cancellation"
                            );

                            if (result == JOptionPane.YES_OPTION) {
                                List<Booking> updatedBookingList = BookingManager.setBookingStatus(1, booking.getBookingID()); // Cancellation

                                updateBookingList(updatedBookingList);

                                HelperFunctions.showPopMessage(this, "Booking cancelled successfully!", "Cancellation Successful");
                            }
                        },
                        // Complete
                        booking -> {

                            Double balance = CompleteBookingComponent.showCompleteBookingDialog(this, booking);
                            
                            if (balance != null) { // null means the user closed or cancelled the dialog
                                List<Booking> updatedBookingList = BookingManager.completeBooking(booking.getBookingID(), balance);
                                updateBookingList(updatedBookingList);
                                //show final invoice
                                HelperFunctions.showPopMessage(this, "Booking completed successfully!", "Completion Successful");
                            }
                        },
                        //select
                        booking ->{
                            if (booking.getStatus() != BookingManager.getBookingStatus(1)) {
                                InvoiceComponent invoicePanel = new InvoiceComponent(
                                        booking.getInvoice(),
                                        booking.getRoom(),
                                        booking.getCheckInDate(),
                                        booking.getCheckOutDate(),
                                        booking.getNumberOfGeust(),
                                        booking.getGuest()
                                );

                                JDialog popupDialog = new JDialog(
                                        (JFrame) SwingUtilities.getWindowAncestor(this),
                                        "Invoice Details",
                                        true
                                );

                                popupDialog.getContentPane().add(invoicePanel);

                                popupDialog.pack();
                                popupDialog.setLocationRelativeTo(this);
                                popupDialog.setVisible(true);
                            }
                        }
                );
                listPanel.add(row);
            }
        }

        listPanel.revalidate();
        listPanel.repaint();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
