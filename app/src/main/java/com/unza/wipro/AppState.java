package com.unza.wipro;

import com.google.gson.Gson;
import com.paditech.core.helper.PrefUtils;
import com.paditech.core.helper.StringUtil;
import com.unza.wipro.main.models.LoginInfo;
import com.unza.wipro.main.models.UserInfo;
import com.unza.wipro.transaction.cart.Cart;
import com.unza.wipro.transaction.cart.CartImpl;
import com.unza.wipro.transaction.cart.CartInfo;
import com.unza.wipro.transaction.user.User;
import com.unza.wipro.utils.Utils;

import static com.unza.wipro.AppConstans.AUTHORIZATION;
import static com.unza.wipro.AppConstans.PREF_APPKEY;
import static com.unza.wipro.AppConstans.PREF_INFO;
import static com.unza.wipro.AppConstans.PREF_TOKEN;

public class AppState {
    private static AppState instance;

    public static synchronized AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    private String token = AppConstans.EMPTY;;
    private String appKey = AppConstans.EMPTY;;
    private Cart currentCart;
    private User currentUser;

    void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public CartInfo getCurrentCart() {
        if (currentCart == null) {
            currentCart = new Cart();
        }
        return currentCart;
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
        currentCart.addListener(listener);
    }

    public void removeCartChangeListener(Cart.CartChangeListener listener) {
        currentCart.removeListener(listener);
    }

    void release() {
        if (currentCart != null) currentCart.removeAllListener();
        currentCart = null;
        currentUser = null;
        token = AppConstans.EMPTY;
        appKey = AppConstans.EMPTY;
    }

    public void saveToCache(String token, String appKey, String userInfo) {
        PrefUtils.savePreferences(WiproApplication.getAppContext(), PREF_TOKEN, token);
        PrefUtils.savePreferences(WiproApplication.getAppContext(), PREF_APPKEY, appKey);
        PrefUtils.savePreferences(WiproApplication.getAppContext(), PREF_INFO, userInfo);
    }

    public void loadFromCache() {
        //todo: Load default data from cache
        token = PrefUtils.getPreferences(WiproApplication.getAppContext(), PREF_TOKEN, AppConstans.EMPTY);
        appKey = PrefUtils.getPreferences(WiproApplication.getAppContext(), PREF_APPKEY, AppConstans.EMPTY);
        String info = PrefUtils.getPreferences(WiproApplication.getAppContext(), PREF_INFO, AppConstans.EMPTY);
        if (!StringUtil.isEmpty(info)) {
            try {
                currentUser = new User.Builder(new Gson().fromJson(info, LoginInfo.class)).build();
            } catch (Exception e) {
            }
        }
    }

    public boolean isLogin() {
        return !StringUtil.isEmpty(token) && !StringUtil.isEmpty(appKey) && currentUser != null;
    }

    public String getToken() {
        if (isLogin()) return String.format(AUTHORIZATION, token);
        return "";
    }

    public String getAppKey() {
        if (isLogin()) return Utils.getSha1Hex(appKey);
        return "";
    }

    public void logout() {
        token = AppConstans.EMPTY;
        appKey = AppConstans.EMPTY;
        currentUser = null;
        PrefUtils.savePreferences(WiproApplication.getAppContext(), PREF_TOKEN, AppConstans.EMPTY);
        PrefUtils.savePreferences(WiproApplication.getAppContext(), PREF_APPKEY, AppConstans.EMPTY);;
        PrefUtils.savePreferences(WiproApplication.getAppContext(), PREF_INFO, AppConstans.EMPTY);;
    }
}
