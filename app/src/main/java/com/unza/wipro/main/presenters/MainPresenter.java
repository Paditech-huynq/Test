package com.unza.wipro.main.presenters;

import android.util.Log;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.AppConstans;
import com.unza.wipro.main.contracts.MainContract;
import com.unza.wipro.main.models.Notice;
import com.unza.wipro.main.models.responses.GetNotificationsRSP;
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
        Log.e("AA", "BBB");
    }

    @Override
    public void onCartUpdate() {
        getView().updateCartCount();
    }

    @Override
    public void getUnreadNoticeCount() {
        if (!app.isLogin()) return;
        AppClient.newInstance().getService().getNotifications(app.getToken(),
                app.getAppKey()).enqueue(new Callback<GetNotificationsRSP>() {
            @Override
            public void onResponse(Call<GetNotificationsRSP> call, Response<GetNotificationsRSP> response) {
                try {
                    if (response.body() != null && response.body().getNotices() != null &&
                            response.body().getNotices().size() > 0) {
                        int count = 0;
                        for (Notice notice: response.body().getNotices()) {
                            if (!notice.isRead()) count ++;
                        }
                        app.setNotifyCount(count);
                        getView().updateNoticeCount();
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<GetNotificationsRSP> call, Throwable t) {
            }
        });
    }

    @Override
    public void onDestroy() {
        app.removeCartChangeListener(this);
        super.onDestroy();
    }
}
