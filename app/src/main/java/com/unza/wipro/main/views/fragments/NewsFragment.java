package com.unza.wipro.main.views.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.paditech.core.BaseFragment;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.ProductFragmentPagerAdapter;

import butterknife.BindView;

public class NewsFragment extends BaseFragment {
    @BindView(R.id.vpgProduct)
    ViewPager mViewPager;

    @BindView(R.id.tabCategory)
    TabLayout mTabLayout;

    public static NewsFragment newInstance() {

        Bundle args = new Bundle();

        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ProductFragmentPagerAdapter mAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home_news;
    }

    @Override
    public String getScreenTitle() {
        return getString(R.string.title_home_news);
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
        mAdapter = new ProductFragmentPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    protected boolean isKeepFragment() {
        return true;
    }
}
