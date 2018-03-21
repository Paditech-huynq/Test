package com.unza.wipro.main.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.paditech.core.image.GlideApp;
import com.paditech.core.mvp.MVPFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.unza.wipro.AppAction;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.HomeFragmentPagerAdapter;
import com.unza.wipro.main.contracts.HomeContract;
import com.unza.wipro.main.presenters.HomePresenter;
import com.unza.wipro.main.views.activities.MainActivity;
import com.unza.wipro.main.views.customs.PlaceHolderDrawableHelper;

import butterknife.BindView;

public class HomeFragment extends MVPFragment<HomePresenter> implements HomeContract.ViewImpl, OnTabSelectListener, AppConstans {
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;

    @BindView(R.id.vpgHome)
    ViewPager mViewPager;

    private HomeFragmentPagerAdapter mAdapter;
    private int currentTab = -1;

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
                if (mAdapter.getItem(position) instanceof FakeScannerFragment) {
                    bus.post(AppAction.NOTIFY_FAKE_SCANNER_FOCUS);
                } else {
                    bus.post(AppAction.NOTIFY_FAKE_SCANNER_UN_FOCUS);
                }
                currentTab = position;
                mBottomBar.selectTabAtPosition(position);
                mAdapter.onViewAppear(position);
                ((MainActivity) getActivity()).updateActionButtonAppearance(mAdapter.getItem(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (currentTab > 0) {
            mViewPager.postDelayed(new Runnable() {

                @Override
                public void run() {
                    mViewPager.setCurrentItem(currentTab);
                }
            }, 100);
        }
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
    public void switchTab(final int pos) {
        currentTab = pos;
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
    public void updateView() {
        if( app.getCurrentUser() == null || getActivity() == null){
            return;
        }
        GlideApp.with(getActivity()).load(app.getCurrentUser().getAvatar()).placeholder(R.drawable.ic_avatar_holder)
                .error(R.drawable.ic_avatar_holder)
                .circleCrop().into((ImageView) getActivity().findViewById(R.id.imvAvatar));
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
        return mAdapter.getItem(mViewPager.getCurrentItem()).isActionShow(resId);
    }

    @Override
    public void onTabSelected(int tabId) {
        getPresenter().onTabSelected(tabId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bus.register(this);
    }

    @Override
    public void onDetach() {
        bus.unregister(this);
        super.onDetach();
    }
}
