package com.unza.wipro.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.paditech.core.BaseFragment;
import com.unza.wipro.main.views.fragments.LookupFragment;
import com.unza.wipro.main.views.fragments.NewsFragment;
import com.unza.wipro.main.views.fragments.OrderListFragment;
import com.unza.wipro.main.views.fragments.ProductListFragment;
import com.unza.wipro.main.views.fragments.ScannerFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentPagerAdapter extends FragmentStatePagerAdapter {
    List<BaseFragment> fragments = new ArrayList<>();

    public HomeFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(ProductListFragment.newInstance());
        fragments.add(OrderListFragment.newInstance());
        fragments.add(ScannerFragment.newInstance());
        fragments.add(LookupFragment.newInstance());
        fragments.add(NewsFragment.newInstance());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
