package com.unza.wipro.main.views.fragments;


import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.paditech.core.BaseFragment;
import com.paditech.core.helper.StringUtil;
import com.unza.wipro.R;

import butterknife.BindView;

public class WebViewFragment extends BaseFragment {
    private String urlWeb;
    private String title;
    @BindView(R.id.web_view)
    WebView mWebView;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_web_view;
    }

    public static WebViewFragment newInstance(String urlWeb, String title) {
        Bundle args = new Bundle();
        WebViewFragment fragment = new WebViewFragment();
        fragment.urlWeb = urlWeb;
        fragment.title = title;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public String getScreenTitle() {
        return title;
    }

    @Override
    public void initView() {
        super.initView();
        setupWebView();
        showDetail();
    }

    private void setupWebView() {
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.setFocusableInTouchMode(false);
        mWebView.setFocusable(false);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadsImagesAutomatically(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setDefaultFontSize((int) getResources().getDimension(R.dimen.text_size_normal));
    }

    private void showDetail() {
        if (!StringUtil.isEmpty(urlWeb))
            mWebView.loadUrl(urlWeb);
    }
}
