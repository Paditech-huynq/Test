package com.unza.wipro.main.views.fragments;

import android.os.Bundle;

import com.paditech.core.BaseFragment;
import com.unza.wipro.R;

public class ProductListFragment extends BaseFragment{
    public static ProductListFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    protected int getLayoutResource() {
        return R.layout.layout_default;
    }
}
