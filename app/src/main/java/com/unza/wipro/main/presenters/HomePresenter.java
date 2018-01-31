package com.unza.wipro.main.presenters;

import android.util.Log;

import com.paditech.core.mvp.BasePresenter;
import com.squareup.otto.Subscribe;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.HomeContract;
import com.unza.wipro.transaction.Transaction;

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
}
