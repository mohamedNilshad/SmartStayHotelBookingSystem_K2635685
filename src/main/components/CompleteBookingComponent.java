/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import main.functions.HelperFunctions;
import main.models.Booking;

/**
 *
 * @author N I L S H A D
 */
public class CompleteBookingComponent {

    public static Double showCompleteBookingDialog(Component c, Booking booking) {

        String bookingId = booking.getBookingIDString();
        String guestName = booking.getGuest().getFullName();
        String roomId = booking.getRoom().getRoomIDString();
        double totalAmount = booking.getInvoice().getTotalAmount();
        double balanceAmount = booking.getInvoice().getTotalAmount() - booking.getInvoice().getBookingAdvance();

        Window owner = SwingUtilities.getWindowAncestor(c);
        JDialog dialog = new JDialog(owner, "Complete Booking", Dialog.ModalityType.APPLICATION_MODAL);

        final Double[] result = {null};
        Color muted = new Color(95, 110, 130);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);
        content.setBorder(new EmptyBorder(20, 24, 20, 24));

        // Header
        JLabel title = new JLabel("Complete Booking: " + bookingId);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel(guestName + "  •  " + roomId);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitle.setForeground(muted);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Total (read-only)
        JLabel totalCaption = new JLabel("Total amount");
        totalCaption.setFont(new Font("Arial", Font.PLAIN, 12));
        totalCaption.setForeground(muted);
        totalCaption.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel totalValue = new JLabel(String.format("Rs. %,.2f", totalAmount));
        totalValue.setFont(new Font("Arial", Font.BOLD, 16));
        totalValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel balanceCaption = new JLabel("Balance amount to pay");
        balanceCaption.setFont(new Font("Arial", Font.PLAIN, 12));
        balanceCaption.setForeground(muted);
        balanceCaption.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel balanceValue = new JLabel(String.format("Rs. %,.2f", balanceAmount));
        balanceValue.setFont(new Font("Arial", Font.BOLD, 16));
        balanceValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Balance input
        JLabel balanceAmountCaption = new JLabel("Enter amount (Rs.)");
        balanceAmountCaption.setFont(new Font("Arial", Font.PLAIN, 12));
        balanceAmountCaption.setForeground(muted);
        balanceAmountCaption.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField balanceField = new JTextField(String.format("%.2f", balanceAmount));
        balanceField.selectAll();
        balanceField.setFont(new Font("Arial", Font.PLAIN, 15));
        balanceField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 208, 218), 1, true),
                new EmptyBorder(8, 10, 8, 10)
        ));
        balanceField.setAlignmentX(Component.LEFT_ALIGNMENT);
        balanceField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        // Error message (hidden until needed)
        JLabel errorLbl = new JLabel(" ");
        errorLbl.setFont(new Font("Arial", Font.PLAIN, 12));
        errorLbl.setForeground(new Color(220, 38, 38));
        errorLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Buttons
        JButton cancelBtn = HelperFunctions.createActionButton("Cancel", new Color(95, 110, 130), true);
        JButton confirmBtn = new JButton("Confirm");
        confirmBtn.setFont(new Font("Arial", Font.BOLD, 12));
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.setBackground(new Color(22, 163, 74));
        confirmBtn.setOpaque(true);
        confirmBtn.setBorderPainted(false);
        confirmBtn.setFocusPainted(false);
        confirmBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confirmBtn.setBorder(new EmptyBorder(7, 18, 7, 18));

        Runnable confirmAction = () -> {
            String text = balanceField.getText().trim().replace(",", "");
            if(balanceAmount <= 0){
                result[0] = balanceAmount;
                dialog.dispose();
            }

            if (text.isEmpty()) {
                errorLbl.setText("Please enter the balance amount.");
                return;
            }

            double balance;
            try {
                balance = Double.parseDouble(text);
            } catch (NumberFormatException ex) {
                errorLbl.setText("Enter a valid number, e.g. 2500 or 2500.50");
                return;
            }

            if (balance < 0) {
                errorLbl.setText("Balance cannot be negative.");
                return;
            }
            if (balance > balanceAmount) {
                errorLbl.setText("Amount cannot exceed the balance of Rs. " + String.format("%,.2f", balanceAmount));
                return;
            }
            
            if (balance < balanceAmount) {
                errorLbl.setText("Amount cannot be less than Rs." + String.format("%,.2f", balanceAmount));
                return;
            }

            result[0] = balance;
            dialog.dispose();
        };

        confirmBtn.addActionListener(e -> confirmAction.run());
        balanceField.addActionListener(e -> confirmAction.run()); // Enter key confirms
        cancelBtn.addActionListener(e -> dialog.dispose());

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttonRow.setOpaque(false);
        buttonRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonRow.add(cancelBtn);
        buttonRow.add(confirmBtn);

        content.add(title);
        content.add(Box.createVerticalStrut(4));
        content.add(subtitle);
        content.add(Box.createVerticalStrut(18));

        content.add(totalCaption);
        content.add(Box.createVerticalStrut(2));
        content.add(totalValue);
        content.add(Box.createVerticalStrut(12));

        content.add(balanceCaption);          // was missing
        content.add(Box.createVerticalStrut(2));
        content.add(balanceValue);            // was missing
        content.add(Box.createVerticalStrut(16));

        content.add(balanceAmountCaption);
        content.add(Box.createVerticalStrut(4));
        content.add(balanceField);
        content.add(Box.createVerticalStrut(4));
        content.add(errorLbl);
        content.add(Box.createVerticalStrut(12));
        content.add(buttonRow);

        dialog.setContentPane(content);
        dialog.getRootPane().setDefaultButton(confirmBtn);
        dialog.setPreferredSize(new Dimension(380, 400));
        dialog.pack();
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(owner);

        SwingUtilities.invokeLater(balanceField::requestFocusInWindow);
        dialog.setVisible(true); // blocks here until the dialog is closed

        return result[0];
    }
}
