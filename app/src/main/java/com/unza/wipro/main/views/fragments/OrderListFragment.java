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
        return R.layout.layout_default;
    }
}
