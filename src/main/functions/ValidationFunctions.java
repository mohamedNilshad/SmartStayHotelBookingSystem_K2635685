/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.functions;

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
}
