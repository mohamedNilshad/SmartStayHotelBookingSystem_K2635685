/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.functions;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author N I L S H A D
 */
public class HelperFunctions {

    public static void showPopMessage(Component parentComponent, String title, String body) {
        showMessageDialog(parentComponent, title, body, JOptionPane.INFORMATION_MESSAGE);
    }

    public static int showConfirmDialog(String title, String subtitle) {
        return JOptionPane.showConfirmDialog(
                null,
                title,
                subtitle,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
    }

    public static Date getDefaultCheckInDate() {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_YEAR, 2);

        return calendar.getTime();
    }

    public static Date getDefaultCheckOutDate(Date checkInDate) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkInDate);

        calendar.add(Calendar.DAY_OF_MONTH, 1);

        return calendar.getTime();
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        return sdf.format(date);
    }

    public static boolean isOneDayBefore(Date givenDate) {

        LocalDate given = givenDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        return given.equals(LocalDate.now().plusDays(1));
    }

    public static long getNoOfNights(Date startDateObj, Date endDateObj) {
        LocalDate startDate = startDateObj.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = endDateObj.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Calculate the difference in days
        long nightsCount = ChronoUnit.DAYS.between(startDate, endDate);

        return nightsCount;
    }

    public static Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static JLabel createStatusBadge(String status) {
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

    public static JButton createActionButton(String text, Color color, boolean enabled) {
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

    public static JLabel createAvatar(String name) {
        String initials = getInitials(name);

        JLabel avatar = new JLabel(initials, SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        avatar.setOpaque(false);
        avatar.setBackground(new Color(219, 234, 254));
        avatar.setForeground(new Color(30, 64, 175));
        avatar.setFont(new Font("Arial", Font.BOLD, 14));
        avatar.setPreferredSize(new Dimension(40, 40));
        return avatar;
    }

    private static String getInitials(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "?";
        }
        String[] parts = name.trim().split("\\s+");
        String first = parts[0].substring(0, 1);
        String last = parts.length > 1 ? parts[parts.length - 1].substring(0, 1) : "";
        return (first + last).toUpperCase();
    }
    
    public static String formatMoney(double value) {
        return String.format("Rs. %,.2f", value);
    }
}
