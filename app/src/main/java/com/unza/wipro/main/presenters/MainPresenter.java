package com.unza.wipro.main.presenters;

import android.util.Log;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.AppConstans;
import com.unza.wipro.main.contracts.MainContract;
import com.unza.wipro.transaction.cart.Cart;

public class MainPresenter extends BasePresenter<MainContract.ViewImpl> implements MainContract.Presenter, AppConstans, Cart.CartChangeListener {
    @Override
    public void onCreate() {
        super.onCreate();
        app.addCartChangeListener(this);
        Log.e("AA","BBB");
    }

    @Override
    public void onCartUpdate() {
        getView().updateCartCount();
    }

    @Override
    public void onDestroy() {
        app.removeCartChangeListener(this);
        super.onDestroy();
    }
}
