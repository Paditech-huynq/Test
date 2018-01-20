package com.unza.wipro.main.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.paditech.core.BaseFragment;
import com.unza.wipro.main.views.fragments.FakeScannerFragment;
import com.unza.wipro.main.views.fragments.LookupFragment;
import com.unza.wipro.main.views.fragments.NewsFragment;
import com.unza.wipro.main.views.fragments.OrderListFragment;
import com.unza.wipro.main.views.fragments.ProductListFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragments = new ArrayList<>();

    public HomeFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(ProductListFragment.newInstance());
        fragments.add(OrderListFragment.newInstance());
        fragments.add(FakeScannerFragment.newInstance());
        fragments.add(LookupFragment.newInstance());
        fragments.add(NewsFragment.newInstance());
    }

    @Override
    public BaseFragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getScreenTitle();
    }

    public void onViewAppear(int position) {
        if (position < 0) {
            if (position == -1) {
                for (int i = 0; i < fragments.size(); i++) {
                    BaseFragment fragment = fragments.get(i);
                    if (i == -1) {
                        fragment.onViewAppear();
                    } else if (i == -2) {
                        fragment.onViewDisappear();
                    }
                }
            }
            return;
        }
        for (int i = 0; i < fragments.size(); i++) {
            BaseFragment fragment = fragments.get(i);
            if (i == position) {
                fragment.onViewAppear();
            } else {
                fragment.onViewDisappear();
            }
        }
    }
}
