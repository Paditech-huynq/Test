package com.pshop.app.main.models;

import com.google.gson.annotations.SerializedName;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/17/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class NewsCategory {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("note")
    String note;
    @SerializedName("cover")
    String cover;
    @SerializedName("updated_at")
    String updatedAt;

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
