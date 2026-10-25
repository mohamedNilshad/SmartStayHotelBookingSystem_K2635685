/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models.room_packages;

/**
 *
 * @author N I L S H A D
 */
public class FamilySuitePackage extends RoomPackage {

    private final String packageName;
    private final String[] bedTypes;
    private final double dailyRoomRate;
    private final double extraGuestCharge;
    private final double taxRate;

    public FamilySuitePackage() {
        this.packageName = "Family Suite";
        this.dailyRoomRate = 18000.00;
        this.bedTypes = new String[]{"Single", "Double", "Queen", "King"};
        this.extraGuestCharge = 2500.00;
        this.taxRate = 15.0;
    }

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