package com.mhdhussein.aralpackagemanagement.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Summary {

    @SerializedName("ofd")
    @Expose
    private Integer ofd;
    @SerializedName("cod")
    @Expose
    private Integer cod;
    @SerializedName("tfd")
    @Expose
    private Integer tfd;
    @SerializedName("avg")
    @Expose
    private Double avg;
    @SerializedName("deficit")
    @Expose
    private Integer deficit;
    @SerializedName("lost")
    @Expose
    private Integer lost;
    @SerializedName("deposited")
    @Expose
    private Integer deposited;

    public Integer getOfd() {
        return ofd;
    }

    public void setOfd(Integer ofd) {
        this.ofd = ofd;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public Integer getTfd() {
        return tfd;
    }

    public void setTfd(Integer tfd) {
        this.tfd = tfd;
    }

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }

    public Integer getDeficit() {
        return deficit;
    }

    public void setDeficit(Integer deficit) {
        this.deficit = deficit;
    }

    public Integer getLost() {
        return lost;
    }

    public void setLost(Integer lost) {
        this.lost = lost;
    }

    public Integer getDeposited() {
        return deposited;
    }

    public void setDeposited(Integer deposited) {
        this.deposited = deposited;
    }
}
