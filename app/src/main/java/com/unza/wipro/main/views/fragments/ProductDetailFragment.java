package com.unza.wipro.main.views.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.transition.Fade;
import android.transition.Transition;
import android.view.Window;
import android.view.WindowManager;

import com.paditech.core.BaseFragment;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.ProductImageAdapter;
import com.unza.wipro.main.views.activities.MainActivity;

import butterknife.BindView;

public class ProductDetailFragment extends BaseFragment {
    @BindView(R.id.vpgProduct)
    ViewPager mViewPager;

    public static ProductDetailFragment newInstance() {

        Bundle args = new Bundle();

        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ProductDetailFragment newInstance(Transition transition) {
        ProductDetailFragment fragment = ProductDetailFragment.newInstance();
        fragment.setSharedElementEnterTransition(transition);
        fragment.setSharedElementReturnTransition(transition);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_product_detail;
    }

    @Override
    public String getScreenTitle() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        setupViewPager();
    }

    private void setupViewPager() {
        mViewPager.setAdapter(new ProductImageAdapter(getActivity()));
    }

    @Override
    public void onViewDisappear() {
        super.onViewDisappear();
        ((MainActivity) getActivity()).setShowHeader(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void onViewAppear() {
        super.onViewAppear();
        ((MainActivity) getActivity()).setShowHeader(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
