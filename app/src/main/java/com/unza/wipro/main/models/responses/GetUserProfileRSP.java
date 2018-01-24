package com.unza.wipro.main.models.responses;


import com.google.gson.annotations.SerializedName;
import com.unza.wipro.transaction.user.UserData;

public class GetUserProfileRSP extends BaseRSP {
    @SerializedName("data")
    UserData user;

    public UserData getUser() {
        return user;
    }
}
