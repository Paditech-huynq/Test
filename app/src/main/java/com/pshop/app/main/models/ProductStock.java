package com.pshop.app.main.models;

import android.location.Location;

import com.google.gson.annotations.SerializedName;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/19/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class ProductStock {
    @SerializedName("id")
    private String id = "0";

    @SerializedName("name")
    private String name = "";

    @SerializedName("address")
    private String address = "";

    @SerializedName("lat")
    private String latitude = "0";

    @SerializedName("long")
    private String longitude = "0";

    @SerializedName("phone")
    private String phone = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public Location getLocation() {
        Location location = null;
        try {
            location = new Location(name);
            if (longitude != null) {
                location.setLongitude(Double.parseDouble(longitude));
            }
            if (latitude != null) {
                location.setLatitude(Double.parseDouble(latitude));
            }
        } catch (NumberFormatException ignored) {
        }
        return location;
    }

    public String getPhone() {
        return phone;
    }
}
