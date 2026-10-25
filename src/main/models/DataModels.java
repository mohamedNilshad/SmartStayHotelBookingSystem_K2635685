/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models;

import java.util.*;

/**
 *
 * @author N I L S H A D
 */
public class DataModels {

    //public static String[] roomTypes = {"Standard", "Deluxe", "Suite", "Family Suite"};
    private static String[] bedTypes = {"Single", "Double", "Queen", "King"};
    private static String[] roomStatus = {"Available", "Reserved", "Under Maintenance"};
    private static List<RoomType> roomTypes = new ArrayList<>();
    private static List<Room> roomList = new ArrayList<>();

    public DataModels() {
        ///set roomTypes
        roomTypes.add(new RoomType("Standard", new String[]{"Single", "Double"}, 6000.00, 1000.00, 10.0));
        roomTypes.add(new RoomType("Deluxe", new String[]{"Single", "Double", "Queen"}, 9000.00, 1500.00, 12.0));
        roomTypes.add(new RoomType("Suite", new String[]{"Single", "Double", "Queen", "King"}, 14000.00, 2000.00, 15.0));
        roomTypes.add(new RoomType("Family Suite", new String[]{"Single", "Double", "Queen", "King"}, 1800.00, 2500.00, 15.0));

        ///set rooms
        roomList.add(new Room(100, roomTypes.get(0), roomStatus[0], false, 1));
        roomList.add(new Room(101, roomTypes.get(0), roomStatus[0], false, 2));

        roomList.add(new Room(102, roomTypes.get(1), roomStatus[1], false, 1));
        roomList.add(new Room(103, roomTypes.get(1), roomStatus[0], true, 2));

        roomList.add(new Room(104, roomTypes.get(2), roomStatus[0], true, 2));
        roomList.add(new Room(105, roomTypes.get(2), roomStatus[1], true, 3));
        roomList.add(new Room(106, roomTypes.get(2), roomStatus[0], true, 3));

        roomList.add(new Room(107, roomTypes.get(3), roomStatus[2], true, 3));
        roomList.add(new Room(108, roomTypes.get(3), roomStatus[0], true, 4));
        roomList.add(new Room(109, roomTypes.get(3), roomStatus[1], true, 4));
        roomList.add(new Room(110, roomTypes.get(3), roomStatus[0], true, 4));

    }

    public static List<RoomType> getRoomTypes() {
        return new ArrayList<>(roomTypes);
    }

    public static List<Room> getRoomList() {
        return new ArrayList<>(roomList);
    }

    public static void addNewRoom(Room room) {
        roomList.add(room);
    }

    public static void addNewRoomType(RoomType roomType) {
        roomTypes.add(roomType);
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
