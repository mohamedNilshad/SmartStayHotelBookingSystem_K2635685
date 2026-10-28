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

    private static String[] bookingStatus = {"Active", "Cancelled", "Completed"};
    private static List<Booking> bookingList = new ArrayList<>();

    public static List<Booking> getBookingList() {
        return new ArrayList<>(bookingList);
    }

    public static List<Booking> getBookingListByGuestId(String id) {
        List<Booking> myBookingList = new ArrayList<>();

        for (Booking booking : bookingList) {

            if (booking.getGuest().getNicPassportNumber().equals(id)) {
                myBookingList.add(booking);
            }
        }

        return myBookingList;
    }

    public static List<Room> getAvailableRoomList(String bedType, String roomPackageName, Date checkInDate, Date checkOutDate, int bookingId) {
        List<Room> availableRoomList = new ArrayList<>();

        for (Room room : RoomManager.getRoomList()) {

            if (room.getBedType().equals(bedType) && room.getRoomPackage().getPackageName().equals(roomPackageName)) {//sort rooms by bedType and roomPackageName
                boolean isAvailable = isBookingAvailable(room.getRoomID(), checkInDate, checkOutDate, bookingId);
                if (isAvailable) {
                    availableRoomList.add(room);
                }
            }
        }

        return availableRoomList;//if everything ok return list
    }

    public static boolean isBookingAvailable(int roomId, Date checkInDate, Date checkOutDate, int bookingId) {
        for (Booking booking : bookingList) {

            if (booking.getRoom().getRoomID() != roomId) {
                continue;       // different room
            }
            if (booking.getBookingID() == bookingId) {
                continue;           // ignore itself (when editing)
            }
            if (!booking.getStatus().equals(bookingStatus[0])) {
                continue; // only "Active" bookings block
            }
            if (isOverlapping(checkInDate, checkOutDate,
                    booking.getCheckInDate(), booking.getCheckOutDate())) {
                return false;
            }
        }
        return true;
    }

    private static boolean isOverlapping(Date newIn, Date newOut, Date existingIn, Date existingOut) {
        return newIn.before(existingOut) && newOut.after(existingIn);
    }

    public static void addNewBooking(Booking booking) {
        bookingList.add(booking);
    }

    public static void updateBooking(Booking booking) {

        for (int i = 0; i < bookingList.size(); i++) {
            if (bookingList.get(i).getBookingID() == booking.getBookingID()) {
                bookingList.set(i, booking);
                break;
            }
        }
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
        return size += 1;
    }

    public static String getBookingStatus(int index) {
        return bookingStatus[index];
    }

    public static List<Booking> setBookingStatus(int index, int bookingId) {

        for (Booking booking : bookingList) {
            if (booking.getBookingID() == bookingId) {
                booking.setStatus(bookingStatus[index]);
                break;
            }
        }

        return getBookingList();
    }

}
