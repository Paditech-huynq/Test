package com.unza.wipro.main.models.responses;

import com.google.gson.annotations.SerializedName;
import com.unza.wipro.main.models.ChangePasswordData;

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