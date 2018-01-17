package com.unza.wipro.main.views.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.paditech.core.BaseFragment;
import com.paditech.core.common.BaseConstant;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.NewsFragmentPagerAdapter;
import com.unza.wipro.main.models.responses.GetNewsCategoriesRSP;
import com.unza.wipro.main.models.responses.GetNewsRSP;
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
        mAdapter = new NewsFragmentPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    protected boolean isKeepFragment() {
        return true;
    }

    private void getCategories() {
        showProgressDialog(true);
        AppClient.newInstance().getService().getNewsCategories("", null, 1, 10)
                .enqueue(new Callback<GetNewsCategoriesRSP>() {
                    @Override
                    public void onResponse(Call<GetNewsCategoriesRSP> call, Response<GetNewsCategoriesRSP> response) {
                        try {
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
}
