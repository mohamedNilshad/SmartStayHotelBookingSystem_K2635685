/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models.room_packages;

/**
 *
 * @author N I L S H A D
 */
public class EmptyPackage extends RoomPackage {

    private String packageName;
    private String[] bedTypes;
    private double dailyRoomRate;
    private double extraGuestCharge;
    private double taxRate;
    

    public EmptyPackage() {}

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public String[] getBedTypes() {
        return bedTypes;
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

    @Override
    public String getBedTypesInString() {
        String bedType = "";

        for (String type : bedTypes) {
            if (!bedType.isEmpty()) {
                bedType += " | ";
            }

            bedType += type;
        }

        return bedType;
    }
}
