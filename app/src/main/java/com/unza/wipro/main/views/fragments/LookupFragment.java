package com.unza.wipro.main.views.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.paditech.core.BaseFragment;
import com.unza.wipro.R;

import butterknife.BindView;

public class LookupFragment extends BaseFragment {
    public static LookupFragment newInstance() {

        Bundle args = new Bundle();

        LookupFragment fragment = new LookupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.rcvLookup)
    RecyclerView mRecyclerView;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home_lookup;
    }

    @Override
    public String getScreenTitle() {
        return getString(R.string.title_home_lookup);
    }

    @Override
    public void setScreenTitle(String title) {
    }
}
