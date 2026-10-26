/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models;

import main.models.room_packages.RoomPackage;

/**
 *
 * @author N I L S H A D
 */
public class Room {
    private int roomID;
    private RoomPackage roomPackage;
    private String bedType;
    private String roomStatus; //(Available-1, Reserved-0, Under Maintenance-3)
    private boolean breakfast;
    private int capacity;

    public Room(int roomID, RoomPackage roomType, String bedType, String roomStatus, boolean breakfast, int capacity) {
        this.roomID = roomID;
        this.roomPackage = roomType;
        this.bedType = bedType;
        this.roomStatus = roomStatus;
        this.breakfast = breakfast;
        this.capacity = capacity;
    }

    public int getRoomID() { return roomID; }
    public RoomPackage getRoomPackage() { return roomPackage; }
    public String getStatus() { return roomStatus; }
    public boolean getBreakfast() { return breakfast; }
    public int getCapacity() { return capacity; }
    public String getBedType() {return bedType;}
    
    
    public void setStatus(String roomStatus) { this.roomStatus = roomStatus; }
}
