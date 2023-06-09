/*
 * Copyright (c) 2023. Maina Michael.
 */

package com.mich.gwan.rms.models;

public class Client extends Manager{
    private String firstName;
    private String secondName;
    private String thirdName;
    private String location;
    private String phoneNumber;
    private int clientId;

    public Client() {
    }

    public Client(String firstName, String secondName, String thirdName, String location, String phoneNumber) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.location = location;
        this.phoneNumber = phoneNumber;
    }

    public Client(int managerId, int clientId) {
        super(managerId);
        this.clientId = clientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
