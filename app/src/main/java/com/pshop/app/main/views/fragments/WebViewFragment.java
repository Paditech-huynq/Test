package com.pshop.app.main.views.fragments;


import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.paditech.core.BaseFragment;
import com.paditech.core.helper.StringUtil;
import com.pshop.app.R;

import butterknife.BindView;

public class WebViewFragment extends BaseFragment {
    @BindView(R.id.web_view)
    WebView mWebView;
    private String urlWeb;
    private String title;

    public static WebViewFragment newInstance(String urlWeb, String title) {
        Bundle args = new Bundle();
        WebViewFragment fragment = new WebViewFragment();
        fragment.urlWeb = urlWeb;
        fragment.title = title;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_web_view;
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
