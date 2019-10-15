package com.mhdhussein.aralpackagemanagement.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CourierAttendanceData {

    @SerializedName("data")
    @Expose
    private ArrayList<CourierAttendance> data = null;

    public ArrayList<CourierAttendance> getData() {
        return data;
    }

    public void setData(ArrayList<CourierAttendance> data) {
        this.data = data;
    }
}
