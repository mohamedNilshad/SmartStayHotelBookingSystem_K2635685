/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models.room_packages;

/**
 *
 * @author N I L S H A D
 */
abstract public class RoomPackage {

    protected String packageName;
    protected double dailyRoomRate;
    protected double extraGuestCharge;
    protected double taxRate;

    abstract public String getPackageName();

    abstract public double getDailyRoomRate();

    abstract public double getExtraGuestCharge();

    abstract public double getTaxRate();
    
}
