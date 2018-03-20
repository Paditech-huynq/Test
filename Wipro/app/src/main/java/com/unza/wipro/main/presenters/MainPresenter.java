package com.unza.wipro.main.presenters;

import android.util.Log;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.AppConstans;
import com.unza.wipro.main.contracts.MainContract;
import com.unza.wipro.main.models.responses.GetNotificationsUnreadRSP;
import com.unza.wipro.services.AppClient;
import com.unza.wipro.transaction.cart.Cart;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter extends BasePresenter<MainContract.ViewImpl> implements MainContract.Presenter, AppConstans, Cart.CartChangeListener {
    @Override
    public void onCreate() {
        super.onCreate();
        app.addCartChangeListener(this);
    }

    @Override
    public void onCartUpdate() {
        try {
            getView().updateCartCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewAppear() {
        super.onViewAppear();
        try {
            getView().updateCartCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getUnreadNoticeCount() {
        if (!app.isLogin()) return;
        AppClient.newInstance().getService().getUnReadNotificationsCount(app.getToken(),
                app.getAppKey()).enqueue(new Callback<GetNotificationsUnreadRSP>() {
            @Override
            public void onResponse(Call<GetNotificationsUnreadRSP> call, Response<GetNotificationsUnreadRSP> response) {
                try {
                    Log.e("testUnReadNotifications", String.valueOf(response.code()));
                    if (response.body() != null) {
                        int count = response.body().count();
                        app.setNotifyCount(count);
                        getView().updateNoticeCount();
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<GetNotificationsUnreadRSP> call, Throwable t) {
            }
        });
    }

    @Override
    public void onDestroy() {
        app.removeCartChangeListener(this);
        super.onDestroy();
    }
}
