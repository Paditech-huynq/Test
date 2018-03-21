package com.unza.wipro;

import android.util.Log;

import com.google.gson.Gson;
import com.paditech.core.helper.PrefUtils;
import com.paditech.core.helper.StringUtil;
import com.unza.wipro.main.models.LoginData;
import com.unza.wipro.main.models.UserData;
import com.unza.wipro.main.models.UserInfo;
import com.unza.wipro.services.AppClient;
import com.unza.wipro.services.AppService;
import com.unza.wipro.transaction.cart.Cart;
import com.unza.wipro.transaction.cart.CartImpl;
import com.unza.wipro.transaction.cart.CartInfo;
import com.unza.wipro.transaction.user.User;
import com.unza.wipro.utils.Utils;

import static com.paditech.core.common.BaseConstant.EMPTY;
import static com.unza.wipro.AppConstans.AUTHORIZATION;
import static com.unza.wipro.AppConstans.PREF_APPKEY;
import static com.unza.wipro.AppConstans.PREF_CURRENT_USER;
import static com.unza.wipro.AppConstans.PREF_INFO;
import static com.unza.wipro.AppConstans.PREF_TOKEN;

public class AppState {
    private static AppState instance;
    private UserData loginInfo;
    private boolean logoutPending;

    static synchronized AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    private AppState() {

    }

    private String token = AppConstans.EMPTY;
    private String appKey = AppConstans.EMPTY;
    private Cart currentCart = new Cart();
    private User currentUser;
    private int notifyCount = 0;
    private AppClient appClient = AppClient.newInstance();

    public CartInfo getCurrentCart() {
        if (currentCart == null) {
            currentCart = new Cart();
        }
        return currentCart;
    }

    public AppService getService() {
        return appClient.getService();
    }

    public UserInfo getCurrentUser() {
        return currentUser;
    }

    public CartImpl editCart() {
        if (currentCart == null) {
            currentCart = new Cart();
        }
        return currentCart;
    }

    public void addCartChangeListener(Cart.CartChangeListener listener) {
        if (currentCart == null) {
            currentCart = new Cart();
        }
        currentCart.addListener(listener);
    }

    public void removeCartChangeListener(Cart.CartChangeListener listener) {
        if (currentCart != null) {
            currentCart.removeListener(listener);
        }
    }

    void release() {
        saveToCache();
        if (currentCart != null) {
            currentCart.removeAllListener();
        }
        currentCart = null;
        currentUser = null;
        token = null;
        appKey = null;
        loginInfo = null;
    }

    protected void saveToCache() {
        PrefUtils.savePreferences(WiproApplication.getAppContext(), PREF_TOKEN, token);
        PrefUtils.savePreferences(WiproApplication.getAppContext(), PREF_APPKEY, appKey);
        PrefUtils.savePreferences(WiproApplication.getAppContext(), PREF_INFO, new Gson().toJson(loginInfo));
        if (currentUser != null) {
            Log.e("saveToCache: ", "curentUser" );
            PrefUtils.savePreferences(WiproApplication.getAppContext(), PREF_CURRENT_USER, new Gson().toJson(currentUser, currentUser.getClass()));
        }

        Log.i("save to cache", "success");
    }

    boolean loadFromCache() {
        try {
            token = PrefUtils.getPreferences(WiproApplication.getAppContext(), PREF_TOKEN, AppConstans.EMPTY);
            appKey = PrefUtils.getPreferences(WiproApplication.getAppContext(), PREF_APPKEY, AppConstans.EMPTY);
            String info = PrefUtils.getPreferences(WiproApplication.getAppContext(), PREF_INFO, AppConstans.EMPTY);

            if (!StringUtil.isEmpty(info)) {
                loginInfo = new Gson().fromJson(info, UserData.class);
                if (loginInfo != null) {
                    currentUser = new User.Builder(loginInfo).build();
                    String cacheUser = PrefUtils.getPreferences(WiproApplication.getAppContext(), PREF_CURRENT_USER, "");
                    if (cacheUser != null && currentUser != null) {
                        Log.i("Cache", String.format("User type: %s", currentUser.getClass().getSimpleName()));
                        currentUser = new Gson().fromJson(cacheUser, currentUser.getClass());
                    }
                }
            }
            Log.i("Cache", "Load from cache success");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Cache", "Load from cache failure");
        }
        return true;
    }

    public boolean isLogin() {
        return !StringUtil.isEmpty(token) && !StringUtil.isEmpty(appKey) && currentUser != null;
    }

    public String getToken() {
        if (isLogin()) return String.format(AUTHORIZATION, token);
        return EMPTY;
    }

    public String getAppKey() {
        if (isLogin()) {
            return Utils.getSha1Hex(appKey);
        }
        return EMPTY;
    }

    public int getNotifyCount() {
        return notifyCount;
    }

    public void setNotifyCount(int notifyCount) {
        this.notifyCount = notifyCount;
    }

    public void logout() {
        logoutPending = true;
        release();
        PrefUtils.savePreferences(WiproApplication.getAppContext(), PREF_TOKEN, "");
        PrefUtils.savePreferences(WiproApplication.getAppContext(), PREF_APPKEY, "");
        PrefUtils.savePreferences(WiproApplication.getAppContext(), PREF_INFO, "");
    }

    public void updateCurrentUser(UserData userData) {
        if (userData.getMemberType() == null && loginInfo != null) {
            userData.setMemberType(loginInfo.getMemberType());
            userData.setIsManager(loginInfo.getIsManager());
        }
        loginInfo = userData;
        currentUser = new User.Builder(userData).build();
    }

    public void updateAppState(LoginData data) {
        token = data.getAccessToken();
        appKey = data.getAppKey();
        updateCurrentUser(data.getInfo());
    }

    public boolean isLogoutPending() {
        if (logoutPending) {
            logoutPending = false;
            return false;
        }
        return logoutPending;
    }
}
