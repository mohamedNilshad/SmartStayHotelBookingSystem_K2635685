/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.managers;

import java.util.ArrayList;
import java.util.List;
import main.models.K2635685_Booking;
import main.models.K2635685_Room;
import main.models.room_packages.K2635685_DeluxePackage;
import main.models.room_packages.K2635685_FamilySuitePackage;
import main.models.room_packages.K2635685_RoomPackage;
import main.models.room_packages.K2635685_StandardPackage;
import main.models.room_packages.K2635685_SuitePackage;

/**
 *
 * @author N I L S H A D
 */
public class RoomManager {

    private static String[] bedTypes = {"Single", "Double", "Queen", "King"};
    private static String[] roomStatus = {"Available", "Reserved", "Under Maintenance"};
    private static List<K2635685_RoomPackage> roomPackageList = new ArrayList<>();
    private static List<K2635685_Room> roomList = new ArrayList<>();

    public RoomManager() {
        //initialize RoomPackages
        roomPackageList.add(new K2635685_StandardPackage());
        roomPackageList.add(new K2635685_DeluxePackage());
        roomPackageList.add(new K2635685_SuitePackage());
        roomPackageList.add(new K2635685_FamilySuitePackage());

        //initialize Rooms
        roomList.add(new K2635685_Room(generateRoomId(), roomPackageList.get(0), bedTypes[0], roomStatus[0], false, 1, 12));
        roomList.add(new K2635685_Room(generateRoomId(), roomPackageList.get(0), bedTypes[0], roomStatus[0], true, 1, 12));
        roomList.add(new K2635685_Room(generateRoomId(), roomPackageList.get(0), bedTypes[1], roomStatus[0], false, 2, 12));

        roomList.add(new K2635685_Room(generateRoomId(), roomPackageList.get(1), bedTypes[0], roomStatus[1], false, 1, 12));
        roomList.add(new K2635685_Room(generateRoomId(), roomPackageList.get(1), bedTypes[1], roomStatus[0], true, 2, 12));

        roomList.add(new K2635685_Room(generateRoomId(), roomPackageList.get(2), bedTypes[0], roomStatus[0], true, 2, 12));
        roomList.add(new K2635685_Room(generateRoomId(), roomPackageList.get(2), bedTypes[1], roomStatus[1], true, 3, 12));
        roomList.add(new K2635685_Room(generateRoomId(), roomPackageList.get(2), bedTypes[2], roomStatus[0], true, 3, 12));

        roomList.add(new K2635685_Room(generateRoomId(), roomPackageList.get(3), bedTypes[0], roomStatus[2], true, 3, 12));
        roomList.add(new K2635685_Room(generateRoomId(), roomPackageList.get(3), bedTypes[1], roomStatus[0], true, 4, 12));
        roomList.add(new K2635685_Room(generateRoomId(), roomPackageList.get(3), bedTypes[2], roomStatus[1], true, 4, 12));
        roomList.add(new K2635685_Room(generateRoomId(), roomPackageList.get(3), bedTypes[3], roomStatus[0], true, 4, 12));

    }
    
    public static int generateRoomId(){
        int count = 100;
        if(!roomList.isEmpty()){
            count = roomList.getLast().getRoomID() + 1;
        }
        return count;
    }

    public static List<String> getRoomPackageNames() {
        List<String> nameList = new ArrayList<>();

        for (K2635685_RoomPackage roomPackage : roomPackageList) {
            nameList.add(roomPackage.getPackageName());
        }

        return nameList;
    }

    public static List<K2635685_RoomPackage> getRoomPackageList() {
        return new ArrayList<>(roomPackageList);
    }
    
    public static K2635685_RoomPackage getRoomPackageByName(String value) {
        for(K2635685_RoomPackage roomPackage : roomPackageList){
            if(roomPackage.getPackageName().equals(value)){
                return roomPackage;
            }
        }
        return null;
    }

    public static List<K2635685_Room> getRoomList() {
        return new ArrayList<>(roomList);
    }

    public static String[] getBedTypes() {
        return bedTypes;
    }

    public static void addNewRoom(K2635685_Room room) {
        roomList.add(room);
    }
    
    public static void updateRoom(K2635685_Room room) {
        roomList.replaceAll(r -> r.getRoomID() == room.getRoomID() ? room : r);
    }

    public static void updateRoomStatus(int roomId, String roomStatus) {

        for (int i = 0; i < roomList.size(); i++) {
            K2635685_Room room = roomList.get(i);

            if (room.getRoomID() == roomId) {
                room.setStatus(roomStatus);
                roomList.set(i, room);
                break;
            }
        }
    }
    
    public static int deleteRoom(int roomId){
        
        if(roomHasActiveBookings(roomId)){
            return -1;
        }
        
        for (int i = 0; i < roomList.size(); i++) {
            K2635685_Room room = roomList.get(i);
            if (room.getRoomID() == roomId ) {
                roomList.remove(i);
                return 1;
            }
        }
        return 0;
    }

    private static boolean roomHasActiveBookings(int roomId) {
        for (K2635685_Booking b : BookingManager.getBookingList()) {
            if (b.getRoom().getRoomID() == roomId && BookingManager.getBookingStatus(0).equals(b.getStatus())) {
                return true;
            }
        }
        return false;
    }

    public static String[] getRoomStatus() {
        return roomStatus;
    }
    
    

}
