/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.components;

import java.awt.*;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import main.functions.HelperFunctions;
import main.models.Guest;
import main.models.Invoice;
import main.models.Room;

/**
 *
 * @author N I L S H A D
 */
public class InvoiceComponent {

    private static final Color MUTED = new Color(95, 110, 130);
    private static final Color BORDER = new Color(215, 222, 230);
    private static final Color ACCENT = new Color(37, 99, 235);
    private static final Color GREEN = new Color(22, 101, 52);
    private static final Color RED = new Color(185, 28, 28);

    public static JPanel createInvoicePanel(
            Invoice invoice,
            String invoiceNo,
            Date invoiceDate,
            Room room,
            Guest guest,
            Date checkIn,
            Date checkOut,
            int noOfGuest
    ) {
        long nights = HelperFunctions.getNoOfNights(checkIn, checkOut);
        int extGuestCount = Math.max(0, noOfGuest - room.getCapacity());

        double dailyRate = room.getRoomPackage().getDailyRoomRate();
        double extraCharge = room.getRoomPackage().getExtraGuestCharge();

        double roomAmount = dailyRate * nights;
        double extAmount = extGuestCount * extraCharge * nights;
        double subtotal = roomAmount + extAmount;
        double totalBeforeAdvance = subtotal - invoice.getDiscountAmount() + invoice.getTaxAmount();

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                new EmptyBorder(20, 24, 20, 24)
        ));

        // ---------- Header ----------
        card.add(buildHeader(invoiceNo, invoiceDate));
        card.add(Box.createVerticalStrut(16));
        card.add(divider());
        card.add(Box.createVerticalStrut(16));

        // ---------- Billed to / Stay ----------
        card.add(buildInfoSection(room, guest, checkIn, checkOut, nights, noOfGuest));
        card.add(Box.createVerticalStrut(16));
        card.add(divider());
        card.add(Box.createVerticalStrut(12));

        // ---------- Line items ----------
        card.add(sectionTitle("CHARGES"));
        card.add(Box.createVerticalStrut(8));

        card.add(line("Room charge",
                money(dailyRate) + "  ×  " + nights + (nights == 1 ? " night" : " nights"),
                money(roomAmount), false, null));

        if (extGuestCount > 0) {
            card.add(Box.createVerticalStrut(8));
            card.add(line("Extra guest charge",
                    money(extraCharge) + "  ×  " + extGuestCount + " guest(s)  ×  " + nights + " nights",
                    money(extAmount), false, null));
        }

        card.add(Box.createVerticalStrut(12));
        card.add(divider());
        card.add(Box.createVerticalStrut(12));

        // ---------- Totals ----------
        card.add(line("Subtotal", null, money(subtotal), true, null));

        if (invoice.getDiscountAmount() > 0) {
            card.add(Box.createVerticalStrut(8));
            card.add(line("Discount", "(" + invoice.getDiscountRate() + " %)",
                    "-" + money(invoice.getDiscountAmount()), false, GREEN));
        }

        card.add(Box.createVerticalStrut(8));
        card.add(line("Tax", "(" + invoice.getTaxRate() + " %)",
                money(invoice.getTaxAmount()), false, null));

        card.add(Box.createVerticalStrut(12));
        card.add(divider());
        card.add(Box.createVerticalStrut(12));

        card.add(line("Total before advance", null, money(totalBeforeAdvance), true, null));
        card.add(Box.createVerticalStrut(8));
        card.add(line("Booking advance", null,
                "-" + money(invoice.getBookingAdvance()), false, RED));

        card.add(Box.createVerticalStrut(16));
        card.add(buildTotalBand(invoice.getTotalAmount()));

        int invoiceWidth = 420; // adjust to fit your panel
        card.setPreferredSize(new Dimension(invoiceWidth, card.getPreferredSize().height));
        card.setMaximumSize(new Dimension(invoiceWidth, Integer.MAX_VALUE));
        return card;
    }

    // ---------- Header: title + invoice no (left), date (right) ----------
    private static JPanel buildHeader(String invoiceNo, Date invoiceDate) {
        JLabel title = new JLabel("INVOICE");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(ACCENT);

        JLabel noLbl = new JLabel("No: " + (invoiceNo == null || invoiceNo.isEmpty() ? "Pending" : invoiceNo));
        noLbl.setFont(new Font("Arial", Font.PLAIN, 13));
        noLbl.setForeground(MUTED);

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setOpaque(false);
        left.add(title);
        left.add(Box.createVerticalStrut(2));
        left.add(noLbl);

        JLabel dateCaption = new JLabel("Date issued");
        dateCaption.setFont(new Font("Arial", Font.PLAIN, 11));
        dateCaption.setForeground(Color.GRAY);
        dateCaption.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel dateLbl = new JLabel(HelperFunctions.formatDate(invoiceDate != null ? invoiceDate : new Date()));
        dateLbl.setFont(new Font("Arial", Font.BOLD, 14));
        dateLbl.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setOpaque(false);
        right.add(dateCaption);
        right.add(Box.createVerticalStrut(2));
        right.add(dateLbl);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.add(left, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        return header;
    }

    // ---------- Two columns: billed to | stay ----------
    private static JPanel buildInfoSection(Room room, Guest guest, Date checkIn, Date checkOut,
            long nights, int noOfGuest) {

        String[] stay = {
            "Room " + room.getRoomIDString() + "  •  " + room.getRoomPackage().getPackageName(),
            "🛏 " + room.getBedType() + "  |  👥 x " + noOfGuest,
            "📅 " + HelperFunctions.formatDate(checkIn) + "  →  " + HelperFunctions.formatDate(checkOut),
            "🌙 " + nights + (nights == 1 ? " night" : " nights")
        };
        JPanel stayCol = column("STAY DETAILS", stay, 0);      // left, no divider

        JPanel billed = column("BILLED TO",
                new String[]{guest.getFullName(), "✉ " + guest.getEmailAddress(), "📞 " + guest.getContactNumber()},
                1);                                            // right, divider on its left edge

        JPanel info = new JPanel(new GridLayout(1, 2, 20, 0));
        info.setOpaque(false);
        info.setAlignmentX(Component.LEFT_ALIGNMENT);
        info.add(stayCol);
        info.add(billed);

        // Width 0 so this section never widens the card. It stretches to the card width instead.
        info.setPreferredSize(new Dimension(0, 110));
        info.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        return info;
    }

    private static JPanel column(String title, String[] lines, int index) {
        JPanel col = new JPanel();
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setOpaque(false);

        col.add(sectionTitle(title));
        col.add(Box.createVerticalStrut(6));

        for (int i = 0; i < lines.length; i++) {
            JLabel l = new JLabel(lines[i]);
            l.setFont(new Font("Segoe UI Emoji", i == 0 ? Font.BOLD : Font.PLAIN, i == 0 ? 14 : 13));
            l.setForeground(i == 0 ? Color.BLACK : MUTED);
            l.setAlignmentX(Component.LEFT_ALIGNMENT);
            col.add(l);
            col.add(Box.createVerticalStrut(3));
        }

        if (index == 1) { // left divider on the second column
            col.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(225, 230, 236)),
                    new EmptyBorder(0, 16, 0, 0)));
        }
        return col;
    }

    // ---------- One row: label (+ muted detail) on the left, amount on the right ----------
    private static JPanel line(String label, String detail, String amount, boolean bold, Color amountColor) {
        JLabel labelLbl = new JLabel(label);
        labelLbl.setFont(new Font("Arial", bold ? Font.BOLD : Font.PLAIN, 14));
        labelLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setOpaque(false);
        left.add(labelLbl);

        if (detail != null) {
            JLabel detailLbl = new JLabel(detail);
            detailLbl.setFont(new Font("Arial", Font.PLAIN, 12));
            detailLbl.setForeground(MUTED);
            detailLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            left.add(Box.createVerticalStrut(2));
            left.add(detailLbl);
        }

        JLabel amountLbl = new JLabel(amount);
        amountLbl.setFont(new Font("Arial", bold ? Font.BOLD : Font.PLAIN, 14));
        if (amountColor != null) {
            amountLbl.setForeground(amountColor);
        }

        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.add(left, BorderLayout.CENTER);
        row.add(amountLbl, BorderLayout.EAST);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, detail != null ? 40 : 24));
        return row;
    }

    // ---------- Highlighted final amount ----------
    private static JPanel buildTotalBand(double totalAmount) {
        JLabel caption = new JLabel("Total payable");
        caption.setFont(new Font("Arial", Font.BOLD, 15));
        caption.setForeground(ACCENT);

        JLabel amount = new JLabel(money(totalAmount));
        amount.setFont(new Font("Arial", Font.BOLD, 20));
        amount.setForeground(ACCENT);

        JPanel band = new JPanel(new BorderLayout());
        band.setBackground(new Color(239, 246, 255));
        band.setBorder(new EmptyBorder(12, 16, 12, 16));
        band.setAlignmentX(Component.LEFT_ALIGNMENT);
        band.add(caption, BorderLayout.WEST);
        band.add(amount, BorderLayout.EAST);
        band.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        return band;
    }

    // ---------- Small helpers ----------
    private static JLabel sectionTitle(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Arial", Font.BOLD, 11));
        l.setForeground(Color.GRAY);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private static JComponent divider() {
        JPanel d = new JPanel();
        d.setBackground(BORDER);
        d.setPreferredSize(new Dimension(1, 1));
        d.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        d.setAlignmentX(Component.LEFT_ALIGNMENT);
        return d;
    }

    private static String money(double value) {
        return String.format("Rs. %,.2f", value);
    }
    
    
    
}
