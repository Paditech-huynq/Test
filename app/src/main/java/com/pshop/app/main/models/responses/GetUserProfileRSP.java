package com.pshop.app.main.models.responses;


import com.google.gson.annotations.SerializedName;
import com.pshop.app.main.models.UserData;

public class GetUserProfileRSP extends BaseRSP {
    @SerializedName("data")
    UserData user;

    public UserData getUser() {
        return user;
    }
}
