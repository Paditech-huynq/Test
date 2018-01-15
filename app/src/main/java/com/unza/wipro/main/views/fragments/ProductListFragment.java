package com.unza.wipro.main.views.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.paditech.core.BaseFragment;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.ProductFragmentPagerAdapter;

import butterknife.BindView;

public class ProductListFragment extends BaseFragment {
    @BindView(R.id.vpgProduct)
    ViewPager mViewPager;

    @BindView(R.id.tabCategory)
    TabLayout mTabLayout;

    private ProductFragmentPagerAdapter mAdapter;
    private int currentPage;

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
    public void initView() {
        super.initView();
        setupViewPager();
    }

    private void setupViewPager() {
        if(mAdapter == null) {
            mAdapter = new ProductFragmentPagerAdapter(getChildFragmentManager());
        }
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    protected boolean isKeepFragment() {
        return true;
    }
}
