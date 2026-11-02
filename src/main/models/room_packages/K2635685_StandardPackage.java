/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models.room_packages;

/**
 *
 * @author N I L S H A D
 */
public class K2635685_StandardPackage extends K2635685_RoomPackage {

    public K2635685_StandardPackage() {
        this.packageName = "Standard";
        this.dailyRoomRate = 6000.00;
        this.extraGuestCharge = 1000.00;
        this.taxRate = 10.0;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public double getDailyRoomRate() {
        return dailyRoomRate;
    }

    @Override
    public double getExtraGuestCharge() {
        return extraGuestCharge;
    }

    @Override
    public double getTaxRate() {
        return taxRate;
    }
}