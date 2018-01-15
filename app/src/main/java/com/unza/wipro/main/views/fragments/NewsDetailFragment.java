package com.unza.wipro.main.views.fragments;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.paditech.core.BaseFragment;
import com.unza.wipro.R;
import com.unza.wipro.main.views.activities.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/15/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class NewsDetailFragment extends BaseFragment {

    @BindView(R.id.tv_news_title)
    TextView mTitleText;
    @BindView(R.id.tv_news_desc)
    TextView mDescText;
    @BindView(R.id.tv_news_date)
    TextView mDateText;
    @BindView(R.id.web_view)
    WebView mWebView;

    public static NewsDetailFragment newInstance() {
        Bundle args = new Bundle();
        NewsDetailFragment fragment = new NewsDetailFragment();
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
        mTitleText.setText("Range Rover Velar có giá từ 3,9 tỉ đồng tại Việt Nam");
        mDescText.setText("Mẫu xe đàn anh của Range Rover Evoque được niêm yết giá bán từ 3,9 đến 4,9 tỉ đồng tại Việt Nam tùy phiên bản.");
        mDateText.setText("3 giờ trước");

        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.setFocusableInTouchMode(false);
        mWebView.setFocusable(false);
        mWebView.setWebViewClient(new WebBrowser());
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setDefaultFontSize((int) getResources().getDimension(R.dimen.text_size_normal));

        mWebView.loadUrl("http://autodaily.vn/2017/08/range-rover-velar-gia-tu-39-ty-dong-chot-ngay-ra-mat-tai-viet-nam/");
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

    @OnClick(R.id.imv_back)
    protected void back() {
        getActivity().onBackPressed();
    }

    private class WebBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }
}
