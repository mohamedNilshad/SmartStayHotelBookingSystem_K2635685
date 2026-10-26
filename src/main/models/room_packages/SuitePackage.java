/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models.room_packages;

/**
 *
 * @author N I L S H A D
 */
public class SuitePackage extends RoomPackage {

    private final String packageName;
    private final double dailyRoomRate;
    private final double extraGuestCharge;
    private final double taxRate;

    public SuitePackage() {
        this.packageName = "Suite";
        this.dailyRoomRate = 14000.00;
        this.extraGuestCharge = 2000.00;
        this.taxRate = 15.0;
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