/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main.admin.frames.forms;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.*;
import javax.swing.border.*;
import main.models.Room;
import java.util.List;
import java.util.function.Consumer;
import main.functions.HelperFunctions;

/**
 *
 * @author N I L S H A D
 */
public class RoomListPanel extends javax.swing.JPanel {

    private JPanel listPanel;
    private JScrollPane scrollPane;
    private JLabel titleLabel;
    private Consumer<Room> onRoomEdit;
    private Consumer<Room> onRoomDelete;

    /**
     * Creates new form RoomListPanel
     * @param roomList
     * @param onRoomEdit
     * @param onRoomDelete
     */
    public RoomListPanel(List<Room> roomList, Consumer<Room> onRoomEdit, Consumer<Room> onRoomDelete) {
        initComponents();
        this.onRoomEdit = onRoomEdit;
        this.onRoomDelete = onRoomDelete;
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

        updateRoomList(roomList);
    }

    public void updateRoomList(List<Room> roomList) {

        listPanel.removeAll();
        if (roomList.isEmpty()) {
            listPanel.add(createRoomRow(new Room(), true));
        } else {
            for (Room room : roomList) {
                listPanel.add(createRoomRow(room, false));
            }
        }

        listPanel.revalidate();
        listPanel.repaint();

    }

    private JPanel createRoomRow(Room room, boolean isEmpty) {
        JPanel row = new JPanel(new BorderLayout(15, 0));

        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(215, 222, 230)),
                new EmptyBorder(14, 16, 14, 16)
        ));
        row.setMinimumSize(new Dimension(0, 90));
        row.setPreferredSize(new Dimension(650, 90));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (isEmpty) {
            JLabel emptyText = new JLabel("No rooms available", SwingConstants.CENTER);
            emptyText.setFont(new Font("Arial", Font.PLAIN, 15));
            emptyText.setForeground(Color.GRAY);
            row.add(emptyText, BorderLayout.CENTER);
        } else {
            Color muted = new Color(95, 110, 130);

            // Room number
            JLabel number = new JLabel(room.getRoomIDString());
            number.setFont(new Font("Arial", Font.BOLD, 18));
            number.setPreferredSize(new Dimension(60, 35));

            // Room details
            JPanel details = new JPanel();
            details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
            details.setOpaque(false);

            JLabel type = new JLabel(room.getRoomPackage().getPackageName());
            type.setFont(new Font("Arial", Font.BOLD, 15));
            type.setForeground(muted);

            String breakfast = room.getBreakfast() ? "  |  🍲 Breakfast" : "";

            JLabel info = new JLabel("🛏 " + room.getBedType() + breakfast + "  |  👥 x " + room.getCapacity());
            info.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
            info.setForeground(muted);

            details.add(type);
            details.add(Box.createVerticalStrut(5));
            details.add(info);

            // Price
            NumberFormat currency = NumberFormat.getInstance(Locale.US);

            JLabel price = new JLabel("Rs. " + currency.format(room.getRoomPackage().getDailyRoomRate())
                    + "  per night");
            price.setFont(new Font("Arial", Font.BOLD, 14));
            price.setAlignmentX(Component.RIGHT_ALIGNMENT);

            // Buttons
            JButton editBtn = HelperFunctions.createActionButton("Edit", new Color(37, 99, 235), true);
            JButton deleteBtn = HelperFunctions.createActionButton("Delete", new Color(220, 38, 38), true);

            editBtn.addActionListener(e -> onRoomEdit.accept(room));

            deleteBtn.addActionListener(e -> {
                int result = HelperFunctions.showConfirmDialog(
                        "Are you sure you want to delete room " + room.getRoomIDString() + "?",
                        "Confirm Room Deletion"
                );

                if (result == JOptionPane.YES_OPTION) {
                    onRoomDelete.accept(room);
                }
            });

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

            row.add(number, BorderLayout.WEST);
            row.add(details, BorderLayout.CENTER);
            row.add(right, BorderLayout.EAST);
        }

        // Click a room row
        row.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (!isEmpty) {
                    onRoomEdit.accept(room);
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
