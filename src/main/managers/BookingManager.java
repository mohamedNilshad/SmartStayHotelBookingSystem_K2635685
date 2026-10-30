/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.managers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import main.models.Booking;
import main.models.Guest;
import main.models.Invoice;
import main.models.Room;

/**
 *
 * @author N I L S H A D
 */
public class BookingManager {

//    private static String[] bookingStatus = {"Active", "Cancelled", "Completed"};
    private static List<String> bookingStatus = new ArrayList<>(Arrays.asList("Active", "Cancelled", "Completed"));
    private static List<Booking> bookingList = new ArrayList<>();

    //Temp
    public BookingManager() {
        List<Room> rooms = RoomManager.getRoomList();
        List<Guest> guests = new ArrayList<>();
        List<Invoice> invoices = new ArrayList<>();

        guests.add(new Guest("199812345678", "0771234567", "Kamal Perera", "kamal@gmail.com"));
        guests.add(new Guest("199812345677", "0771234565", "Vimal", "vimal@gmail.com"));
        guests.add(new Guest("199812345676", "0772534565", "Ravi", "ravi@gmail.com"));
        guests.add(new Guest("199812345675", "0775534567", "Perera", "perera@gmail.com"));
        
        

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            invoices.add(new Invoice(sdf.parse("2026-10-28"), 52752.00, 47482.00, 10000.00, false, null, rooms.get(0).getRoomPackage().getTaxRate(), 5270.0, rooms.get(0).getDiscountRate(), 0));
            invoices.add(new Invoice(sdf.parse("2026-10-26"), 32000.00, 28800.00, 10000.00, false, null, rooms.get(1).getRoomPackage().getTaxRate(), 3200.0, rooms.get(1).getDiscountRate(), 0));
            invoices.add(new Invoice(sdf.parse("2026-10-27"), 77320.00, 69588.00, 10000.00, true, sdf.parse("2026-11-21"), rooms.get(2).getRoomPackage().getTaxRate(), 7732.0, rooms.get(2).getDiscountRate(), 0));
            invoices.add(new Invoice(sdf.parse("2026-10-25"), 81600.00, 73440.00, 10000.00, false, null, rooms.get(3).getRoomPackage().getTaxRate(), 8160.0, rooms.get(3).getDiscountRate(), 0));
            
            
            bookingList.add(new Booking(rooms.get(0), guests.get(0), sdf.parse("2026-11-05"), sdf.parse("2026-11-10"), sdf.parse("2026-10-28"), 3, invoices.get(0), "Active"));
            bookingList.add(new Booking(rooms.get(1), guests.get(1), sdf.parse("2026-11-02"), sdf.parse("2026-11-04"), sdf.parse("2026-10-26"), 1, invoices.get(1), "Active"));
            bookingList.add(new Booking(rooms.get(2), guests.get(2), sdf.parse("2026-11-15"), sdf.parse("2026-11-21"), sdf.parse("2026-10-27"), 2, invoices.get(2), "Completed"));
            bookingList.add(new Booking(rooms.get(2), guests.get(2), sdf.parse("2026-11-08"), sdf.parse("2026-11-12"), sdf.parse("2026-10-25"), 5, invoices.get(3), "Cancelled"));

        } catch (ParseException e) {
        }

    }

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
        return bookingStatus.get(index);
    }

    public static List<Booking> setBookingStatus(int index, int bookingId) {

        for (Booking booking : bookingList) {
            if (booking.getBookingID() == bookingId) {
                booking.setStatus(bookingStatus.get(index));
                break;
            }
        }

        return getBookingList();
    }

    public static List<String> getBookingStatus() {
        return new ArrayList<>(bookingStatus);
    }
    
    

}
