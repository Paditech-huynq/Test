package com.unza.wipro;

import com.unza.wipro.main.models.UserInfo;
import com.unza.wipro.transaction.cart.Cart;
import com.unza.wipro.transaction.cart.CartImpl;
import com.unza.wipro.transaction.cart.CartInfo;
import com.unza.wipro.transaction.user.User;

public class AppState {
    private static AppState instance;

    static synchronized AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

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
        currentCart.removeAllListener();
        currentCart = null;
        currentUser = null;
    }

    public void loadFromCache() {
        //todo: Load default data from cache
    }
}
