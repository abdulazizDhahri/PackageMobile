package com.mhdhussein.aralpackagemanagement.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SummaryData {
    @SerializedName("data")
    @Expose
    private Summary data;

    public Summary getData() {
        return data;
    }

    public void setData(Summary data) {
        this.data = data;
    }
}
