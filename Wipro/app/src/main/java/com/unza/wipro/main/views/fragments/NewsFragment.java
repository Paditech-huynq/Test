package com.unza.wipro.main.views.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.paditech.core.BaseFragment;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.NewsFragmentPagerAdapter;
import com.unza.wipro.main.models.responses.GetNewsCategoriesRSP;
import com.unza.wipro.services.AppClient;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends BaseFragment {
    @BindView(R.id.vpgProduct)
    ViewPager mViewPager;

    @BindView(R.id.tabCategory)
    TabLayout mTabLayout;

    @BindView(R.id.layoutLoading)
    View layoutLoading;

    public static NewsFragment newInstance() {

        Bundle args = new Bundle();

        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private NewsFragmentPagerAdapter mAdapter;

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
        getCategories();
    }

    private void setupViewPager() {
        if(mAdapter == null) {
            mAdapter = new NewsFragmentPagerAdapter(getChildFragmentManager());
        }
        mViewPager.setAdapter(mAdapter);
    }

    private void getCategories() {
        showProgressDialog(true);
        AppClient.newInstance().getService().getNewsCategories("", null, 1, 10)
                .enqueue(new Callback<GetNewsCategoriesRSP>() {
                    @Override
                    public void onResponse(Call<GetNewsCategoriesRSP> call, Response<GetNewsCategoriesRSP> response) {
                        try {
                            Log.e("testgetNewsCategories", String.valueOf(response.code()));
                            showProgressDialog(false);
                            if (response.body() != null) {
                                mAdapter.setCategories(response.body().getCategories());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetNewsCategoriesRSP> call, Throwable t) {
                        showProgressDialog(false);
                        showToast(t.getLocalizedMessage());
                    }
                });
    }

    @Override
    public boolean isActionShow(int resId) {
        return false;
    }

    @Override
    public void showProgressDialog(boolean isShown) {
        layoutLoading.setVisibility(isShown ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onNetworkOnline() {
        super.onNetworkOnline();
        if(mAdapter.getCount() == 0)
        {

        }
    }

    @Override
    protected boolean isKeepFragment() {
        return true;
    }
}
