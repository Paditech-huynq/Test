package com.unza.wipro.main.views.fragments;

import android.content.Context;
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
        return R.layout.fragment_home_product;
    }

    @Override
    public String getScreenTitle() {
        return getString(R.string.title_home_product);
    }

    @Override
    public void setScreenTitle(String title) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setScreenTitle(getScreenTitle());
    }
}
