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

/**
 *
 * @author N I L S H A D
 */
public class HelperFunctions {

    public static void showPopMessage(Component parentComponent, String msg) {
        javax.swing.JOptionPane.showMessageDialog(parentComponent, msg);
    }

    public static boolean validateCheckInDate(Date selectedDate) {

        Calendar today = Calendar.getInstance();

        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        Calendar minimumDate = (Calendar) today.clone();
        minimumDate.add(Calendar.DAY_OF_MONTH, 2);

        Calendar selectedCalendar = Calendar.getInstance();
        selectedCalendar.setTime(selectedDate);

        selectedCalendar.set(Calendar.HOUR_OF_DAY, 0);
        selectedCalendar.set(Calendar.MINUTE, 0);
        selectedCalendar.set(Calendar.SECOND, 0);
        selectedCalendar.set(Calendar.MILLISECOND, 0);

        return (selectedCalendar.before(minimumDate));
    }

    public static boolean validateCheckOutDate(Date checkOutDate, Date checkInDate) {

        Calendar checkInCalendar = Calendar.getInstance();
        checkInCalendar.setTime(checkInDate);

        checkInCalendar.set(Calendar.HOUR_OF_DAY, 0);
        checkInCalendar.set(Calendar.MINUTE, 0);
        checkInCalendar.set(Calendar.SECOND, 0);
        checkInCalendar.set(Calendar.MILLISECOND, 0);

        checkInCalendar.add(Calendar.DAY_OF_MONTH, 1);

        Calendar checkOutCalendar = Calendar.getInstance();
        checkOutCalendar.setTime(checkOutDate);

        checkOutCalendar.set(Calendar.HOUR_OF_DAY, 0);
        checkOutCalendar.set(Calendar.MINUTE, 0);
        checkOutCalendar.set(Calendar.SECOND, 0);
        checkOutCalendar.set(Calendar.MILLISECOND, 0);

        return checkOutCalendar.before(checkInCalendar);
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
