package com.pshop.app.main.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.paditech.core.BaseFragment;
import com.pshop.app.main.models.ProductCategory;
import com.pshop.app.main.views.fragments.ProductPageFragment;

import java.util.ArrayList;
import java.util.List;

public class ProductFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<ProductCategory> productCategories = new ArrayList<>();

    public ProductFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setProductCategories(List<ProductCategory> productCategories) {
        this.productCategories = productCategories;
    }

    @Override
    public BaseFragment getItem(int position) {
        String categoryId = productCategories.get(position).getId();
        ProductPageFragment productPageFragment = ProductPageFragment.newInstance(categoryId);
        return productPageFragment;
    }

    @Override
    public int getCount() {
        return productCategories.size();
    }

    public String getTitles(int pos) {
        return productCategories.get(pos).getName();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return productCategories.get(position).getName();
    }

    public void onResumeFromBackStack() {

    }
}
