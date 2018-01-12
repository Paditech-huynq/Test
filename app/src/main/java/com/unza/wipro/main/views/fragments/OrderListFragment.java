package com.unza.wipro.main.views.fragments;

import android.os.Bundle;

import com.paditech.core.BaseFragment;
import com.unza.wipro.R;

public class OrderListFragment extends BaseFragment {
    public static OrderListFragment newInstance() {
        
        Bundle args = new Bundle();
        
        OrderListFragment fragment = new OrderListFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home_order;
    }

    @Override
    public String getScreenTitle() {
        return getString(R.string.title_home_order);
    }

    @Override
    public void setScreenTitle(String title) {
    }

    @Override
    protected boolean isKeepFragment() {
        return true;
    }
}
