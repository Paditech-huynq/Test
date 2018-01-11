package com.unza.wipro.main.views.fragments;

import android.os.Bundle;

import com.paditech.core.BaseFragment;
import com.unza.wipro.R;

public class NewsFragment extends BaseFragment {
    public static NewsFragment newInstance() {

        Bundle args = new Bundle();

        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

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

}
