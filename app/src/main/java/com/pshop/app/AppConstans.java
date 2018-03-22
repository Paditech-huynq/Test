package com.pshop.app;

import com.paditech.core.common.BaseConstant;
import com.squareup.otto.Bus;

public interface AppConstans extends BaseConstant {
    Bus bus = GlobalBus.getBus();

    AppState app = AppState.getInstance();

    String PREF_TOKEN = "login::token";
    String PREF_APPKEY = "login::appkey";
    String PREF_INFO = "login::info";
    String PREF_CURRENT_USER = "login::current_user";

    String AUTHORIZATION = "WiproCrmApp %s";
    int PAGE_SIZE = 10;

    public interface Api {
        int Success = 1;
        int Failure = 2;
    }
}
