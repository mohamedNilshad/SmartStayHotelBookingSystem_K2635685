/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main.guest.frames.forms;

import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import main.functions.HelperFunctions;
import main.functions.ValidationFunctions;
import main.managers.BookingManager;
import main.managers.GuestManager;
import main.managers.RoomManager;
import main.models.Booking;
import main.models.Room;
import main.models.room_packages.RoomPackage;

/**
 *
 * @author N I L S H A D
 */
public class NewBookingPanel extends javax.swing.JPanel {

    private final Runnable onBack;
    private Room selectedRoom;
    private List<Room> allRooms = new ArrayList<>();
    private JScrollPane scrollPane;
    private double totalAmount;

    /**
     * Creates new form RoomListPanel
     *
     * @param room
     * @param onBack
     */
    public NewBookingPanel(Room room, Runnable onBack) {
        initComponents();
        roomPackageDropDown.setBackground(Color.white);
        bedTypeDropDown.setBackground(Color.white);
        checkInDate.setBackground(Color.white);
        advanceAmount.setBackground(Color.white);

        availableRoomsPanel.setLayout(new BorderLayout());
        availableRoomsPanel.setVisible(false);
        roomListCount.setVisible(false);
        bookingDetailsPanel.setVisible(false);
        confirmBooking.setVisible(false);

        this.onBack = onBack;
        totalAmount = 0.0;
        allRooms = RoomManager.getRoomList();
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

        for (RoomPackage roomPackage : RoomManager.getRoomPackageList()) {
            roomPackageDropDown.addItem(roomPackage.getPackageName());
        }

        for (String bedType : RoomManager.getBedTypes()) {
            bedTypeDropDown.addItem(bedType);
        }

        roomPackageDropDown.setSelectedItem(selectedRoom.getRoomPackage().getPackageName());
        bedTypeDropDown.setSelectedItem(selectedRoom.getBedType());

        checkInDate.setDate(HelperFunctions.getDefaultCheckInDate());
        checkOutDate.setDate(HelperFunctions.getDefaultCheckOutDate(HelperFunctions.getDefaultCheckInDate()));

        numberOfGuest.setText(String.valueOf(selectedRoom.getCapacity()));
        advanceAmount.setText("10000.0");
    }

    private JScrollPane createRoomListScrollPane(List<Room> rooms) {
        // Container that holds all rows, stacked vertically

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        if (rooms == null || rooms.isEmpty()) {
            listPanel.add(createRoomRow(null, true));
        } else {
            for (Room room : rooms) {
                listPanel.add(createRoomRow(room, false));
            }
        }

        listPanel.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        return scrollPane;
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
            JLabel number = new JLabel(room.getRoomIDString());
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
                    selectedRoom = room;
                    showInvoice(room);
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

    private void showInvoice(Room room) {
        String roomId = room.getRoomIDString();
        String packageName = room.getRoomPackage().getPackageName();
        String bedType = room.getBedType();

        String guestName = GuestManager.getCurrentGuest().getFullName();
        String guestEmail = GuestManager.getCurrentGuest().getEmailAddress();
        String guestPhone = GuestManager.getCurrentGuest().getContactNumber();

        String tempCheckInDate = HelperFunctions.formatDate(checkInDate.getDate());
        String tempCheckOutDate = HelperFunctions.formatDate(checkOutDate.getDate());

        long noOfNights = HelperFunctions.getNoOfDays(checkInDate.getDate(), checkOutDate.getDate());
        int noOfGuest = Integer.parseInt(numberOfGuest.getText());
        int extGuestCount = (noOfGuest > room.getCapacity()) ?  (noOfGuest - room.getCapacity()) : 0;

        double dailyRoomRate = room.getRoomPackage().getDailyRoomRate();
        double extraGuestCharge = room.getRoomPackage().getExtraGuestCharge();
        double extAmount = extGuestCount * extraGuestCharge * noOfNights;
        double advncAmount = Double.parseDouble(advanceAmount.getText().trim());

        double taxRate = room.getRoomPackage().getTaxRate();
        double discountRate = 0.0;
        if (noOfNights >= 5) {
            discountRate = room.getDiscountRate();
        }

        totalAmount = dailyRoomRate * noOfNights;
        totalAmount += extAmount;

        double discountAmount = totalAmount * (discountRate / 100);
        totalAmount -= discountAmount;

        double taxAmount = totalAmount * (taxRate / 100);
        totalAmount += taxAmount;

        totalAmount -= advncAmount;

        bookingDetailsPanel.setVisible(true);
        confirmBooking.setVisible(true);

        roomNumber.setText(roomId);
        roomInfo.setText(packageName + " • " + bedType + " bed");

        guestNameLbl.setText(guestName);
        guestEmailLbl.setText(guestEmail);
        guestPhoneLbl.setText(guestPhone);

        checkInDateInv.setText(tempCheckInDate);
        checkOutDateInv.setText(tempCheckOutDate);

        noOfNightsInv.setText(String.valueOf(noOfNights));
        noOfGuestsInv.setText(String.valueOf(noOfGuest));

        roomAmount.setText("Rs. " + dailyRoomRate + "  x  " + noOfNights);
        totRoomAmount.setText("Rs. " + dailyRoomRate * noOfNights);

        extGuestCharge.setText("Rs. " + extraGuestCharge + "  x  " + extGuestCount);
        totExtGuestCharge.setText("Rs. " + extAmount);

        discountPercentLbl.setText("(" + discountRate + " %)");
        totDiscountAmt.setText("-Rs." + discountAmount);

        taxPercentLbl.setText("(" + taxRate + " %)");
        totTaxAmt.setText("Rs." + taxAmount);
        
        bookingAdvance.setText("-Rs. " + advncAmount);
        totalAmountLbl.setText("Rs. " + totalAmount);
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
        bookingDetailsPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        roomNumber = new javax.swing.JLabel();
        roomInfo = new javax.swing.JLabel();
        guestNameLbl = new javax.swing.JLabel();
        guestEmailLbl = new javax.swing.JLabel();
        guestPhoneLbl = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        checkInDateInv = new javax.swing.JLabel();
        checkOutDateInv = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        noOfNightsInv = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        noOfGuestsInv = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        extGuestCharge = new javax.swing.JLabel();
        totExtGuestCharge = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        discountPercentLbl = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        taxPercentLbl = new javax.swing.JLabel();
        bookingAdvance = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        totalAmountLbl = new javax.swing.JLabel();
        totRoomAmount = new javax.swing.JLabel();
        roomAmount = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        totDiscountAmt = new javax.swing.JLabel();
        totTaxAmt = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        availableRoomsPanel = new javax.swing.JPanel();
        roomListCount = new javax.swing.JLabel();
        confirmBooking = new javax.swing.JButton();
        advanceAmount = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();

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
        roomPackageDropDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomPackageDropDownActionPerformed(evt);
            }
        });

        bedTypeDropDown.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        bedTypeDropDown.setOpaque(true);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Bed type");

        checkInDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                checkInDatePropertyChange(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Check-in date");

        checkOutDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                checkOutDatePropertyChange(evt);
            }
        });

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

        bookingDetailsPanel.setBackground(new java.awt.Color(204, 204, 204));
        bookingDetailsPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Selected room");

        roomNumber.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        roomNumber.setText("Room 103");

        roomInfo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        roomInfo.setForeground(new java.awt.Color(51, 51, 51));
        roomInfo.setText("Standard · Queen bed");

        guestNameLbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        guestNameLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        guestNameLbl.setText("Mr. Nilshad");

        guestEmailLbl.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        guestEmailLbl.setForeground(new java.awt.Color(51, 51, 51));
        guestEmailLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        guestEmailLbl.setText("nilshad@gmail.com");

        guestPhoneLbl.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        guestPhoneLbl.setForeground(new java.awt.Color(51, 51, 51));
        guestPhoneLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        guestPhoneLbl.setText("0777123456");

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("Check-in");

        checkInDateInv.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        checkInDateInv.setText("26 Oct 2026");

        checkOutDateInv.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        checkOutDateInv.setText("26 Oct 2026");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("Check-out");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel6.setText("No of Nights");

        noOfNightsInv.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        noOfNightsInv.setText("1");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 153, 153));
        jLabel7.setText("No of Guests");

        noOfGuestsInv.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        noOfGuestsInv.setText("1");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(76, 74, 74));
        jLabel3.setText("Extra guest charges");

        extGuestCharge.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        extGuestCharge.setForeground(new java.awt.Color(76, 74, 74));
        extGuestCharge.setText("Rs. 1000.0  x  2");

        totExtGuestCharge.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        totExtGuestCharge.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totExtGuestCharge.setText("Rs. 2000.0");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(76, 74, 74));
        jLabel4.setText("Discounts");

        discountPercentLbl.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        discountPercentLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        discountPercentLbl.setText("10%");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(76, 74, 74));
        jLabel8.setText("Tax");

        taxPercentLbl.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        taxPercentLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        taxPercentLbl.setText("10%");

        bookingAdvance.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bookingAdvance.setForeground(new java.awt.Color(51, 204, 0));
        bookingAdvance.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        bookingAdvance.setText("Rs. 10000.0");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(76, 74, 74));
        jLabel14.setText("Refundable booking advance");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(76, 74, 74));
        jLabel15.setText("Total");

        totalAmountLbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        totalAmountLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalAmountLbl.setText("Rs. 10000.0");

        totRoomAmount.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        totRoomAmount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totRoomAmount.setText("Rs. 2000.0");

        roomAmount.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        roomAmount.setForeground(new java.awt.Color(76, 74, 74));
        roomAmount.setText("Rs. 1000.0  x  2");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(76, 74, 74));
        jLabel16.setText("Base room charge");

        totDiscountAmt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        totDiscountAmt.setForeground(new java.awt.Color(51, 204, 0));
        totDiscountAmt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totDiscountAmt.setText("Rs. 2000.0");

        totTaxAmt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        totTaxAmt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totTaxAmt.setText("Rs. 2000.0");

        jSeparator4.setForeground(new java.awt.Color(255, 255, 255));

        jSeparator5.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout bookingDetailsPanelLayout = new javax.swing.GroupLayout(bookingDetailsPanel);
        bookingDetailsPanel.setLayout(bookingDetailsPanelLayout);
        bookingDetailsPanelLayout.setHorizontalGroup(
            bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator5)
            .addComponent(jSeparator1)
            .addComponent(jSeparator4)
            .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bookingDetailsPanelLayout.createSequentialGroup()
                        .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(44, 44, 44)
                                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(noOfNightsInv)
                                    .addComponent(checkOutDateInv)
                                    .addComponent(checkInDateInv)))
                            .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(44, 44, 44)
                                .addComponent(noOfGuestsInv)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                        .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(totalAmountLbl))
                            .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(roomNumber)
                                    .addComponent(roomInfo))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(guestNameLbl, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(guestEmailLbl, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(guestPhoneLbl, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bookingAdvance))
                            .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(0, 0, 0)
                                .addComponent(discountPercentLbl)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(totDiscountAmt))
                            .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(0, 0, 0)
                                .addComponent(taxPercentLbl)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(totTaxAmt))
                            .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(38, 38, 38)
                                        .addComponent(extGuestCharge))
                                    .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel16)
                                        .addGap(47, 47, 47)
                                        .addComponent(roomAmount)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(totRoomAmount, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(totExtGuestCharge, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addContainerGap())))
        );
        bookingDetailsPanelLayout.setVerticalGroup(
            bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, 0)
                        .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(roomNumber)
                            .addComponent(guestEmailLbl))
                        .addGap(0, 0, 0)
                        .addComponent(roomInfo))
                    .addGroup(bookingDetailsPanelLayout.createSequentialGroup()
                        .addComponent(guestNameLbl)
                        .addGap(18, 18, 18)
                        .addComponent(guestPhoneLbl)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(checkInDateInv))
                .addGap(0, 0, 0)
                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(checkOutDateInv))
                .addGap(0, 0, 0)
                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(noOfNightsInv))
                .addGap(0, 0, 0)
                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(noOfGuestsInv))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(roomAmount)
                    .addComponent(totRoomAmount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(extGuestCharge)
                    .addComponent(totExtGuestCharge))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(discountPercentLbl)
                    .addComponent(totDiscountAmt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(taxPercentLbl)
                    .addComponent(totTaxAmt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(bookingAdvance))
                .addGap(18, 18, 18)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(bookingDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(totalAmountLbl))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout availableRoomsPanelLayout = new javax.swing.GroupLayout(availableRoomsPanel);
        availableRoomsPanel.setLayout(availableRoomsPanelLayout);
        availableRoomsPanelLayout.setHorizontalGroup(
            availableRoomsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 535, Short.MAX_VALUE)
        );
        availableRoomsPanelLayout.setVerticalGroup(
            availableRoomsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 238, Short.MAX_VALUE)
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3)
                    .addGroup(layout.createSequentialGroup()
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
                                .addGap(16, 16, 16)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel12)
                                        .addComponent(jLabel10)
                                        .addComponent(checkOutDate, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                                        .addComponent(bedTypeDropDown, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(roomListCount)
                            .addComponent(availableRoomsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(confirmBooking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bookingDetailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                .addComponent(availableRoomsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(checkOutDate, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(bookingDetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(confirmBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        onBack.run();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void roomPackageDropDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomPackageDropDownActionPerformed

    }//GEN-LAST:event_roomPackageDropDownActionPerformed

    private void checkInDatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_checkInDatePropertyChange

    }//GEN-LAST:event_checkInDatePropertyChange

    private void checkOutDatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_checkOutDatePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_checkOutDatePropertyChange

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
            bookingDetailsPanel.setVisible(false);
            confirmBooking.setVisible(false);

            List<Room> availableRoomList = BookingManager.getAvailableRoomList(
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

        Booking booking = new Booking(
                selectedRoom,
                GuestManager.getCurrentGuest(),
                checkInDate.getDate(),
                checkOutDate.getDate(),
                HelperFunctions.getCurrentDate(),
                Integer.parseInt(numberOfGuest.getText()),
                Double.parseDouble(advanceAmount.getText().trim()),
                false,
                totalAmount,
                BookingManager.getBookingStatus(0)
        );

        BookingManager.addNewBooking(booking);
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
    private javax.swing.JLabel bookingAdvance;
    private javax.swing.JPanel bookingDetailsPanel;
    private com.toedter.calendar.JDateChooser checkInDate;
    private javax.swing.JLabel checkInDateInv;
    private com.toedter.calendar.JDateChooser checkOutDate;
    private javax.swing.JLabel checkOutDateInv;
    private javax.swing.JButton confirmBooking;
    private javax.swing.JLabel discountPercentLbl;
    private javax.swing.JLabel extGuestCharge;
    private javax.swing.JLabel guestEmailLbl;
    private javax.swing.JLabel guestNameLbl;
    private javax.swing.JLabel guestPhoneLbl;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel noOfGuestsInv;
    private javax.swing.JLabel noOfNightsInv;
    private javax.swing.JButton nogMinus;
    private javax.swing.JButton nogPlus;
    private javax.swing.JLabel numberOfGuest;
    private javax.swing.JLabel roomAmount;
    private javax.swing.JLabel roomInfo;
    private javax.swing.JLabel roomListCount;
    private javax.swing.JLabel roomNumber;
    private javax.swing.JComboBox<String> roomPackageDropDown;
    private javax.swing.JLabel taxPercentLbl;
    private javax.swing.JLabel totDiscountAmt;
    private javax.swing.JLabel totExtGuestCharge;
    private javax.swing.JLabel totRoomAmount;
    private javax.swing.JLabel totTaxAmt;
    private javax.swing.JLabel totalAmountLbl;
    // End of variables declaration//GEN-END:variables
}
