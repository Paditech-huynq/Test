package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.HomeContract;

public class HomePresenter extends BasePresenter<HomeContract.ViewImpl> implements HomeContract.Presenter {
    @Override
    public void onTabSelected(int tabId) {
        switch (tabId) {
            case R.id.tab_product:
                break;
            case R.id.tab_order:
                break;
            case R.id.tab_qr:
                break;
            case R.id.tab_lookup:
                break;
            case R.id.tab_news:
                break;
        }
    }
}
