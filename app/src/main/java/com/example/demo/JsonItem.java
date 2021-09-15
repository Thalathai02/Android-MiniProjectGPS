package com.example.demo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsonItem {
@Expose
    @SerializedName("id")
    private Integer id;
    @Expose
    @SerializedName("lat")
    private Double lat;
    @Expose
    @SerializedName("lng")
    private Double lng;
    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("user")
    private Integer user;

    public JsonItem(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }



}
