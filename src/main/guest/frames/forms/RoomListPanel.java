/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main.guest.frames.forms;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.*;
import javax.swing.border.*;
import main.models.Room;
import java.util.List;
import java.util.function.Consumer;
import main.models.room_packages.EmptyPackage;
import main.models.room_packages.RoomPackage;

/**
 *
 * @author N I L S H A D
 */
public class RoomListPanel extends javax.swing.JPanel {

    private JPanel listPanel;
    private JScrollPane scrollPane;
    private JLabel titleLabel;
    private final Consumer<Room> onRoomSelected;

    /**
     * Creates new form RoomListPanel
     */
    public RoomListPanel(List<Room> roomList, Consumer<Room> onRoomSelected) {
        initComponents();
        this.onRoomSelected = onRoomSelected;
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
                BorderFactory.createMatteBorder(
                        0, 0, 1, 0, new Color(215, 222, 230)
                ),
                new EmptyBorder(14, 16, 14, 16)
        ));
        row.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 76)
        );
        row.setPreferredSize(new Dimension(650, 76));

        row.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (isEmpty) {
            JLabel emptyText = new JLabel("No rooms available", SwingConstants.CENTER);
            emptyText.setFont(new Font("Arial", Font.PLAIN, 15));
            emptyText.setForeground(Color.GRAY);
            row.add(emptyText, BorderLayout.CENTER);
        } else {

            // Room number
            JLabel number = new JLabel(String.valueOf(room.getRoomID()));
            number.setFont(new Font("Arial", Font.BOLD, 18));
            number.setPreferredSize(new Dimension(60, 35));

            // Room details
            JPanel details = new JPanel();
            details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
            details.setOpaque(false);

            JLabel type = new JLabel(room.getRoomPackage().getPackageName());
            type.setFont(new Font("Arial", Font.BOLD, 15));
            type.setForeground(new Color(95, 110, 130));

            String breakfast = room.getBreakfast() ? "  |  🍲 Breakfast" : "";
           

            JLabel info = new JLabel("🛏 " + room.getBedType() + breakfast + "  |  👥 x " + room.getCapacity());
            info.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
            info.setForeground(new Color(95, 110, 130));

            details.add(type);
            details.add(Box.createVerticalStrut(5));
            details.add(info);

            // Price
            NumberFormat currency = NumberFormat.getInstance(
                    Locale.US
            );

            JLabel price = new JLabel(
                    "Rs. " + currency.format(room.getRoomPackage().getDailyRoomRate())
            );
            price.setFont(new Font("Arial", Font.BOLD, 14));

            JLabel perNight = new JLabel("per night");
            perNight.setFont(new Font("Arial", Font.PLAIN, 11));
            perNight.setForeground(Color.GRAY);

            JPanel pricePanel = new JPanel();
            pricePanel.setLayout(
                    new BoxLayout(pricePanel, BoxLayout.Y_AXIS)
            );
            pricePanel.setOpaque(false);
            pricePanel.setPreferredSize(new Dimension(120, 40));

            pricePanel.add(price);
            pricePanel.add(perNight);

            JLabel arrow = new JLabel("\u203A");
            arrow.setFont(new Font("Arial", Font.PLAIN, 24));
            arrow.setForeground(new Color(95, 110, 130));

            JPanel right = new JPanel(new BorderLayout(12, 0));
            right.setOpaque(false);
            right.add(pricePanel, BorderLayout.CENTER);
            right.add(arrow, BorderLayout.EAST);

            row.add(number, BorderLayout.WEST);
            row.add(details, BorderLayout.CENTER);
            row.add(right, BorderLayout.EAST);
        }

        // Click a room row
        row.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (!isEmpty) {
                    onRoomSelected.accept(room);

                }

            }

            @Override
            public void mouseEntered(
                    java.awt.event.MouseEvent e) {
                row.setBackground(new Color(240, 244, 248));
            }

            @Override
            public void mouseExited(
                    java.awt.event.MouseEvent e) {
                row.setBackground(Color.WHITE);
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
