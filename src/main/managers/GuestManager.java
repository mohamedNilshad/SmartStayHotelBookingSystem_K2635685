package main.managers;

import java.util.ArrayList;
import java.util.List;
import main.models.users.K2635685_Guest;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author N I L S H A D
 */
public class GuestManager {

    private static final List<K2635685_Guest> guestList = new ArrayList<>();
    private static K2635685_Guest currentGuest;

    public static boolean isRegistered(String nic) {
        for (K2635685_Guest guest : guestList) {
            if (guest.getNicPassportNumber().equalsIgnoreCase(nic.trim())) {

                setCurrentGuest(guest);
                return true;
            }
        }
        return false;
    }

    public static void registerUser(K2635685_Guest guest) {
        if (!isRegistered(guest.getNicPassportNumber())) {
            guestList.add(guest);
            setCurrentGuest(guest);
        }
    }

    public static void setCurrentGuest(K2635685_Guest guest) {
        currentGuest = guest;
    }

    public static K2635685_Guest getCurrentGuest() {
        return currentGuest;
    }

    public static void clearSession() {
        currentGuest = null;
    }
}
