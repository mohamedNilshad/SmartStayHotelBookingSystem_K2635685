/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main.frames.forms;

import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.*;
import javax.swing.border.*;
import main.models.Room;
import java.util.List;
import java.util.function.Consumer;
import main.functions.HelperFunctions;
import main.managers.BookingManager;
import main.models.Booking;
import main.models.room_packages.EmptyPackage;
import main.models.room_packages.RoomPackage;

/**
 *
 * @author N I L S H A D
 */
public class BookingListPanel extends javax.swing.JPanel {

    private JPanel listPanel;
    private JScrollPane scrollPane;
    private JLabel titleLabel;
    private final Consumer<Booking> onBookingSelected;

    /**
     * Creates new form RoomListPanel
     */
    public BookingListPanel(List<Booking> bookingList, Consumer<Booking> onBookingSelected) {
        initComponents();
        this.onBookingSelected = onBookingSelected;
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
        row.setMinimumSize(new Dimension(0, 100));
        row.setPreferredSize(new Dimension(650, 100));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color muted = new Color(95, 110, 130);

        if (isEmpty) {
            JLabel emptyText = new JLabel("No bookings available", SwingConstants.CENTER);
            emptyText.setFont(new Font("Arial", Font.PLAIN, 15));
            emptyText.setForeground(Color.GRAY);
            row.add(emptyText, BorderLayout.CENTER);
            
        } else {
            String bookingId = "#" + booking.getBookingID();
            String roomId = String.valueOf(booking.getRoom().getRoomID());
            String packageName = booking.getRoom().getRoomPackage().getPackageName();
            String bedType = booking.getRoom().getBedType();
            String noOfGuest = "x " + booking.getNumberOfGeust();
            String totalAmount = "Rs. 10000.00";
            String checkIn = HelperFunctions.formatDate(booking.getCheckInDate());
            String checkOut = HelperFunctions.formatDate(booking.getCheckOutDate());
            String status = booking.getStatus(); // "Active", "Cancelled", "Completed"
            boolean isActive = "Active".equals(status) && !HelperFunctions.isOneDayBefore(booking.getCheckInDate());
  
            
            boolean isCancelled = "Cancelled".equals(status);
            boolean isCompleted = "Completed".equals(status);
           
            JLabel idLbl = new JLabel(bookingId);
            idLbl.setFont(new Font("Arial", Font.BOLD, 18));
            idLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel badge = createStatusBadge(status);
            badge.setAlignmentX(Component.LEFT_ALIGNMENT);

            JPanel left = new JPanel();
            left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
            left.setOpaque(false);
            left.setPreferredSize(new Dimension(100, 60));
            left.add(idLbl);
            left.add(Box.createVerticalStrut(8));
            left.add(badge);

            
            JLabel roomLbl = new JLabel("Room " + roomId + "  •  " + packageName);
            roomLbl.setFont(new Font("Arial", Font.BOLD, 15));
            roomLbl.setForeground(muted);

            JLabel guestLbl = new JLabel("🛏 " + bedType + "  |  👥 " + noOfGuest);
            guestLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
            guestLbl.setForeground(muted);

            JLabel dateLbl = new JLabel("📅 " + checkIn + "  →  " + checkOut);
            dateLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
            dateLbl.setForeground(muted);

            JPanel details = new JPanel();
            details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
            details.setOpaque(false);
            details.add(roomLbl);
            details.add(Box.createVerticalStrut(5));
            details.add(guestLbl);
            details.add(Box.createVerticalStrut(3));
            details.add(dateLbl);

            
            JLabel amountLbl = new JLabel(totalAmount);
            amountLbl.setFont(new Font("Arial", Font.BOLD, 15));
            amountLbl.setAlignmentX(Component.RIGHT_ALIGNMENT);

            JLabel totalCaption = new JLabel("Total");
            totalCaption.setFont(new Font("Arial", Font.PLAIN, 11));
            totalCaption.setForeground(Color.GRAY);
            totalCaption.setAlignmentX(Component.RIGHT_ALIGNMENT);

            
            

            JButton editBtn = createActionButton("Edit", new Color(37, 99, 235), (isActive && !isCompleted && !isCancelled));
            JButton cancelBtn = createActionButton("Cancel", new Color(220, 38, 38), (isActive && !isCompleted && !isCancelled));

//            editBtn.addActionListener(e -> onBookingEdit.accept(booking));
            cancelBtn.addActionListener(e -> {
                
                List<Booking> updatedBookingList = BookingManager.setBookingStatus(1, booking.getBookingID());
                updateBookingList(updatedBookingList);
            });

            JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
            buttons.setOpaque(false);
            buttons.setAlignmentX(Component.RIGHT_ALIGNMENT);
            buttons.add(editBtn);
            buttons.add(cancelBtn);

            JPanel right = new JPanel();
            right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
            right.setOpaque(false);
            right.add(amountLbl);
            right.add(totalCaption);
            right.add(Box.createVerticalStrut(8));
            right.add(buttons);

            row.add(left, BorderLayout.WEST);
            row.add(details, BorderLayout.CENTER);
            row.add(right, BorderLayout.EAST);
        }

        
        row.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (!isEmpty) {
                    onBookingSelected.accept(booking);
                }
            }

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

    private JLabel createStatusBadge(String status) {
        Color bg;
        Color fg;

        String s = status == null ? "" : status;
        switch (s) {
            case "Active":
                bg = new Color(220, 252, 231);
                fg = new Color(22, 101, 52);
                break;
            case "Cancelled":
                bg = new Color(254, 226, 226);
                fg = new Color(153, 27, 27);
                break;
            case "Completed":
                bg = new Color(226, 232, 240);
                fg = new Color(51, 65, 85);
                break;
            default:
                bg = new Color(241, 245, 249);
                fg = Color.GRAY;
        }

        JLabel badge = new JLabel(s, SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        badge.setOpaque(false);
        badge.setBackground(bg);
        badge.setForeground(fg);
        badge.setFont(new Font("Arial", Font.BOLD, 11));
        badge.setBorder(new EmptyBorder(3, 12, 3, 12));
        return badge;
    }

    private JButton createActionButton(String text, Color color, boolean enabled) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setEnabled(enabled);
        btn.setCursor(new Cursor(enabled ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR));

        Color c = enabled ? color : new Color(180, 186, 195);
        btn.setForeground(c);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(c, 1, true),
                new EmptyBorder(5, 14, 5, 14)
        ));
        return btn;
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
