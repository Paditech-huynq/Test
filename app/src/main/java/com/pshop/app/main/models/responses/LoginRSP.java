package com.pshop.app.main.models.responses;

import com.google.gson.annotations.SerializedName;
import com.pshop.app.main.models.LoginData;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/22/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class LoginRSP extends BaseRSP {

    @SerializedName("data")
    private LoginData data;

    public LoginData getData() {
        return data;
    }

    public boolean isSuccess() {return result == 1 && getData() != null;};
}
