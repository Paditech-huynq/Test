package com.unza.wipro.main.models;

import com.google.gson.annotations.SerializedName;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/22/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class LoginData {
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("appKey")
    private String appKey;
    @SerializedName("info")
    private LoginInfo info;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public LoginInfo getInfo() {
        return info;
    }

    public void setInfo(LoginInfo info) {
        this.info = info;
    }
}
