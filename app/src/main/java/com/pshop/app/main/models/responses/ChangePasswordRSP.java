package com.pshop.app.main.models.responses;

import com.google.gson.annotations.SerializedName;
import com.pshop.app.main.models.ChangePasswordData;

public class ChangePasswordRSP extends BaseRSP {
    @SerializedName("data")
    private ChangePasswordData data;

    public ChangePasswordData getData() {
        return data;
    }

    public void setData(ChangePasswordData data) {
        this.data = data;
    }
}