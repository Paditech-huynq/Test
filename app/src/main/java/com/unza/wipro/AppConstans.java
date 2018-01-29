package com.unza.wipro;

import com.paditech.core.common.BaseConstant;
import com.squareup.otto.Bus;

public interface AppConstans extends BaseConstant {
    Bus bus = GlobalBus.getBus();

    AppState app = AppState.getInstance();

    String PREF_TOKEN = "login::token";
    String PREF_APPKEY = "login::appkey";
    String PREF_INFO = "login::info";
    String AUTHORIZATION = "WiproCrmApp %s";
    int PAGE_SIZE = 10;

}
