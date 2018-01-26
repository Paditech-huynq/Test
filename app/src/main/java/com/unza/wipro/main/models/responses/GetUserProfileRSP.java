package com.unza.wipro.main.models.responses;


import com.google.gson.annotations.SerializedName;
import com.unza.wipro.main.models.LoginInfo;

public class GetUserProfileRSP extends BaseRSP {
    @SerializedName("data")
    LoginInfo user;

    public LoginInfo getUser() {
        return user;
    }
}
