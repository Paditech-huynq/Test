package com.pshop.app.main.models;

import com.google.gson.annotations.SerializedName;

public class Thumbnail {

    @SerializedName("link")
    private String link;
    @SerializedName("width")
    private String width;
    @SerializedName("height")
    private String height;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}