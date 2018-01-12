package com.unza.wipro.main.views.fragments;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.paditech.core.BaseFragment;
import com.paditech.core.mvp.MVPFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.HomeFragmentPagerAdapter;
import com.unza.wipro.main.contracts.HomeContract;
import com.unza.wipro.main.presenters.HomePresenter;

import butterknife.BindView;

public class HomeFragment extends MVPFragment<HomePresenter> implements HomeContract.ViewImpl {
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;

    @BindView(R.id.vpgHome)
    ViewPager mViewPager;

    private HomeFragmentPagerAdapter mAdapter;

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
        setUpViewPagger();
        setUpBottomBar();
        super.setScreenTitle(getString(R.string.title_home_product));
    }

    private void setUpViewPagger() {
        if(mAdapter== null) {
            Log.e("create adapter","create");
            mAdapter = new HomeFragmentPagerAdapter(getChildFragmentManager());
        }
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mAdapter.getCount());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBottomBar.selectTabAtPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setUpBottomBar() {
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                getPresenter().onTabSelected(tabId);
            }
        }, false);
    }

    @Override
    public void switchTab(int pos) {
        mViewPager.setCurrentItem(pos, false);
        updateTitle();
    }

    @Override
    public void updateTitle() {
        try {
            super.setScreenTitle(((BaseFragment) mAdapter.getItem(mViewPager.getCurrentItem())).getScreenTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getScreenTitle() {
        return null;
    }

    @Override
    public void setScreenTitle(String title) {
//        super.setScreenTitle(title);
    }

    @Override
    public void onViewAppear() {
        super.onViewAppear();
    }

    @Override
    public void onViewDisappear() {
        super.onViewDisappear();
    }

    @Override
    public void onResumeFromBackStack() {
        super.onResumeFromBackStack();
        setUpViewPagger();
        setUpBottomBar();
//        super.setScreenTitle(String.valueOf(mAdapter.getPageTitle(mViewPager.getCurrentItem())));
    }
}
