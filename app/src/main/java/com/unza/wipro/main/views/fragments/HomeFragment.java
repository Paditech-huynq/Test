package com.unza.wipro.main.views.fragments;

import android.os.Bundle;

import com.paditech.core.mvp.MVPFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.unza.wipro.R;
import com.unza.wipro.main.presenters.HomePresenter;

import butterknife.BindView;

public class HomeFragment extends MVPFragment<HomePresenter> {
    @BindView(R.id.bottomBar)
    BottomBar navigation;

    public static HomeFragment newInstance() {
        
        Bundle args = new Bundle();
        
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        super.initView();
        navigation.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                getPresenter().onTabSelected(tabId);
            }
        });
    }
}
