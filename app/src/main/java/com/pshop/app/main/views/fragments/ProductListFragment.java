package com.pshop.app.main.views.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.paditech.core.BaseFragment;
import com.pshop.app.R;
import com.pshop.app.main.adapter.ProductFragmentPagerAdapter;
import com.pshop.app.main.models.ProductCategory;
import com.pshop.app.main.models.responses.GetProductCategoryRSP;
import com.pshop.app.services.AppClient;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListFragment extends BaseFragment {
    @BindView(R.id.layoutLoading)
    View layoutLoading;

    @BindView(R.id.vpgProduct)
    ViewPager mViewPager;

    @BindView(R.id.tabCategory)
    TabLayout mTabLayout;

    private ProductFragmentPagerAdapter mAdapter;

    public static ProductListFragment newInstance() {

        Bundle args = new Bundle();

        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home_product;
    }

    @Override
    public String getScreenTitle() {
        return getString(R.string.title_home_product);
    }

    @Override
    public void setScreenTitle(String title) {
    }

    @Override
    public void initView() {
        super.initView();
        setupViewPager();
        getProductCategoryFromServer();
    }

    private void getProductCategoryFromServer() {
        showProgressDialog(true);
        AppClient.newInstance()
                .getService()
                .getProductCategory()
                .enqueue(new Callback<GetProductCategoryRSP>() {
                    @Override
                    public void onResponse(Call<GetProductCategoryRSP> call, Response<GetProductCategoryRSP> response) {
                        try {
                            Log.e("testgetProductCategory", String.valueOf(response.code()));
                            showProgressDialog(false);
                            if (response.body() != null) {
                                GetProductCategoryRSP productCategoryRSP = response.body();
                                List<ProductCategory> productCategories = productCategoryRSP.getData();
                                mAdapter.setProductCategories(productCategories);
                                mAdapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetProductCategoryRSP> call, Throwable t) {
                        try {
                            showProgressDialog(false);
                            showToast(t.getLocalizedMessage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void setupViewPager() {
        if (mAdapter == null) {
            mAdapter = new ProductFragmentPagerAdapter(getChildFragmentManager());
        }
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    protected boolean isKeepFragment() {
        return true;
    }

    @Override
    public boolean isActionShow(int resId) {
        switch (resId)
        {
            case R.id.btnEdit:
            case R.id.btnDone:
            case R.id.btnTrash:
                return false;
        }
        return true;
    }

    @Override
    public void showProgressDialog(boolean isShown) {
        layoutLoading.setVisibility(isShown ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onNetworkOnline() {
        super.onNetworkOnline();
        if (mAdapter != null && mAdapter.getCount() == 0) {
            getProductCategoryFromServer();
        }
    }
}
