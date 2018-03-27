package com.pshop.app;

import com.google.gson.Gson;

public enum AppAction {
    REQUEST_CAMERA_OPEN("request_camera_open"),
    REQUEST_CAMERA_CLOSE("request_camera_close"),
    NOTIFY_FAKE_SCANNER_FOCUS("notify_fake_scanner_focus"),
    NOTIFY_FAKE_SCANNER_UN_FOCUS("notify_fake_scanner_un_focus"),
    NOTIFY_CUSTOMER_SELECTED("notify_customer_selected"),
    NOTIFY_CUSTOMER_SELECTED_AFTER_CREATE("notify_customer_selected_after_create");

    public String value;
    private String extraData;

    AppAction(String value) {
        this.value = value;
    }

    public AppAction setData(String extraData) {
        this.extraData = extraData;
        return this;
    }

    public <T> T getData(Class<T> target) {
        return new Gson().fromJson(extraData, target);
    }
}
