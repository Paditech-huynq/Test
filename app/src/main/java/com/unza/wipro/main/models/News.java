package com.unza.wipro.main.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/17/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class News {
    @SerializedName("id")
    int id;
    @SerializedName("title")
    String title;
    @SerializedName("summary")
    String summary;
    @SerializedName("content")
    String content;
    @SerializedName("thumbnail")
    Thumbnail thumbnail;
    @SerializedName("created_at")
    long createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
