package com.mich.gwan.rms.models;

public class Room extends House{
    private int roomId;
    private String roomType;
    private String deposit;
    private String rent;
    private String Status;

    public Room(){}

    public Room(int roomId, String roomType, String deposit, String rent, String status) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.deposit = deposit;
        this.rent = rent;
        Status = status;
    }

    public Room(int managerId, int clientId, int houseId, int roomId) {
        super(managerId, clientId, houseId);
        this.roomId = roomId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
