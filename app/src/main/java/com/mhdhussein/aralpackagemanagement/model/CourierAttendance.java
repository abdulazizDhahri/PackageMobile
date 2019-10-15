package com.mhdhussein.aralpackagemanagement.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourierAttendance {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("courier")
    @Expose
    private String courier;
    @SerializedName("courier_id")
    @Expose
    private Integer courierId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("supervisor")
    @Expose
    private String supervisor;
    @SerializedName("supervisor_id")
    @Expose
    private Integer supervisorId;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("lateTime")
    @Expose
    private String lateTime;
    @SerializedName("lateTimeNumber")
    @Expose
    private String lateTimeNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    public Integer getCourierId() {
        return courierId;
    }

    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public Integer getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Integer supervisorId) {
        this.supervisorId = supervisorId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLateTime() {
        return lateTime;
    }

    public void setLateTime(String lateTime) {
        this.lateTime = lateTime;
    }

    public String getLateTimeNumber() {
        return lateTimeNumber;
    }

    public void setLateTimeNumber(String lateTimeNumber) {
        this.lateTimeNumber = lateTimeNumber;
    }
}
