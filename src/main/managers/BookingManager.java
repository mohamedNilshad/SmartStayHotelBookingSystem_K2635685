/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.managers;

import java.util.*;
import main.models.Room;
import main.models.room_packages.*;

/**
 *
 * @author N I L S H A D
 */
public class BookingManager {

    private static String[] roomStatus = {"Available", "Reserved", "Under Maintenance"};
    private static List<RoomPackage> roomPackageList = new ArrayList<>();
    private static List<Room> roomList = new ArrayList<>();

    public BookingManager() {
        //initialize RoomPackages
        roomPackageList.add(new StandardPackage());
        roomPackageList.add(new DeluxePackage());
        roomPackageList.add(new SuitePackage());
        roomPackageList.add(new FamilySuitePackage());

        //initialize Rooms
        roomList.add(new Room(100, roomPackageList.get(0), roomStatus[0], false, 1));
        roomList.add(new Room(101, roomPackageList.get(0), roomStatus[0], false, 2));

        roomList.add(new Room(102, roomPackageList.get(1), roomStatus[1], false, 1));
        roomList.add(new Room(103, roomPackageList.get(1), roomStatus[0], true, 2));

        roomList.add(new Room(104, roomPackageList.get(2), roomStatus[0], true, 2));
        roomList.add(new Room(105, roomPackageList.get(2), roomStatus[1], true, 3));
        roomList.add(new Room(106, roomPackageList.get(2), roomStatus[0], true, 3));

        roomList.add(new Room(107, roomPackageList.get(3), roomStatus[2], true, 3));
        roomList.add(new Room(108, roomPackageList.get(3), roomStatus[0], true, 4));
        roomList.add(new Room(109, roomPackageList.get(3), roomStatus[1], true, 4));
        roomList.add(new Room(110, roomPackageList.get(3), roomStatus[0], true, 4));

    }

    public static List<String> getRoomPackageNames() {
        List<String> nameList = new ArrayList<>();

        for (RoomPackage roomPackage : roomPackageList) {
            nameList.add(roomPackage.getPackageName());
        }

        return nameList;
    }
    
    
    public static List<RoomPackage> getRoomPackageList() {
        return new ArrayList<>(roomPackageList);
    }

    public static List<Room> getRoomList() {
        return new ArrayList<>(roomList);
    }

    public static void addNewRoom(Room room) {
        roomList.add(room);
    }

    public static void updateRoomStatus(int roomId, String roomStatus) {

        for (int i = 0; i < roomList.size(); i++) {
            Room room = roomList.get(i);

            if (room.getRoomID() == roomId) {
                room.setStatus(roomStatus);
                roomList.set(i, room);
                break;
            }
        }
    }

}
