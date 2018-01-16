package com.unza.wipro.main.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.paditech.core.BaseFragment;
import com.unza.wipro.main.views.fragments.ProductPageFragment;

public class ProductFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private String[] titles = new String[]{
            "category 1",
            "category 2",
            "category 3",
            "category 4",
            "category 5",
            "category 6",
            "category 7",
            "category 8",
            "category 9",
            "category 10",
    };

    public ProductFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public BaseFragment getItem(int position) {
        ProductPageFragment productPageFragment = ProductPageFragment.newInstance();
        return productPageFragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    public String getTitles(int pos) {
        return titles[pos];
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public void onResumeFromBackStack() {

    }
}
