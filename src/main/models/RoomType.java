/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models;

/**
 *
 * @author N I L S H A D
 */
public class RoomType {

    private String name;
    private String[] bedTypes;
    private double dailyRoomRate;
    private double extraGuestCharge;
    private double taxRate;

    public RoomType(String name, String[] bedTypes, double dailyRoomRate, double extraGuestCharge, double taxRate) {
        this.name = name;
        this.dailyRoomRate = dailyRoomRate;
        this.bedTypes = bedTypes;
        this.extraGuestCharge = extraGuestCharge;
        this.taxRate = taxRate;
    }
    
    public RoomType() {}

    public String getName() {
        return name;
    }

    public String[] getBedTypes() {
        return bedTypes;
    }

    public double getDailyRoomRate() {
        return dailyRoomRate;
    }

    public double getExtraGuestCharge() {
        return extraGuestCharge;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public String getBedTypesInString() {
        String bedType = "";

        for (String type : bedTypes) {
            if(!bedType.isEmpty()){
             bedType += " | ";
            }
            
            bedType += type;
        }

        return bedType;
    }
    
    public RoomType getEmpty() {
        return new RoomType();
    }

}
