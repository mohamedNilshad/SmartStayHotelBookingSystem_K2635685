/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.functions;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;

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

    public static long getNoOfDays(Date startDateObj, Date endDateObj) {
        LocalDate startDate = startDateObj.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = endDateObj.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // 3. Calculate the difference in days
        long daysCount = ChronoUnit.DAYS.between(startDate, endDate);

        return daysCount;
    }

    public static Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }
}
