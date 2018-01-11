package com.unza.wipro.main.views.activities;

import com.paditech.core.mvp.MVPActivity;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.MainContract;
import com.unza.wipro.main.presenters.MainPresenter;

import butterknife.BindView;

public class MainActivity extends MVPActivity<MainPresenter> implements MainContract.ViewImpl, OnTabSelectListener {

    @BindView(R.id.bottomBar)
    BottomBar navigation;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        super.initView();
        navigation.setOnTabSelectListener(this, true);
    }

    @Override
    public void onTabSelected(int tabId) {
        switch (tabId) {
            case R.id.navigation_product:
                break;
            case R.id.navigation_order:
                break;
            case R.id.navigation_qr:
                break;
            case R.id.navigation_lookup:
                break;
            case R.id.navigation_news:
                break;
        }
    }
}
