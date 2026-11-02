/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.managers;

import java.util.*;
import main.functions.HelperFunctions;
import main.models.K2635685_Booking;
import main.models.K2635685_Invoice;
import main.models.K2635685_Room;

/**
 *
 * @author N I L S H A D
 */
public class K2635685_BookingManager {

    private static final List<String> bookingStatus = new ArrayList<>(Arrays.asList("Active", "Cancelled", "Completed"));
    private static final List<K2635685_Booking> bookingList = new ArrayList<>();

    public static List<K2635685_Booking> getBookingList() {
        return new ArrayList<>(bookingList);
    }

    public static List<K2635685_Booking> getBookingListByGuestId(String id) {
        List<K2635685_Booking> myBookingList = new ArrayList<>();

        for (K2635685_Booking booking : bookingList) {

            if (booking.getGuest().getNicPassportNumber().equals(id)) {
                myBookingList.add(booking);
            }
        }

        return myBookingList;
    }

    public static List<K2635685_Room> getAvailableRoomList(String bedType, String roomPackageName, Date checkInDate, Date checkOutDate, int bookingId) {
        List<K2635685_Room> availableRoomList = new ArrayList<>();

        for (K2635685_Room room : K2635685_RoomManager.getRoomList()) {

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
        for (K2635685_Booking booking : bookingList) {

            if (booking.getRoom().getRoomID() != roomId) {
                continue;       // different room
            }
            if (booking.getBookingID() == bookingId) {
                continue;           // ignore itself (when editing)
            }
            if (!booking.getStatus().equals(bookingStatus.getFirst())) {
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

    public static void addNewBooking(K2635685_Booking booking) {
        bookingList.add(booking);
    }

    public static void updateBooking(K2635685_Booking booking) {

        for (int i = 0; i < bookingList.size(); i++) {
            if (bookingList.get(i).getBookingID() == booking.getBookingID()) {
                bookingList.set(i, booking);
                break;
            }
        }
    }
    
    public static List<K2635685_Booking> completeBooking(int bookingId, double paidAmount) {
        
        for (int i = 0; i < bookingList.size(); i++) {
            if (bookingList.get(i).getBookingID() == bookingId) {
                
                K2635685_Booking tempBooking = bookingList.get(i);
                
                K2635685_Invoice updatedInvoice = tempBooking.getInvoice();
                updatedInvoice.setPaidDate(HelperFunctions.getCurrentDate());
                updatedInvoice.setIsPaid(true);
                
                
                tempBooking.setStatus(bookingStatus.get(2));
                tempBooking.setInvoice(updatedInvoice);
                bookingList.set(i, tempBooking);
                break;
            }
        }
        return new ArrayList<>(bookingList);
    }

    public static void updateBookingStatus(int bookingId, String bookingStatus) {

        for (int i = 0; i < bookingList.size(); i++) {
            K2635685_Booking booking = bookingList.get(i);

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
        return bookingStatus.get(index);
    }

    public static List<K2635685_Booking> setBookingStatus(int index, int bookingId) {
        String guestId = "";
        for (K2635685_Booking booking : bookingList) {
            if (booking.getBookingID() == bookingId) {
                booking.setStatus(bookingStatus.get(index));
                guestId = booking.getGuest().getNicPassportNumber();
                break;
            }
        }

        return getBookingListByGuestId(guestId);
    }

    public static List<String> getBookingStatus() {
        return new ArrayList<>(bookingStatus);
    }
    
    

}
