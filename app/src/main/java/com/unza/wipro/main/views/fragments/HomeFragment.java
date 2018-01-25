package com.unza.wipro.main.views.fragments;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.paditech.core.mvp.MVPFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.HomeFragmentPagerAdapter;
import com.unza.wipro.main.contracts.HomeContract;
import com.unza.wipro.main.presenters.HomePresenter;
import com.unza.wipro.main.views.activities.MainActivity;

import butterknife.BindView;

public class HomeFragment extends MVPFragment<HomePresenter> implements HomeContract.ViewImpl, OnTabSelectListener {
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
        ((MainActivity) getActivity()).updateActionButtonAppearance(mAdapter.getItem(mViewPager.getCurrentItem()));
        super.setScreenTitle(getString(R.string.title_home_product));
    }

    private void setUpViewPagger() {
        if (mAdapter == null) {
            mAdapter = new HomeFragmentPagerAdapter(getChildFragmentManager());
        }
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBottomBar.selectTabAtPosition(position);
                try {
                    mAdapter.onViewAppear(position);
                } catch (Exception e){
                    e.printStackTrace();
                }
                ((MainActivity) getActivity()).updateActionButtonAppearance(mAdapter.getItem(position));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onResumeFromBackStack() {
        super.onResumeFromBackStack();
        ((MainActivity) getActivity()).updateActionButtonAppearance(mAdapter.getItem(mViewPager.getCurrentItem()));
    }

    private void setUpBottomBar() {
        mBottomBar.setOnTabSelectListener(this, false);
    }

    @Override
    public void switchTab(int pos) {
        mViewPager.setCurrentItem(pos, false);
        updateTitle();
    }

    @Override
    public void updateTitle() {
        try {
            super.setScreenTitle(mAdapter.getItem(mViewPager.getCurrentItem()).getScreenTitle());
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
    }

    @Override
    public boolean isActionShow(int resId) {
        return resId != R.id.btnTrash;
    }

    @Override
    public void onTabSelected(int tabId) {
        getPresenter().onTabSelected(tabId);
    }

    @Override
    public void onViewAppear() {
        mAdapter.onViewAppear(-1);
        super.onViewAppear();
    }

    @Override
    public void onViewDisappear() {
        mAdapter.onViewAppear(-2);
        super.onViewDisappear();
    }
}
