package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.HomeContract;

public class HomePresenter extends BasePresenter<HomeContract.ViewImpl> implements HomeContract.Presenter {
    @Override
    public void onTabSelected(int tabId) {
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
    }
}
