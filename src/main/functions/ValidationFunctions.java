/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.functions;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 *
 * @author N I L S H A D
 */
public class ValidationFunctions {

    public static boolean validateNIC(String nic) {
        if (nic == null) {
            return false;
        }
        final Pattern NIC_12_REGEX = Pattern.compile("^\\d{12}$");
        final Pattern NIC_10_REGEX = Pattern.compile("^\\d{9}[vV]$");
        String cleanNic = nic.trim();
        return NIC_12_REGEX.matcher(cleanNic).matches() || NIC_10_REGEX.matcher(cleanNic).matches();
    }

    public static boolean validatePassport(String passport) {
        if (passport == null) {
            return false;
        }
        final Pattern PASSPORT_REGEX = Pattern.compile("^[A-Za-z0-9]{6,9}$");
        return PASSPORT_REGEX.matcher(passport.trim()).matches();
    }

    public static boolean validateEmail(String email) {
        if (email == null) {
            return false;
        }

        final Pattern EMAIL_REGEX = Pattern.compile(
                "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        );
        return EMAIL_REGEX.matcher(email.trim()).matches();
    }

    public static boolean validatePhone(String phone) {
        if (phone == null) {
            return false;
        }

        // Standard robust email validation pattern
        // General phone number pattern (supports optional '+' and 10 to 15 digits)
        final Pattern PHONE_REGEX = Pattern.compile(
                "^\\+?[0-9]{10,15}$"
        );
        // Remove spaces, hyphens, and brackets from the input string
        String cleanPhone = phone.replaceAll("[\\s\\-\\(\\)]", "");
        return PHONE_REGEX.matcher(cleanPhone).matches();
    }

    public static boolean validateDoubleValue(Component parentComponent, String value) {

        if (value.isEmpty()) {
            HelperFunctions.showPopMessage(parentComponent, "Please enter a amount.", "");
            return false;
        }

        try {
            double number = Double.parseDouble(value);

            if (!Double.isFinite(number)) {
                throw new NumberFormatException();
            }

            return true;

        } catch (NumberFormatException e) {
            HelperFunctions.showPopMessage(parentComponent, "Please enter a valid amount.", "");
            return false;
        }
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

    public static boolean isSameDate(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(date1).equals(sdf.format(date2));
    }
}
