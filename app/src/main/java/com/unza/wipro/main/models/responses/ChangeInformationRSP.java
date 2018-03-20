package com.unza.wipro.main.models.responses;


import com.google.gson.annotations.SerializedName;
import com.unza.wipro.main.models.UserData;

public class ChangeInformationRSP extends BaseRSP {
    @SerializedName("data")
    private UserData data;

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }
}
