package com.mhdhussein.aralpackagemanagement.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainReport {

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
    @SerializedName("total_credit")
    @Expose
    private String totalCredit;
    @SerializedName("deposited")
    @Expose
    private String deposited;
    @SerializedName("returned")
    @Expose
    private String returned;
    @SerializedName("deficit")
    @Expose
    private String deficit;
    @SerializedName("lost")
    @Expose
    private String lost;
    @SerializedName("delivered")
    @Expose
    private String delivered;
    @SerializedName("average_delivered")
    @Expose
    private String averageDelivered;
    @SerializedName("fuel")
    @Expose
    private String fuel;
    @SerializedName("total_attendance")
    @Expose
    private String totalAttendance;
    @SerializedName("shipment_price")
    @Expose
    private String shipmentPrice;
    @SerializedName("salary")
    @Expose
    private String salary;
    @SerializedName("revenue")
    @Expose
    private String revenue;
    @SerializedName("net_profit")
    @Expose
    private String netProfit;

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

    public String getDelivered() {
        return delivered;
    }

    public void setDelivered(String delivered) {
        this.delivered = delivered;
    }

    public String getAverageDelivered() {
        return averageDelivered;
    }

    public void setAverageDelivered(String averageDelivered) {
        this.averageDelivered = averageDelivered;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getTotalAttendance() {
        return totalAttendance;
    }

    public void setTotalAttendance(String totalAttendance) {
        this.totalAttendance = totalAttendance;
    }

    public String getShipmentPrice() {
        return shipmentPrice;
    }

    public void setShipmentPrice(String shipmentPrice) {
        this.shipmentPrice = shipmentPrice;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(String netProfit) {
        this.netProfit = netProfit;
    }
}
