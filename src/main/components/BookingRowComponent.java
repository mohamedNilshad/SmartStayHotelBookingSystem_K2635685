/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.components;

import java.awt.*;
import java.util.function.Consumer;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import main.functions.HelperFunctions;
import main.models.K2635685_Booking;
import main.models.users.K2635685_Guest;

/**
 *
 * @author N I L S H A D
 */
public class BookingRowComponent {

    private static final Color MUTED = new Color(95, 110, 130);

    public static JPanel createBookingRow(
            K2635685_Booking booking,
            boolean isEmpty,
            boolean isAdmin,
            Consumer<K2635685_Booking> onEdit,
            Consumer<K2635685_Booking> onCancel,
            Consumer<K2635685_Booking> onComplete,
            Consumer<K2635685_Booking> onSelect
    ) {

        int rowHeight = isAdmin ? 110 : 100;

        JPanel row = new JPanel(new BorderLayout(20, 0));
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(215, 222, 230)),
                new EmptyBorder(14, 16, 14, 16)
        ));
        row.setMinimumSize(new Dimension(0, rowHeight));
        row.setPreferredSize(new Dimension(650, rowHeight));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, rowHeight));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (isEmpty || booking == null) {
            JLabel emptyText = new JLabel("No bookings available", SwingConstants.CENTER);
            emptyText.setFont(new Font("Arial", Font.PLAIN, 15));
            emptyText.setForeground(Color.GRAY);
            row.add(emptyText, BorderLayout.CENTER);
        } else {
            String status = booking.getStatus(); // "Active", "Cancelled", "Completed"
            boolean isActive = "Active".equals(status);
            boolean canModify = isActive && !HelperFunctions.isOneDayBefore(booking.getCheckInDate());
            boolean canComplete = isActive;

            row.add(buildLeft(booking, status, isAdmin), BorderLayout.WEST);
            row.add(buildCenter(booking, isAdmin), BorderLayout.CENTER);
            row.add(buildRight(booking, isAdmin, canModify, canComplete,
                    onEdit, onCancel, onComplete), BorderLayout.EAST);
        }

        row.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                onSelect.accept(booking);
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

    // ---------- LEFT: booking id + status badge ----------
    private static JPanel buildLeft(K2635685_Booking booking, String status, boolean isAdmin) {
        JLabel idLbl = new JLabel(booking.getBookingIDString());
        idLbl.setFont(new Font("Arial", Font.BOLD, 18));
        idLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel badge = HelperFunctions.createStatusBadge(status);
        badge.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setOpaque(false);
        left.setPreferredSize(new Dimension(isAdmin ? 90 : 100, 60));
        left.add(idLbl);
        left.add(Box.createVerticalStrut(8));
        left.add(badge);
        return left;
    }

    // ---------- CENTER: stay details (+ guest info for admin) ----------
    private static JPanel buildCenter(K2635685_Booking booking, boolean isAdmin) {
        JPanel stayPanel = buildStayPanel(booking);

        if (!isAdmin) {
            return stayPanel; // guests only see the stay details
        }

        stayPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(225, 230, 236)),
                new EmptyBorder(0, 20, 0, 0)
        ));

        JPanel center = new JPanel(new GridLayout(1, 2, 20, 0));
        center.setOpaque(false);
        center.add(buildGuestPanel(booking.getGuest()));
        center.add(stayPanel);
        return center;
    }

    private static JPanel buildStayPanel(K2635685_Booking booking) {
        String roomId = booking.getRoom().getRoomIDString();
        String packageName = booking.getRoom().getRoomPackage().getPackageName();
        String bedType = booking.getRoom().getBedType();
        String noOfGuest = "x " + booking.getNumberOfGeust();
        String checkIn = HelperFunctions.formatDate(booking.getCheckInDate());
        String checkOut = HelperFunctions.formatDate(booking.getCheckOutDate());

        JLabel roomLbl = new JLabel(roomId + "  •  " + packageName);
        roomLbl.setFont(new Font("Arial", Font.BOLD, 15));
        roomLbl.setForeground(MUTED);

        JLabel occupancyLbl = new JLabel("🛏 " + bedType + "  |  👥 " + noOfGuest);
        occupancyLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        occupancyLbl.setForeground(MUTED);

        JLabel dateLbl = new JLabel("📅 " + checkIn + "  →  " + checkOut);
        dateLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        dateLbl.setForeground(MUTED);

        JPanel stay = new JPanel();
        stay.setLayout(new BoxLayout(stay, BoxLayout.Y_AXIS));
        stay.setOpaque(false);
        stay.add(roomLbl);
        stay.add(Box.createVerticalStrut(5));
        stay.add(occupancyLbl);
        stay.add(Box.createVerticalStrut(3));
        stay.add(dateLbl);
        return stay;
    }

    private static JPanel buildGuestPanel(K2635685_Guest guest) {
        String guestName = guest.getFullName();

        JLabel nameLbl = new JLabel(guestName);
        nameLbl.setFont(new Font("Arial", Font.BOLD, 15));

        JLabel phoneLbl = new JLabel("📞 " + guest.getContactNumber());
        phoneLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        phoneLbl.setForeground(MUTED);

        JLabel emailLbl = new JLabel("✉ " + guest.getEmailAddress());
        emailLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        emailLbl.setForeground(MUTED);

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
        return guestPanel;
    }

    // ---------- RIGHT: amount + buttons ----------
    private static JPanel buildRight(
            K2635685_Booking booking,
            boolean isAdmin,
            boolean canModify,
            boolean canComplete,
            Consumer<K2635685_Booking> onEdit,
            Consumer<K2635685_Booking> onCancel,
            Consumer<K2635685_Booking> onComplete
    ) {
        JLabel amountLbl = new JLabel("Rs." + (booking.getInvoice().getTotalAmount() + booking.getAdvncAmount()));
        amountLbl.setFont(new Font("Arial", Font.BOLD, 15));
        amountLbl.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel totalCaption = new JLabel("Total");
        totalCaption.setFont(new Font("Arial", Font.PLAIN, 11));
        totalCaption.setForeground(Color.GRAY);
        totalCaption.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JButton editBtn = HelperFunctions.createActionButton("Edit", new Color(37, 99, 235), canModify);
        JButton cancelBtn = HelperFunctions.createActionButton("Cancel", new Color(220, 38, 38), canModify);

        if (onEdit != null && !isAdmin)   editBtn.addActionListener(e -> onEdit.accept(booking));
        if (onCancel != null) cancelBtn.addActionListener(e -> onCancel.accept(booking));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttons.setOpaque(false);
        buttons.setAlignmentX(Component.RIGHT_ALIGNMENT);
        if(!isAdmin) buttons.add(editBtn);
        buttons.add(cancelBtn);

        // Complete button is admin-only
        if (isAdmin) {
            JButton completeBtn = HelperFunctions.createActionButton("Complete", new Color(22, 163, 74), canComplete);
            if (onComplete != null) completeBtn.addActionListener(e -> onComplete.accept(booking));
            buttons.add(completeBtn);
        }

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setOpaque(false);
        right.add(amountLbl);
        right.add(totalCaption);
        right.add(Box.createVerticalStrut(8));
        right.add(buttons);
        return right;
    }
}