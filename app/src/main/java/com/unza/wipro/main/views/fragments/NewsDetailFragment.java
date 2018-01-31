package com.unza.wipro.main.views.fragments;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.paditech.core.BaseFragment;
import com.paditech.core.helper.StringUtil;
import com.unza.wipro.R;
import com.unza.wipro.main.models.News;
import com.unza.wipro.main.models.responses.GetNewsDetailRSP;
import com.unza.wipro.main.views.activities.MainActivity;
import com.unza.wipro.services.AppClient;
import com.unza.wipro.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/15/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class NewsDetailFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.tv_news_title)
    TextView mTitleText;
    @BindView(R.id.tv_news_desc)
    TextView mDescText;
    @BindView(R.id.tv_news_date)
    TextView mDateText;
    @BindView(R.id.web_view)
    WebView mWebView;

    News mNews;

    public static NewsDetailFragment newInstance(News news) {
        Bundle args = new Bundle();
        NewsDetailFragment fragment = new NewsDetailFragment();
        fragment.mNews = news;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_news_detail;
    }

    @Override
    public String getScreenTitle() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        setupWebView();
        setupSwipeRefresh();
        getDetail();
    }

    @Override
    public void onViewAppear() {
        super.onViewAppear();
        ((MainActivity) getActivity()).setShowHeader(false);
    }

    @Override
    public void onViewDisappear() {
        super.onViewDisappear();
        ((MainActivity) getActivity()).setShowHeader(true);
    }

    @Override
    public void onRefresh() {
        getDetail();
    }

    @OnClick(R.id.imv_back)
    protected void back() {
        getActivity().onBackPressed();
    }

    private void setupWebView() {
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.setFocusableInTouchMode(false);
        mWebView.setFocusable(false);
        mWebView.setWebViewClient(new WebBrowser());
        WebSettings settings = mWebView.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setJavaScriptEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setDefaultFontSize((int) getResources().getDimension(R.dimen.text_size_normal));
    }

    private void setupSwipeRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
    }

    private void showDetail(News news) {
        if (news == null) return;
        mNews = news;
        mTitleText.setText(mNews.getTitle());
        mDescText.setText(mNews.getSummary());
        mDateText.setText(Utils.getTimeCreated(getContext(), mNews.getCreatedAt()));
        if (!StringUtil.isEmpty(mNews.getContent())) {
            //todo: fix
            String head = "<head> <style>img{display: inline;height: auto;max-width:   100%;}</style> <style>body {font-family: 'Roboto';  }</style></head>";
            mWebView.loadData(head+mNews.getContent(), "text/html; charset=UTF-8;", null);
        }
    }

    private void getDetail() {
        if (mNews == null) return;
        showProgressDialog(true);
        AppClient.newInstance().getService().getNewsDetail(String.valueOf(mNews.getId()))
                .enqueue(new Callback<GetNewsDetailRSP>() {
                    @Override
                    public void onResponse(Call<GetNewsDetailRSP> call, Response<GetNewsDetailRSP> response) {
                        try {
                            mSwipeRefreshLayout.setRefreshing(false);
                            showProgressDialog(false);
                            if (response.body() != null) {
                                showDetail(response.body().getNews());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetNewsDetailRSP> call, Throwable t) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        showProgressDialog(false);
                        showToast(t.getLocalizedMessage());
                    }
                });
    }

    private class WebBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }
}
