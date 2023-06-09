/*
 * Copyright (c) 2023. Maina Michael.
 */

package com.mich.gwan.rms.models;

public class Manager {
    private String firstName;
    private String secondName;
    private String lastName;
    private String phoneNumber;
    private String location;
    private String email;
    private String password;
    private int managerId;

    public Manager() {
    }

    public Manager(String firstName, String secondName, String lastName, String phoneNumber, String location, String email, int id) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.email = email;
        this.managerId = id;
    }

    public Manager(int managerId){
        this.managerId = managerId;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }
}
