package com.example.demo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostUser {
    @Expose
    @SerializedName("idUsers")
    private Integer idUsers;
    @Expose
    @SerializedName("uuid")
    private String uuid;
    @Expose
    @SerializedName("email")
    private String email;

    public PostUser(String uuid, String email) {
        this.uuid = uuid;
        this.email = email;
    }

    @SerializedName("idUsers")
    public Integer getIdUsers() {
        return idUsers;
    }

    @SerializedName("idUsers")
    public void setIdUsers(Integer idUsers) {
        this.idUsers = idUsers;
    }


    @SerializedName("uuid")
    public String getUuid() {
        return uuid;
    }

    @SerializedName("uuid")
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @SerializedName("email")
    public String getEmail() {
        return email;
    }

    @SerializedName("email")
    public void setEmail(String email) {
        this.email = email;
    }
}
