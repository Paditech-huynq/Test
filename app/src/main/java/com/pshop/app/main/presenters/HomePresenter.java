package com.pshop.app.main.presenters;

import android.util.Log;

import com.paditech.core.mvp.BasePresenter;
import com.squareup.otto.Subscribe;
import com.pshop.app.AppConstans;
import com.pshop.app.R;
import com.pshop.app.main.contracts.HomeContract;
import com.pshop.app.transaction.Transaction;

public class HomePresenter extends BasePresenter<HomeContract.ViewImpl> implements HomeContract.Presenter, AppConstans {
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            bus.register(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            bus.unregister(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTabSelected(int tabId) {
        try {
            switch (tabId) {
                case R.id.tab_product:
                    getView().switchTab(0);
                    break;
                case R.id.tab_order:
                    getView().switchTab(1);
                    break;
                case R.id.tab_qr:
                    getView().switchTab(2);
                    break;
                case R.id.tab_lookup:
                    getView().switchTab(3);
                    break;
                case R.id.tab_news:
                    getView().switchTab(4);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Change to tab 2 when transaction success
     *
     * @param transaction
     */
    @Subscribe
    public void onTransactionSuccess(Transaction transaction) {
        Log.e("Transaction",transaction+"");
        try {
            getView().switchTab(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewAppear() {
        super.onViewAppear();
        getView().updateView();
    }
}
