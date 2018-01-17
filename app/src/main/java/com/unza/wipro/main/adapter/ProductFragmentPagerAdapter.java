package com.unza.wipro.main.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.paditech.core.BaseFragment;
import com.unza.wipro.main.models.ProductCategory;
import com.unza.wipro.main.views.fragments.ProductPageFragment;

import java.util.ArrayList;
import java.util.List;

public class ProductFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<ProductCategory> productCategories = new ArrayList<>();

    public void setProductCategories(List<ProductCategory> productCategories) {
        this.productCategories = productCategories;
    }

    public ProductFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public BaseFragment getItem(int position) {
        int category = 0;
        try {
            category = Integer.parseInt(productCategories.get(position).getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ProductPageFragment productPageFragment = ProductPageFragment.newInstance(category);
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
