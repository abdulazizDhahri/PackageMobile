package com.mhdhussein.aralpackagemanagement.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShipmentEntry {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("courier_id")
    @Expose
    private Integer courierId;
    @SerializedName("courier_number")
    @Expose
    private String courierNumber;
    @SerializedName("courier")
    @Expose
    private String courier;
    @SerializedName("supervisor")
    @Expose
    private String supervisor;
    @SerializedName("total_sent")
    @Expose
    private String totalSent;
    @SerializedName("amount_sent")
    @Expose
    private String amountSent;
    @SerializedName("total_cash")
    @Expose
    private String totalCash;
    @SerializedName("total_cash_packages")
    @Expose
    private String totalCashPackages;
    @SerializedName("total_credit")
    @Expose
    private String totalCredit;
    @SerializedName("total_credit_packages")
    @Expose
    private String totalCreditPackages;
    @SerializedName("deposited")
    @Expose
    private String deposited;
    @SerializedName("returned")
    @Expose
    private String returned;
    @SerializedName("delivered")
    @Expose
    private String delivered;
    @SerializedName("deficit")
    @Expose
    private String deficit;
    @SerializedName("lost")
    @Expose
    private String lost;
    @SerializedName("fuel")
    @Expose
    private String fuel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourierId() {
        return courierId;
    }

    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }

    public String getCourierNumber() {
        return courierNumber;
    }

    public void setCourierNumber(String courierNumber) {
        this.courierNumber = courierNumber;
    }

    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getTotalSent() {
        return totalSent;
    }

    public void setTotalSent(String totalSent) {
        this.totalSent = totalSent;
    }

    public String getAmountSent() {
        return amountSent;
    }

    public void setAmountSent(String amountSent) {
        this.amountSent = amountSent;
    }

    public String getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(String totalCash) {
        this.totalCash = totalCash;
    }

    public String getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(String totalCredit) {
        this.totalCredit = totalCredit;
    }

    public String getDeposited() {
        return deposited;
    }

    public void setDeposited(String deposited) {
        this.deposited = deposited;
    }

    public String getReturned() {
        return returned;
    }

    public void setReturned(String returned) {
        this.returned = returned;
    }

    public String getDelivered() {
        return delivered;
    }

    public void setDelivered(String delivered) {
        this.delivered = delivered;
    }

    public String getDeficit() {
        return deficit;
    }

    public void setDeficit(String deficit) {
        this.deficit = deficit;
    }

    public String getLost() {
        return lost;
    }

    public void setLost(String lost) {
        this.lost = lost;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getTotalCashPackages() {
        return totalCashPackages;
    }

    public void setTotalCashPackages(String totalCashPackages) {
        this.totalCashPackages = totalCashPackages;
    }

    public String getTotalCreditPackages() {
        return totalCreditPackages;
    }

    public void setTotalCreditPackages(String totalCreditPackages) {
        this.totalCreditPackages = totalCreditPackages;
    }
}
