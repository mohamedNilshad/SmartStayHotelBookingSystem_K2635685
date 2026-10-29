/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main.admin.frames.forms;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.List;
import java.util.function.Consumer;
import main.functions.HelperFunctions;
import main.managers.BookingManager;
import main.models.Booking;
import main.models.Guest;

/**
 *
 * @author N I L S H A D
 */
public class BookingListPanel extends javax.swing.JPanel {

    private JPanel listPanel;
    private JScrollPane scrollPane;
    private JLabel titleLabel;
    private final Consumer<Booking> onBookingEdit;

    /**
     * Creates new form RoomListPanel
     */
    public BookingListPanel(List<Booking> bookingList, Consumer<Booking> onBookingEdit) {
        initComponents();
        this.onBookingEdit = onBookingEdit;
        setLayout(new BorderLayout());
        listPanel = new JPanel();

        listPanel.setLayout(
                new BoxLayout(listPanel, BoxLayout.Y_AXIS)
        );
        listPanel.setBackground(Color.WHITE);

        scrollPane = new JScrollPane(listPanel);

        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
        );

        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        add(scrollPane, java.awt.BorderLayout.CENTER);

        updateBookingList(bookingList);
    }

    public void updateBookingList(List<Booking> bookingList) {

        listPanel.removeAll();
        if (bookingList.isEmpty()) {
            listPanel.add(createBookingRow(new Booking(), true));
        } else {
            for (Booking booking : bookingList) {
                listPanel.add(createBookingRow(booking, false));
            }
        }

        listPanel.revalidate();
        listPanel.repaint();

    }

    private JPanel createBookingRow(Booking booking, boolean isEmpty) {

        JPanel row = new JPanel(new BorderLayout(20, 0));
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(215, 222, 230)),
                new EmptyBorder(14, 16, 14, 16)
        ));
        row.setMinimumSize(new Dimension(0, 110));
        row.setPreferredSize(new Dimension(650, 110));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color muted = new Color(95, 110, 130);

        if (isEmpty) {
            JLabel emptyText = new JLabel("No bookings available", SwingConstants.CENTER);
            emptyText.setFont(new Font("Arial", Font.PLAIN, 15));
            emptyText.setForeground(Color.GRAY);
            row.add(emptyText, BorderLayout.CENTER);

        } else {
            Guest guest = booking.getGuest();

            String bookingId = "#" + booking.getBookingID();
            String roomId = String.valueOf(booking.getRoom().getRoomID());
            String packageName = booking.getRoom().getRoomPackage().getPackageName();
            String bedType = booking.getRoom().getBedType();
            String noOfGuest = "x " + booking.getNumberOfGeust();
            String totalAmount = "Rs." + booking.getTotalAmount();
            String checkIn = HelperFunctions.formatDate(booking.getCheckInDate());
            String checkOut = HelperFunctions.formatDate(booking.getCheckOutDate());
            String status = booking.getStatus(); // "Active", "Cancelled", "Completed"

            String guestName = guest.getFullName();
            String guestPhone = guest.getContactNumber();
            String guestEmail = guest.getEmailAddress();

            boolean isCancelled = "Cancelled".equals(status);
            boolean isCompleted = "Completed".equals(status);
            boolean canModify = "Active".equals(status)
                    && !HelperFunctions.isOneDayBefore(booking.getCheckInDate())
                    && !isCompleted && !isCancelled;
            boolean canComplete = "Active".equals(status);

            JLabel idLbl = new JLabel(bookingId);
            idLbl.setFont(new Font("Arial", Font.BOLD, 18));
            idLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel badge = HelperFunctions.createStatusBadge(status);
            badge.setAlignmentX(Component.LEFT_ALIGNMENT);

            JPanel left = new JPanel();
            left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
            left.setOpaque(false);
            left.setPreferredSize(new Dimension(90, 60));
            left.add(idLbl);
            left.add(Box.createVerticalStrut(8));
            left.add(badge);

            JLabel nameLbl = new JLabel(guestName);
            nameLbl.setFont(new Font("Arial", Font.BOLD, 15));

            JLabel phoneLbl = new JLabel("📞 " + guestPhone);
            phoneLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
            phoneLbl.setForeground(muted);

            JLabel emailLbl = new JLabel("✉ " + guestEmail);
            emailLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
            emailLbl.setForeground(muted);

            JPanel guestText = new JPanel();
            guestText.setLayout(new BoxLayout(guestText, BoxLayout.Y_AXIS));
            guestText.setOpaque(false);
            guestText.add(nameLbl);
            guestText.add(Box.createVerticalStrut(5));
            guestText.add(phoneLbl);
            guestText.add(Box.createVerticalStrut(3));
            guestText.add(emailLbl);

            JPanel avatarWrap = new JPanel(new GridBagLayout());
            avatarWrap.setOpaque(false);
            avatarWrap.add(HelperFunctions.createAvatar(guestName));

            JPanel guestPanel = new JPanel(new BorderLayout(12, 0));
            guestPanel.setOpaque(false);
            guestPanel.add(avatarWrap, BorderLayout.WEST);
            guestPanel.add(guestText, BorderLayout.CENTER);

            JLabel roomLbl = new JLabel("Room " + roomId + "  •  " + packageName);
            roomLbl.setFont(new Font("Arial", Font.BOLD, 15));
            roomLbl.setForeground(muted);

            JLabel occupancyLbl = new JLabel("🛏 " + bedType + "  |  👥 " + noOfGuest);
            occupancyLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
            occupancyLbl.setForeground(muted);

            JLabel dateLbl = new JLabel("📅 " + checkIn + "  →  " + checkOut);
            dateLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
            dateLbl.setForeground(muted);

            JPanel stayPanel = new JPanel();
            stayPanel.setLayout(new BoxLayout(stayPanel, BoxLayout.Y_AXIS));
            stayPanel.setOpaque(false);
            stayPanel.add(roomLbl);
            stayPanel.add(Box.createVerticalStrut(5));
            stayPanel.add(occupancyLbl);
            stayPanel.add(Box.createVerticalStrut(3));
            stayPanel.add(dateLbl);

            stayPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(225, 230, 236)),
                    new EmptyBorder(0, 20, 0, 0)
            ));

            JPanel center = new JPanel(new GridLayout(1, 2, 20, 0));
            center.setOpaque(false);
            center.add(guestPanel);
            center.add(stayPanel);

            JLabel amountLbl = new JLabel(totalAmount);
            amountLbl.setFont(new Font("Arial", Font.BOLD, 15));
            amountLbl.setAlignmentX(Component.RIGHT_ALIGNMENT);

            JLabel totalCaption = new JLabel("Total");
            totalCaption.setFont(new Font("Arial", Font.PLAIN, 11));
            totalCaption.setForeground(Color.GRAY);
            totalCaption.setAlignmentX(Component.RIGHT_ALIGNMENT);

            JButton editBtn = HelperFunctions.createActionButton("Edit", new Color(37, 99, 235), canModify);
            JButton cancelBtn = HelperFunctions.createActionButton("Cancel", new Color(220, 38, 38), canModify);
            JButton completeBtn = HelperFunctions.createActionButton("Complete", new Color(22, 163, 74), canComplete);

            editBtn.addActionListener(e -> onBookingEdit.accept(booking));
            cancelBtn.addActionListener(e -> {

                int result = HelperFunctions.showConfirmDialog(
                        "Are you sure you want to cancel this booking?",
                        "Confirm Booking Cancellation"
                );

                if (result == JOptionPane.YES_OPTION) {
                    List<Booking> updatedBookingList = BookingManager.setBookingStatus(1, booking.getBookingID()); // Cancellation

                    updateBookingList(updatedBookingList);

                    HelperFunctions.showPopMessage(this, "Booking cancelled successfully!", "Cancellation Successful");
                }
            });
            completeBtn.addActionListener(e -> {
                Double balance = showCompleteBookingDialog(booking);

                if (balance != null) { // null means the user closed or cancelled the dialog
//                    List<Booking> updatedBookingList = BookingManager.completeBooking(booking.getBookingID(), balance);

//                    updateBookingList(updatedBookingList);

                    HelperFunctions.showPopMessage(this, "Booking completed successfully!", "Completion Successful");
                }
            });

            JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
            buttons.setOpaque(false);
            buttons.setAlignmentX(Component.RIGHT_ALIGNMENT);
            buttons.add(editBtn);
            buttons.add(cancelBtn);
            buttons.add(completeBtn);

            JPanel right = new JPanel();
            right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
            right.setOpaque(false);
            right.add(amountLbl);
            right.add(totalCaption);
            right.add(Box.createVerticalStrut(8));
            right.add(buttons);

            row.add(left, BorderLayout.WEST);
            row.add(center, BorderLayout.CENTER);
            row.add(right, BorderLayout.EAST);
        }

        row.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                row.setBackground(new Color(240, 244, 248));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), row);
                if (!row.contains(p)) {
                    row.setBackground(Color.WHITE);
                }
            }
        });

        return row;
    }

    private Double showCompleteBookingDialog(Booking booking) {
        Window owner = SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog(owner, "Complete Booking", Dialog.ModalityType.APPLICATION_MODAL);

        final Double[] result = {null};
        Color muted = new Color(95, 110, 130);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);
        content.setBorder(new EmptyBorder(20, 24, 20, 24));

        // Header
        JLabel title = new JLabel("Complete Booking #" + booking.getBookingID());
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel(booking.getGuest().getFullName() + "  •  Room " + booking.getRoom().getRoomID());
        subtitle.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitle.setForeground(muted);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Total (read-only)
        JLabel totalCaption = new JLabel("Total amount");
        totalCaption.setFont(new Font("Arial", Font.PLAIN, 12));
        totalCaption.setForeground(muted);
        totalCaption.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel totalValue = new JLabel("Rs." + (booking.getTotalAmount() - booking.getBookingAdvance()));
        totalValue.setFont(new Font("Arial", Font.BOLD, 16));
        totalValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Balance input
        JLabel balanceCaption = new JLabel("Balance amount (Rs.)");
        balanceCaption.setFont(new Font("Arial", Font.PLAIN, 12));
        balanceCaption.setForeground(muted);
        balanceCaption.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField balanceField = new JTextField();
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
        content.add(Box.createVerticalStrut(16));
        content.add(balanceCaption);
        content.add(Box.createVerticalStrut(4));
        content.add(balanceField);
        content.add(Box.createVerticalStrut(4));
        content.add(errorLbl);
        content.add(Box.createVerticalStrut(12));
        content.add(buttonRow);

        dialog.setContentPane(content);
        dialog.getRootPane().setDefaultButton(confirmBtn);
        dialog.setPreferredSize(new Dimension(380, 340));
        dialog.pack();
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(owner);

        SwingUtilities.invokeLater(balanceField::requestFocusInWindow);
        dialog.setVisible(true); // blocks here until the dialog is closed

        return result[0];
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
