package com.mich.gwan.rms.models;

public class House extends Client{
    private int houseId;
    private String houseName;
    private String location;
    private String houseType;

    public House() {
    }

    public House(int houseId, String houseName, String location, String houseType) {
        this.houseId = houseId;
        this.houseName = houseName;
        this.location = location;
        this.houseType = houseType;
    }

    public House(int managerId, int clientId, int houseId) {
        super(managerId, clientId);
        this.houseId = houseId;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }
}
