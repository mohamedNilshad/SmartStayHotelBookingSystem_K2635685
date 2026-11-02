/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.components;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.Consumer;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import main.functions.HelperFunctions;
import main.models.K2635685_Room;

/**
 *
 * @author N I L S H A D
 */
public class RoomRowComponent {

    private static final Color MUTED = new Color(95, 110, 130);

    public static JPanel createRoomRow(
            K2635685_Room room,
            boolean isEmpty,
            boolean isAdmin,
            Consumer<K2635685_Room> onEdit,
            Consumer<K2635685_Room> onDelete,
            Consumer<K2635685_Room> onSelect
    ) {
        int rowHeight = isAdmin ? 90 : 76;

        JPanel row = new JPanel(new BorderLayout(15, 0));
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(215, 222, 230)),
                new EmptyBorder(14, 16, 14, 16)
        ));
        row.setMinimumSize(new Dimension(0, rowHeight));
        row.setPreferredSize(new Dimension(600, rowHeight));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, rowHeight));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boolean empty = isEmpty || room == null;

        if (empty) {
            JLabel emptyText = new JLabel("No rooms available", SwingConstants.CENTER);
            emptyText.setFont(new Font("Arial", Font.PLAIN, 15));
            emptyText.setForeground(Color.GRAY);
            row.add(emptyText, BorderLayout.CENTER);
        } else {
            row.add(buildNumber(room), BorderLayout.WEST);
            row.add(buildDetails(room), BorderLayout.CENTER);
            row.add(isAdmin
                    ? buildAdminRight(room, onEdit, onDelete)
                    : buildGuestRight(room), BorderLayout.EAST);
        }

        row.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // Only guests select a room by clicking the row
                if (!empty && !isAdmin && onSelect != null) {
                    onSelect.accept(room);
                }
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                row.setBackground(new Color(240, 244, 248));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                // Ignore exits caused by moving onto a child (label/button)
                Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), row);
                if (!row.contains(p)) {
                    row.setBackground(Color.WHITE);
                }
            }
        });

        return row;
    }

    // ---------- LEFT: room number ----------
    private static JLabel buildNumber(K2635685_Room room) {
        JLabel number = new JLabel(room.getRoomIDString());
        number.setFont(new Font("Arial", Font.BOLD, 18));
        number.setPreferredSize(new Dimension(60, 35));
        return number;
    }

    // ---------- CENTER: package + bed/breakfast/capacity ----------
    private static JPanel buildDetails(K2635685_Room room) {
        JLabel type = new JLabel(room.getRoomPackage().getPackageName());
        type.setFont(new Font("Arial", Font.BOLD, 15));
        type.setForeground(MUTED);

        String breakfast = room.getBreakfast() ? "  |  🍲 Breakfast" : "";

        JLabel info = new JLabel("🛏 " + room.getBedType() + breakfast + "  |  👥 x " + room.getCapacity());
        info.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        info.setForeground(MUTED);

        JPanel details = new JPanel();
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
        details.setOpaque(false);
        details.add(type);
        details.add(Box.createVerticalStrut(5));
        details.add(info);
        return details;
    }

    // ---------- RIGHT (admin): price + Edit/Delete ----------
    private static JPanel buildAdminRight(K2635685_Room room, Consumer<K2635685_Room> onEdit, Consumer<K2635685_Room> onDelete) {
        JLabel price = new JLabel(formatRate(room) + "  per night");
        price.setFont(new Font("Arial", Font.BOLD, 14));
        price.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JButton editBtn = HelperFunctions.createActionButton("Edit", new Color(37, 99, 235), true);
        JButton deleteBtn = HelperFunctions.createActionButton("Delete", new Color(220, 38, 38), true);

        if (onEdit != null)   editBtn.addActionListener(e -> onEdit.accept(room));
        if (onDelete != null) deleteBtn.addActionListener(e -> onDelete.accept(room));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttons.setOpaque(false);
        buttons.setAlignmentX(Component.RIGHT_ALIGNMENT);
        buttons.add(editBtn);
        buttons.add(deleteBtn);

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setOpaque(false);
        right.add(Box.createVerticalGlue());
        right.add(price);
        right.add(Box.createVerticalStrut(8));
        right.add(buttons);
        right.add(Box.createVerticalGlue());
        return right;
    }

    // ---------- RIGHT (guest): price, "per night" and arrow ----------
    private static JPanel buildGuestRight(K2635685_Room room) {
        JLabel price = new JLabel(formatRate(room));
        price.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel perNight = new JLabel("per night");
        perNight.setFont(new Font("Arial", Font.PLAIN, 11));
        perNight.setForeground(Color.GRAY);

        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.Y_AXIS));
        pricePanel.setOpaque(false);
        pricePanel.setPreferredSize(new Dimension(120, 40));
        pricePanel.add(price);
        pricePanel.add(perNight);

        JLabel arrow = new JLabel("\u203A");
        arrow.setFont(new Font("Arial", Font.PLAIN, 24));
        arrow.setForeground(MUTED);

        JPanel right = new JPanel(new BorderLayout(12, 0));
        right.setOpaque(false);
        right.add(pricePanel, BorderLayout.CENTER);
        right.add(arrow, BorderLayout.EAST);
        return right;
    }

    private static String formatRate(K2635685_Room room) {
        NumberFormat currency = NumberFormat.getInstance(Locale.US);
        return "Rs. " + currency.format(room.getRoomPackage().getDailyRoomRate());
    }
}