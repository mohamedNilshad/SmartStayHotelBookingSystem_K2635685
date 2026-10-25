package main;

import java.util.ArrayList;
import java.util.List;
import main.models.Guest;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author N I L S H A D
 */
public class GuestManager {

    private static final List<Guest> guestList = new ArrayList<>();
    private static Guest currentGuest;

    public static boolean isRegistered(String nic) {
        for (Guest guest : guestList) {
            if (guest.getNicPassportNumber().equalsIgnoreCase(nic.trim())) {

                setCurrentGuest(guest);
                return true;
            }
        }
        return false;
    }

    public static void registerUser(Guest guest) {
        if (!isRegistered(guest.getNicPassportNumber())) {
            guestList.add(guest);
            setCurrentGuest(guest);
        }
    }

    public static void setCurrentGuest(Guest guest) {
        currentGuest = guest;
    }

    public static Guest getCurrentGuest() {
        return currentGuest;
    }

    public static void clearSession() {
        currentGuest = null;
    }
}
