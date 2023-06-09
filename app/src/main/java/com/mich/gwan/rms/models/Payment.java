/*
 * Copyright (c) 2023. Maina Michael.
 */

package com.mich.gwan.rms.models;

public class Payment extends Tenant{
    private String paymentDate;
    private String paidAmount;
    private String unpaidAmount;
    private String garbageFee;
    private String month;
    private String rentInvoice;
    private String garbageInvoice;
    private int paymentId;

    public Payment(){}

    public Payment(int managerId, int clientId, int houseId, int roomId, int tenantId, int paymentId) {
        super(managerId, clientId, houseId, roomId, tenantId);
        this.paymentId = paymentId;
    }

    public Payment(String paymentDate, String paidAmount, String unpaidAmount, String garbageFee, int paymentId) {
        this.paymentDate = paymentDate;
        this.paidAmount = paidAmount;
        this.unpaidAmount = unpaidAmount;
        this.garbageFee = garbageFee;
        this.paymentId = paymentId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getUnpaidAmount() {
        return unpaidAmount;
    }

    public void setUnpaidAmount(String unpaidAmount) {
        this.unpaidAmount = unpaidAmount;
    }

    public String getGarbageFee() {
        return garbageFee;
    }

    public void setGarbageFee(String garbageFee) {
        this.garbageFee = garbageFee;
    }

    public String getRentInvoice() {
        return rentInvoice;
    }

    public void setRentInvoice(String rentInvoice) {
        this.rentInvoice = rentInvoice;
    }

    public String getGarbageInvoice() {
        return garbageInvoice;
    }

    public void setGarbageInvoice(String garbageInvoice) {
        this.garbageInvoice = garbageInvoice;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }
}
