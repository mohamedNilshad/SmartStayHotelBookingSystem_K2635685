/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.managers;

import java.util.*;
import main.models.Booking;
import main.models.Room;

/**
 *
 * @author N I L S H A D
 */
public class BookingManager {

    private static String[] bookingStatus = {"Active", "Cancelled"};
    private static List<Booking> bookingList = new ArrayList<>();

    public static List<Booking> getBookingList() {
        return new ArrayList<>(bookingList);
    }

    public static List<Room> getAvailableRoomList(String bedType, String roomPackageName, Date checkInDate, Date checkOutDate) {
        List<Room> availableRoomList = new ArrayList<>();

        for (Room room : RoomManager.getRoomList()) {

            if (room.getBedType().equals(bedType) && room.getRoomPackage().getPackageName().equals(roomPackageName)) {//sort rooms by bedType and roomPackageName
                boolean isAvailable = isBookingAvailable(room.getRoomID(), checkInDate, checkOutDate);
                if (isAvailable) {
                    availableRoomList.add(room);
                }
            }
        }

        return availableRoomList;//if everything ok return list
    }

    public static boolean isBookingAvailable(int roomId, Date checkInDate, Date checkOutDate) {
        for (Booking booking : bookingList) {

            if (booking.getRoom().getRoomID() == roomId) {//sort bookings by roomId

                if (isDateBetween(booking.getCheckInDate(), checkInDate, checkOutDate)) {//confirm check-in date not in the dates
                    return false;

                } else if (isDateBetween(booking.getCheckOutDate(), checkInDate, checkOutDate)) {//confirm check-out date not in the dates
                    return false;
                }
            }
        }

        return true;//if room available return true
    }

    private static boolean isDateBetween(Date date, Date startDate, Date endDate) {

        if (date == null || startDate == null || endDate == null) {
            return false;
        }

        return !date.before(startDate) && !date.after(endDate);
    }

    public static void addNewBooking(Booking booking) {
        bookingList.add(booking);
    }

    public static void updateBookingStatus(int bookingId, String bookingStatus) {

        for (int i = 0; i < bookingList.size(); i++) {
            Booking booking = bookingList.get(i);

            if (booking.getBookingID() == bookingId) {
                booking.setStatus(bookingStatus);
                bookingList.set(i, booking);
                break;
            }
        }
    }

    public static int generateBookingId() {
        int size = bookingList.size();
        return size++;
    }

    public static String getBookingStatus(int index) {
        return bookingStatus[index];
    }
    
    

}
