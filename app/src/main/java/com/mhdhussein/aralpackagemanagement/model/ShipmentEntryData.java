package com.mhdhussein.aralpackagemanagement.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShipmentEntryData {
    @SerializedName("data")
    @Expose
    private ShipmentEntry data;

    public ShipmentEntry getData() {
        return data;
    }

    public void setData(ShipmentEntry data) {
        this.data = data;
    }
}
