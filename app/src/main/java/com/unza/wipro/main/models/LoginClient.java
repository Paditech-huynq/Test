package com.unza.wipro.main.models;

import android.content.Context;

import com.google.gson.Gson;
import com.paditech.core.helper.PrefUtils;
import com.paditech.core.helper.StringUtil;
import com.unza.wipro.AppConstans;
import com.unza.wipro.utils.Utils;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/22/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class LoginClient implements AppConstans {

    public static boolean isLogin(Context context) {
        return !StringUtil.isEmpty(PrefUtils.getPreferences(context, PREF_TOKEN, EMPTY)) &&
                !StringUtil.isEmpty(PrefUtils.getPreferences(context, PREF_APPKEY, EMPTY)) &&
                !StringUtil.isEmpty(PrefUtils.getPreferences(context, PREF_INFO, EMPTY));
    }

    public static String getToken(Context context) {
        if (isLogin(context)) return String.format(AUTHORIZATION, PrefUtils.getPreferences(context, PREF_TOKEN, EMPTY));
        return "";
    }

    public static String getAppKey(Context context) {
        if (isLogin(context)) return Utils.getSha1Hex(PrefUtils.getPreferences(context, PREF_APPKEY, EMPTY));
        return "";
    }

    public static LoginInfo getLoginInfo(Context context) {
        if (!StringUtil.isEmpty(PrefUtils.getPreferences(context, PREF_INFO, EMPTY))) {
            try {
                return new Gson().fromJson(PrefUtils.getPreferences(context, PREF_INFO, EMPTY), LoginInfo.class);
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static void logout(Context context) {
        PrefUtils.savePreferences(context, PREF_TOKEN, EMPTY);
        PrefUtils.savePreferences(context, PREF_APPKEY, EMPTY);
        PrefUtils.savePreferences(context, PREF_INFO, EMPTY);
    }
}
