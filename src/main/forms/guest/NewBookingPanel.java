/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main.forms.guest;

import java.awt.*;
import java.util.List;
import java.util.Date;
import javax.swing.*;
import main.components.InvoiceComponent;
import main.components.RoomRowComponent;
import main.functions.CalculateTotal;
import main.functions.HelperFunctions;
import main.functions.ValidationFunctions;
import main.managers.K2635685_BookingManager;
import main.managers.K2635685_GuestManager;
import main.managers.K2635685_RoomManager;
import main.models.K2635685_Booking;
import main.models.K2635685_Invoice;
import main.models.K2635685_Room;
import main.models.room_packages.K2635685_RoomPackage;

/**
 *
 * @author N I L S H A D
 */
public class NewBookingPanel extends javax.swing.JPanel {

    private final Runnable onBack;
    private final K2635685_Room selectedRoom;
    private K2635685_Invoice invoice;

    /**
     * Creates new form RoomListPanel
     *
     * @param room
     * @param onBack
     */
    public NewBookingPanel(K2635685_Room room, Runnable onBack) {
        initComponents();
        roomPackageDropDown.setBackground(Color.white);
        bedTypeDropDown.setBackground(Color.white);
        checkInDate.setBackground(Color.white);
        advanceAmount.setBackground(Color.white);

        availableRoomsPanel.setLayout(new BorderLayout());
        availableRoomsPanel.setVisible(false);
        roomListCount.setVisible(false);
        bookingDetailsScrollPane.setVisible(false);
        confirmBooking.setVisible(false);

        this.onBack = onBack;
        selectedRoom = room;

        checkInDate.getDateEditor().addPropertyChangeListener("date", evt -> {

            Date selectedDate = (Date) evt.getNewValue();

            if (selectedDate != null) {
                boolean isNotValidDate = ValidationFunctions.validateCheckInDate(selectedDate);
                if (isNotValidDate) {
                    HelperFunctions.showPopMessage(this, "Check-in date must be at least 2 days from today.", "");
                    Date newCheckInDate = HelperFunctions.getDefaultCheckInDate();
                    checkInDate.setDate(newCheckInDate);
                    checkOutDate.setDate(HelperFunctions.getDefaultCheckOutDate(newCheckInDate));
                } else {
                    checkOutDate.setDate(HelperFunctions.getDefaultCheckOutDate(selectedDate));
                }
            }
        });

        checkOutDate.getDateEditor().addPropertyChangeListener("date", evt -> {

            Date selectedcheckOutDate = (Date) evt.getNewValue();

            if (selectedcheckOutDate != null) {
                boolean isNotValidDate = ValidationFunctions.validateCheckOutDate(selectedcheckOutDate, checkInDate.getDate());
                if (isNotValidDate) {
                    HelperFunctions.showPopMessage(this, "Check-out date must be at least 1 day from Check-in date.", "");
                    checkOutDate.setDate(HelperFunctions.getDefaultCheckOutDate(checkInDate.getDate()));
                }
            }
        });

        setForm();
    }

    private void setForm() {
        roomPackageDropDown.removeAllItems();
        bedTypeDropDown.removeAllItems();

        for (K2635685_RoomPackage roomPackage : K2635685_RoomManager.getRoomPackageList()) {
            roomPackageDropDown.addItem(roomPackage.getPackageName());
        }

        for (String bedType : K2635685_RoomManager.getBedTypes()) {
            bedTypeDropDown.addItem(bedType);
        }

        roomPackageDropDown.setSelectedItem(selectedRoom.getRoomPackage().getPackageName());
        bedTypeDropDown.setSelectedItem(selectedRoom.getBedType());

        checkInDate.setDate(HelperFunctions.getDefaultCheckInDate());
        checkOutDate.setDate(HelperFunctions.getDefaultCheckOutDate(HelperFunctions.getDefaultCheckInDate()));

        numberOfGuest.setText(String.valueOf(selectedRoom.getCapacity()));
        advanceAmount.setText("10000.0");
    }

    private JScrollPane createRoomListScrollPane(List<K2635685_Room> rooms) {
        // Container that holds all rows, stacked vertically

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        if (rooms == null || rooms.isEmpty()) {
            listPanel.add(
                    RoomRowComponent.createRoomRow(
                            null, true, false,
                            null, null,
                            null
                    )
            );
        } else {
            for (K2635685_Room roomItem : rooms) {
                listPanel.add(
                        RoomRowComponent.createRoomRow(
                                roomItem, false, false,
                                null, null,
                                room -> {
                                    invoice = CalculateTotal.calculation(room, checkInDate.getDate(),
                                            checkOutDate.getDate(), numberOfGuest.getText(), advanceAmount.getText().trim());
                                    showInvoice(room);
                                }
                        )
                );
            }
        }

        listPanel.add(Box.createVerticalGlue());

        JScrollPane rowScrollPane = new JScrollPane(listPanel);
        rowScrollPane.setBorder(BorderFactory.createEmptyBorder());
        rowScrollPane.getViewport().setBackground(Color.WHITE);
        rowScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        rowScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rowScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        return rowScrollPane;
    }

    private void showInvoice(K2635685_Room room) {
 
        InvoiceComponent invoicePanel = new InvoiceComponent(
                invoice, room, 
                checkInDate.getDate(), 
                checkOutDate.getDate(), 
                Integer.parseInt(numberOfGuest.getText()),
                K2635685_GuestManager.getCurrentGuest()
        );

    
        invoicePanel.setPreferredSize(new Dimension(417, 600));

        bookingDetailsScrollPane.setViewportView(invoicePanel);

        bookingDetailsScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        bookingDetailsScrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        bookingDetailsScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        bookingDetailsScrollPane.revalidate();
        bookingDetailsScrollPane.repaint();
        bookingDetailsScrollPane.setVisible(true);
        confirmBooking.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        roomPackageDropDown = new javax.swing.JComboBox<>();
        bedTypeDropDown = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        checkInDate = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        checkOutDate = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        nogMinus = new javax.swing.JButton();
        nogPlus = new javax.swing.JButton();
        numberOfGuest = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        availableRoomsPanel = new javax.swing.JPanel();
        roomListCount = new javax.swing.JLabel();
        confirmBooking = new javax.swing.JButton();
        advanceAmount = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        bookingDetailsScrollPane = new javax.swing.JScrollPane();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(858, 520));

        jButton3.setBackground(new java.awt.Color(0, 0, 0));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Back");
        jButton3.setFocusable(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Room package");

        roomPackageDropDown.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        roomPackageDropDown.setOpaque(true);

        bedTypeDropDown.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        bedTypeDropDown.setOpaque(true);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Bed type");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Check-in date");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Check-out date");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Number of guests");

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        nogMinus.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        nogMinus.setText("-");
        nogMinus.setBorder(null);
        nogMinus.setFocusable(false);
        nogMinus.setMaximumSize(new java.awt.Dimension(25, 25));
        nogMinus.setMinimumSize(new java.awt.Dimension(25, 25));
        nogMinus.setPreferredSize(new java.awt.Dimension(25, 25));
        nogMinus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nogMinusActionPerformed(evt);
            }
        });

        nogPlus.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        nogPlus.setText("+");
        nogPlus.setBorder(null);
        nogPlus.setFocusable(false);
        nogPlus.setMaximumSize(new java.awt.Dimension(25, 25));
        nogPlus.setMinimumSize(new java.awt.Dimension(25, 25));
        nogPlus.setPreferredSize(new java.awt.Dimension(25, 25));
        nogPlus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nogPlusActionPerformed(evt);
            }
        });

        numberOfGuest.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        numberOfGuest.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numberOfGuest.setText("1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(nogMinus, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(numberOfGuest, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(nogPlus, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(numberOfGuest, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nogPlus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nogMinus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jButton1.setBackground(new java.awt.Color(31, 58, 86));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Check Availability");
        jButton1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(31, 58, 86), 1, true));
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout availableRoomsPanelLayout = new javax.swing.GroupLayout(availableRoomsPanel);
        availableRoomsPanel.setLayout(availableRoomsPanelLayout);
        availableRoomsPanelLayout.setHorizontalGroup(
            availableRoomsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 535, Short.MAX_VALUE)
        );
        availableRoomsPanelLayout.setVerticalGroup(
            availableRoomsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        roomListCount.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        roomListCount.setForeground(new java.awt.Color(102, 102, 102));
        roomListCount.setText("Available Rooms (1)");

        confirmBooking.setBackground(new java.awt.Color(42, 111, 42));
        confirmBooking.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        confirmBooking.setForeground(new java.awt.Color(255, 255, 255));
        confirmBooking.setText("Confirm Booking");
        confirmBooking.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(31, 58, 86), 1, true));
        confirmBooking.setFocusable(false);
        confirmBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmBookingActionPerformed(evt);
            }
        });

        advanceAmount.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        advanceAmount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        advanceAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                advanceAmountKeyReleased(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setText("Booking advance Rs. ");

        bookingDetailsScrollPane.setBackground(new java.awt.Color(255, 255, 255));
        bookingDetailsScrollPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel9)
                                .addComponent(roomPackageDropDown, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel11)
                            .addComponent(checkInDate, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addComponent(advanceAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel12)
                                .addComponent(jLabel10)
                                .addComponent(checkOutDate, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                                .addComponent(bedTypeDropDown, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(roomListCount)
                    .addComponent(availableRoomsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(confirmBooking, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                    .addComponent(bookingDetailsScrollPane))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bookingDetailsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(confirmBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(roomPackageDropDown, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bedTypeDropDown, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(checkOutDate, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(checkInDate, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(15, 15, 15)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel17))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(advanceAmount)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(37, 37, 37)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(roomListCount)
                                .addGap(4, 4, 4)
                                .addComponent(availableRoomsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        onBack.run();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void nogMinusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nogMinusActionPerformed
        int value = Integer.parseInt(numberOfGuest.getText());
        if (value > 1) {
            value--;
            numberOfGuest.setText(String.valueOf(value));
        }
    }//GEN-LAST:event_nogMinusActionPerformed

    private void nogPlusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nogPlusActionPerformed
        int value = Integer.parseInt(numberOfGuest.getText());
        if (value < 10) {
            value++;
            numberOfGuest.setText(String.valueOf(value));
        }
    }//GEN-LAST:event_nogPlusActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        double number = Double.parseDouble(advanceAmount.getText().trim());
        if (number < 10000.0) {
            HelperFunctions.showPopMessage(this, "Amount should be at least Rs.10000.0", "");
            advanceAmount.requestFocus();

        } else {
            bookingDetailsScrollPane.setVisible(false);
            confirmBooking.setVisible(false);

            List<K2635685_Room> availableRoomList = K2635685_BookingManager.getAvailableRoomList(
                    bedTypeDropDown.getSelectedItem().toString(),
                    roomPackageDropDown.getSelectedItem().toString(),
                    checkInDate.getDate(),
                    checkOutDate.getDate(),
                    -1
            );

            availableRoomsPanel.setVisible(true);
            availableRoomsPanel.removeAll();

            roomListCount.setVisible(true);
            roomListCount.setText("Available rooms (" + availableRoomList.size() + ")");

            availableRoomsPanel.add(createRoomListScrollPane(availableRoomList), BorderLayout.CENTER);
            availableRoomsPanel.revalidate();
            availableRoomsPanel.repaint();
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void confirmBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmBookingActionPerformed

        K2635685_Booking booking = new K2635685_Booking(
                selectedRoom,
                K2635685_GuestManager.getCurrentGuest(),
                checkInDate.getDate(),
                checkOutDate.getDate(),
                HelperFunctions.getCurrentDate(),
                Integer.parseInt(numberOfGuest.getText()),
                invoice,
                K2635685_BookingManager.getBookingStatus(0)
        );

        K2635685_BookingManager.addNewBooking(booking);
        HelperFunctions.showPopMessage(this, "Room No " + selectedRoom.getRoomIDString() + " booked successfully!", "");
        onBack.run();
    }//GEN-LAST:event_confirmBookingActionPerformed

    private void advanceAmountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_advanceAmountKeyReleased
        String value = advanceAmount.getText().trim();
        boolean isValid = ValidationFunctions.validateDoubleValue(this, value);
        if (!isValid) {
            advanceAmount.setText("10000.0");
            advanceAmount.requestFocus();
        }


    }//GEN-LAST:event_advanceAmountKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField advanceAmount;
    private javax.swing.JPanel availableRoomsPanel;
    private javax.swing.JComboBox<String> bedTypeDropDown;
    private javax.swing.JScrollPane bookingDetailsScrollPane;
    private com.toedter.calendar.JDateChooser checkInDate;
    private com.toedter.calendar.JDateChooser checkOutDate;
    private javax.swing.JButton confirmBooking;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton nogMinus;
    private javax.swing.JButton nogPlus;
    private javax.swing.JLabel numberOfGuest;
    private javax.swing.JLabel roomListCount;
    private javax.swing.JComboBox<String> roomPackageDropDown;
    // End of variables declaration//GEN-END:variables
}
