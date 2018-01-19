package com.unza.wipro.main.views.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.paditech.core.BaseFragment;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.ProductFragmentPagerAdapter;
import com.unza.wipro.main.models.ProductCategory;
import com.unza.wipro.main.models.responses.GetProductCategoryRSP;
import com.unza.wipro.services.AppClient;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListFragment extends BaseFragment {
    @BindView(R.id.vpgProduct)
    ViewPager mViewPager;

    @BindView(R.id.tabCategory)
    TabLayout mTabLayout;

    private ProductFragmentPagerAdapter mAdapter;
    private int currentPage;

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
                        GetProductCategoryRSP productCategoryRSP = response.body();
                        List<ProductCategory> productCategories = productCategoryRSP.getData();
                        mAdapter.setProductCategories(productCategories);
                        mAdapter.notifyDataSetChanged();
                        showProgressDialog(false);
                    }

                    @Override
                    public void onFailure(Call<GetProductCategoryRSP> call, Throwable t) {
                        showProgressDialog(false);
                        Toast.makeText(ProductListFragment.this.getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
        if (resId == R.id.btnTrash) {
            return false;
        }
        return false;
    }
}
