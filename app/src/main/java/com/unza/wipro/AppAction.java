package com.unza.wipro;

import com.google.gson.Gson;

public enum AppAction {
    REQUEST_CAMERA_OPEN("request_camera_open"),
    REQUEST_CAMERA_CLOSE("request_camera_close"),
    NOTIFY_FAKE_SCANNER_FOCUS("notify_fake_scanner_focus"),
    NOTIFY_FAKE_SCANNER_UN_FOCUS("notify_fake_scanner_un_focus");

    public String value;

    AppAction(String value) {
        this.value = value;
    }

    private String extraData;

    public void setData(String extraData) {
        this.extraData = extraData;

    }

    public <T> T getData(Class<T> target) {
        return new Gson().fromJson(extraData, target);
    }
}
