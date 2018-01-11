package com.unza.wipro.main.views.activities;

import android.util.Log;

import com.paditech.core.mvp.MVPActivity;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.MainContract;
import com.unza.wipro.main.presenters.MainPresenter;
import com.unza.wipro.main.views.fragments.HomeFragment;

public class MainActivity extends MVPActivity<MainPresenter> implements MainContract.ViewImpl {

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        super.initView();
        switchFragment(HomeFragment.newInstance(), false);
        Log.e("AAA","BBB");
    }
}
