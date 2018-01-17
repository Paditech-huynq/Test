package com.unza.wipro.main.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.paditech.core.BaseFragment;
import com.unza.wipro.main.models.NewsCategory;
import com.unza.wipro.main.views.fragments.NewsPageFragment;

import java.util.List;

public class NewsFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<NewsCategory> categories;

    public NewsFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setCategories(List<NewsCategory> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @Override
    public BaseFragment getItem(int position) {
        return NewsPageFragment.newInstance(categories.get(position));
    }

    @Override
    public int getCount() {
        if (categories != null)
            return categories.size();
        return 0;
    }

    public String getTitles(int pos) {
        return categories.get(pos).getName();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categories.get(position).getName();
    }

    public void onResumeFromBackStack() {

    }
}
