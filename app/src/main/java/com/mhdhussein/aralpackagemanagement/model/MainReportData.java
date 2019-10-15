package com.mhdhussein.aralpackagemanagement.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MainReportData {

    @SerializedName("data")
    @Expose
    private ArrayList<MainReport> data;

    public ArrayList<MainReport> getData() {
        return data;
    }

    public void setData(ArrayList<MainReport> data) {
        this.data = data;
    }

}
