package com.unza.wipro.main.views.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.TextView;

import com.paditech.core.mvp.MVPActivity;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.MainContract;
import com.unza.wipro.main.presenters.MainPresenter;

import butterknife.BindView;

public class MainActivity extends MVPActivity<MainPresenter> implements MainContract.ViewImpl, BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @BindView(R.id.message)
    TextView mTextMessage;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        super.initView();
        navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                mTextMessage.setText(R.string.title_home);
                return true;
            case R.id.navigation_dashboard:
                mTextMessage.setText(R.string.title_dashboard);
                return true;
            case R.id.navigation_notifications:
                mTextMessage.setText(R.string.title_notifications);
                return true;
        }
        return false;
    }
}
