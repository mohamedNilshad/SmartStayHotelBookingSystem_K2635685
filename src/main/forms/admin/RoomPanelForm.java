/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main.forms.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import main.functions.HelperFunctions;
import main.managers.K2635685_RoomManager;
import main.models.K2635685_Room;

/**
 *
 * @author N I L S H A D
 */
public class RoomPanelForm extends javax.swing.JPanel {

    private final String placeholder = "Search...";
    private List<K2635685_Room> roomList = K2635685_RoomManager.getRoomList();
    private List<K2635685_Room> sortedRoomList = new ArrayList<>();
    private RoomListPanel roomListPanel;

    public RoomPanelForm() {
        initComponents();
        sortedRoomList = new ArrayList<>(roomList);

        var border = BorderFactory.createCompoundBorder(
                searchField.getBorder(),
                new EmptyBorder(5, 5, 5, 5)
        );

        searchField.setBorder(border);

        if (searchField.getText().isEmpty()) {
            searchField.setText(placeholder);
            searchField.setForeground(Color.GRAY);
        }

        int roomCount = sortedRoomList.size();

        tabelHeader.setText(roomCount + " rooms available");
        pnlContent.setLayout(new java.awt.BorderLayout());
        
        buildRoomListPanel();

        showPanel(roomListPanel);
    }

    private void showPanel(JPanel panel) {
        pnlContent.removeAll();

        pnlContent.setLayout(new BorderLayout());
        pnlContent.add(panel, BorderLayout.CENTER);

        pnlContent.revalidate();
        pnlContent.repaint();
    }

    private void buildRoomListPanel() {
        roomListPanel = new RoomListPanel(
                sortedRoomList,
                editRoom -> {
                    titleLabel.setText("Update Room");
                    subtitleLabel.setText("Edit this room.");
                    tabelHeader.setVisible(false);
                    searchField.setVisible(false);
                    newRoomBtn.setVisible(false);

                    EditRoomPanel fillPanel = new EditRoomPanel(
                            editRoom,
                            onUpdatedRoom -> {
                                titleLabel.setText("Rooms");
                                subtitleLabel.setText("Pick a room to manage.");
                                tabelHeader.setVisible(true);
                                searchField.setVisible(true);
                                newRoomBtn.setVisible(true);

                                roomList.replaceAll(r -> r.getRoomID() == onUpdatedRoom.getRoomID() ? onUpdatedRoom : r);
                                sortedRoomList.replaceAll(r -> r.getRoomID() == onUpdatedRoom.getRoomID() ? onUpdatedRoom : r);
                                // Update UI
                                tabelHeader.setText(sortedRoomList.size() + " rooms available");
                                roomListPanel.updateRoomList(sortedRoomList);
                                showPanel(roomListPanel);
                            },
                            () -> {
                                titleLabel.setText("Rooms");
                                subtitleLabel.setText("Pick a room to manage.");
                                tabelHeader.setVisible(true);
                                searchField.setVisible(true);
                                newRoomBtn.setVisible(true);

                                showPanel(roomListPanel);
                            }
                    );
                    showPanel(fillPanel);
                },
                deleteRoom -> {
                    int roomDeleted = K2635685_RoomManager.deleteRoom(deleteRoom.getRoomID());

                    switch (roomDeleted) {
                        case 1:
                            HelperFunctions.showPopMessage(this, "Room deleted successfully!", "Deletion Successful");
                            // Update UI
                            sortedRoomList.remove(deleteRoom);
                            tabelHeader.setText(sortedRoomList.size() + " rooms available");
                            roomListPanel.updateRoomList(sortedRoomList);
                            roomList = K2635685_RoomManager.getRoomList();
                            break;
                        case -1:
                            HelperFunctions.showPopMessage(this, "This room has a booking", "Deletion Failed");
                            break;
                        default:
                            HelperFunctions.showPopMessage(this, "Room deleted failed!", "Deletion Failed");
                            break;
                    }

                }
        );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        subtitleLabel = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        tabelHeader = new javax.swing.JLabel();
        canvas1 = new java.awt.Canvas();
        pnlContent = new javax.swing.JPanel();
        newRoomBtn = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(858, 705));
        setPreferredSize(new java.awt.Dimension(858, 699));

        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(22, 43, 64));
        titleLabel.setText("Rooms");

        subtitleLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        subtitleLabel.setForeground(java.awt.SystemColor.activeCaptionBorder);
        subtitleLabel.setText("Pick a room to manage.  ");

        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchFieldFocusLost(evt);
            }
        });
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchFieldKeyReleased(evt);
            }
        });

        tabelHeader.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabelHeader.setForeground(java.awt.SystemColor.activeCaptionBorder);
        tabelHeader.setText("0 room type available on 21 Oct 2026");

        pnlContent.setBackground(new java.awt.Color(255, 255, 255));
        pnlContent.setPreferredSize(new java.awt.Dimension(858, 520));

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 538, Short.MAX_VALUE)
        );

        newRoomBtn.setBackground(new java.awt.Color(51, 51, 255));
        newRoomBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        newRoomBtn.setForeground(new java.awt.Color(255, 255, 255));
        newRoomBtn.setText("New");
        newRoomBtn.setFocusable(false);
        newRoomBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newRoomBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(pnlContent, javax.swing.GroupLayout.DEFAULT_SIZE, 838, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(229, 229, 229)
                                .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(subtitleLabel)
                                    .addComponent(titleLabel)
                                    .addComponent(tabelHeader)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 310, Short.MAX_VALUE)
                                        .addComponent(newRoomBtn)))))
                        .addGap(17, 17, 17)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(titleLabel)
                .addGap(0, 0, 0)
                .addComponent(subtitleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(newRoomBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchField, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                .addGap(14, 14, 14)
                .addComponent(tabelHeader)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlContent, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(286, 286, 286))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchFieldFocusGained
        if (searchField.getText().equals(placeholder)) {
            searchField.setText("");
            searchField.setForeground(Color.BLACK); // Change back to normal typing color
        }
    }//GEN-LAST:event_searchFieldFocusGained

    private void searchFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchFieldFocusLost
        if (searchField.getText().isEmpty()) {
            searchField.setText(placeholder);
            searchField.setForeground(Color.GRAY);
        }
    }//GEN-LAST:event_searchFieldFocusLost

    private void searchFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchFieldKeyReleased
        String text = searchField.getText().trim();

        if (text.isEmpty()) {
            sortedRoomList = K2635685_RoomManager.getRoomList();

        } else {

            sortedRoomList.clear();

            for (K2635685_Room room : roomList) {
                boolean isPackageName = room.getRoomPackage().getPackageName().toLowerCase().contains(text.toLowerCase());
                boolean isRoomId = room.getRoomIDString().toLowerCase().contains(text.toLowerCase());

                if (isPackageName || isRoomId) {
                    sortedRoomList.add(room);
                }
            }
        }

        tabelHeader.setText(sortedRoomList.size() + " rooms available");

        // Update UI
        roomListPanel.updateRoomList(sortedRoomList);
    }//GEN-LAST:event_searchFieldKeyReleased

    private void newRoomBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newRoomBtnActionPerformed
        titleLabel.setText("New Room");
        subtitleLabel.setText("Add new room.");
        tabelHeader.setVisible(false);
        searchField.setVisible(false);
        newRoomBtn.setVisible(false);

        NewRoomPanel newRoomPanel = new NewRoomPanel(
                onNewRoom -> {
                    titleLabel.setText("Rooms");
                    subtitleLabel.setText("Pick a room to manage.");
                    tabelHeader.setVisible(true);
                    searchField.setVisible(true);
                    newRoomBtn.setVisible(true);
                    
                    buildRoomListPanel();

//                    roomListPanel = new RoomListPanel(
//                            sortedRoomList,
//                            editRoom -> {
//                                titleLabel.setText("Update Room");
//                                subtitleLabel.setText("Edit this room.");
//                                tabelHeader.setVisible(false);
//                                searchField.setVisible(false);
//
//                                EditRoomPanel fillPanel = new EditRoomPanel(
//                                        editRoom,
//                                        onUpdatedRoom -> {
//                                            titleLabel.setText("Rooms");
//                                            subtitleLabel.setText("Pick a room to manage.");
//                                            tabelHeader.setVisible(true);
//                                            searchField.setVisible(true);
//
//                                            roomList.replaceAll(r -> r.getRoomID() == onUpdatedRoom.getRoomID() ? onUpdatedRoom : r);
//                                            sortedRoomList.replaceAll(r -> r.getRoomID() == onUpdatedRoom.getRoomID() ? onUpdatedRoom : r);
//                                            // Update UI
//                                            tabelHeader.setText(sortedRoomList.size() + " rooms available");
//                                            roomListPanel.updateRoomList(sortedRoomList);
//                                            showPanel(roomListPanel);
//                                        },
//                                        () -> {
//                                            titleLabel.setText("Rooms");
//                                            subtitleLabel.setText("Pick a room to manage.");
//                                            tabelHeader.setVisible(true);
//                                            searchField.setVisible(true);
//
//                                            showPanel(roomListPanel);
//                                        }
//                                );
//                                showPanel(fillPanel);
//                            },
//                            deleteRoom -> {
//                                int roomDeleted = RoomManager.deleteRoom(deleteRoom.getRoomID());
//
//                                switch (roomDeleted) {
//                                    case 1:
//                                        HelperFunctions.showPopMessage(this, "Room deleted successfully!", "Deletion Successful");
//                                        // Update UI
//                                        sortedRoomList.remove(deleteRoom);
//                                        tabelHeader.setText(sortedRoomList.size() + " rooms available");
//                                        roomListPanel.updateRoomList(sortedRoomList);
//                                        roomList = RoomManager.getRoomList();
//                                        break;
//                                    case -1:
//                                        HelperFunctions.showPopMessage(this, "This room has a booking", "Deletion Failed");
//                                        break;
//                                    default:
//                                        HelperFunctions.showPopMessage(this, "Room deleted failed!", "Deletion Failed");
//                                        break;
//                                }
//
//                            }
//                    );
                    roomList.add(onNewRoom);
                    sortedRoomList.add(onNewRoom);
                    // Update UI
                    tabelHeader.setText(sortedRoomList.size() + " rooms available");
                    roomListPanel.updateRoomList(sortedRoomList);

                    showPanel(roomListPanel);
                },
                () -> {
                    titleLabel.setText("Rooms");
                    subtitleLabel.setText("Pick a room to manage.");
                    tabelHeader.setVisible(true);
                    searchField.setVisible(true);
                    newRoomBtn.setVisible(true);

                    showPanel(roomListPanel);
                }
        );
        showPanel(newRoomPanel);
    }//GEN-LAST:event_newRoomBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Canvas canvas1;
    private javax.swing.JButton newRoomBtn;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JLabel tabelHeader;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
