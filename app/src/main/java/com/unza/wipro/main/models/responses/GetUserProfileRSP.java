package com.unza.wipro.main.models.responses;


import com.google.gson.annotations.SerializedName;
import com.unza.wipro.transaction.user.User;

public class GetUserProfileRSP extends BaseRSP {
    @SerializedName("data")
    User user;

    public User getUser() {
        return user;
    }
}
