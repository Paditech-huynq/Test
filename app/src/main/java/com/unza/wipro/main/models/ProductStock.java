package com.unza.wipro.main.models;

import com.google.gson.annotations.SerializedName;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/19/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class ProductStock {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("address")
    String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public void setAddress(String address) {
        this.address = address;
    }
}
