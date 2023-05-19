package com.mich.gwan.rms.models;

public class Tenant extends Room{
    private int tenantId;
    private String tenantFirstName;
    private String tenantSecondName;
    private String tenantLastName;
    private String tenantPhoneNumber;
    private String tenantEmail;

    public Tenant(int managerId, int clientId, int houseId, int roomId, int tenantId) {
        super(managerId, clientId, houseId, roomId);
        this.tenantId = tenantId;
    }

    public Tenant(){}

    public Tenant(int tenantId, String tenantFirstName, String tenantSecondName, String tenantLastName, String tenantPhoneNumber, String tenantEmail) {
        this.tenantId = tenantId;
        this.tenantFirstName = tenantFirstName;
        this.tenantSecondName = tenantSecondName;
        this.tenantLastName = tenantLastName;
        this.tenantPhoneNumber = tenantPhoneNumber;
        this.tenantEmail = tenantEmail;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int clientId) {
        this.tenantId = clientId;
    }

    public String getTenantFirstName() {
        return tenantFirstName;
    }

    public void setTenantFirstName(String tenantFirstName) {
        this.tenantFirstName = tenantFirstName;
    }

    public String getTenantSecondName() {
        return tenantSecondName;
    }

    public void setTenantSecondName(String tenantSecondName) {
        this.tenantSecondName = tenantSecondName;
    }

    public String getTenantLastName() {
        return tenantLastName;
    }

    public void setTenantLastName(String tenantLastName) {
        this.tenantLastName = tenantLastName;
    }

    public String getTenantPhoneNumber() {
        return tenantPhoneNumber;
    }

    public void setTenantPhoneNumber(String tenantPhoneNumber) {
        this.tenantPhoneNumber = tenantPhoneNumber;
    }

    public String getTenantEmail() {
        return tenantEmail;
    }

    public void setTenantEmail(String tenantEmail) {
        this.tenantEmail = tenantEmail;
    }
}
