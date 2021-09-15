package com.example.demo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class postLocation {
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
    @SerializedName("Date")
    private String Date;
    @Expose
    @SerializedName("user")
    private Integer user;

    public postLocation(Double lng, Double lat, String Date) {
        this.lat = lat;
        this.lng = lng;
        this.Date = Date;
    }
}
