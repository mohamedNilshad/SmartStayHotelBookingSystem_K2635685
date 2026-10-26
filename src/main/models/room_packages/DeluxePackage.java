/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models.room_packages;

/**
 *
 * @author N I L S H A D
 */
public class DeluxePackage extends RoomPackage {

    private final String packageName;
    private final double dailyRoomRate;
    private final double extraGuestCharge;
    private final double taxRate;

    public DeluxePackage() {
        this.packageName = "Deluxe";
        this.dailyRoomRate = 9000.00;
        this.extraGuestCharge = 1500.00;
        this.taxRate = 12.0;
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