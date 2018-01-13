package com.unza.wipro.main.views.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.paditech.core.BaseFragment;
import com.paditech.core.helper.ViewHelper;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.ProfileListAdapter;
import com.unza.wipro.main.views.customs.VerticalSpacesItemDecoration;

import butterknife.BindView;

public class ProfileListFragment extends BaseFragment {
    @BindView(R.id.rcvProfile)
    RecyclerView mRecyclerView;

    ProfileListAdapter mAdapter;

    public static ProfileListFragment newInstance() {

        Bundle args = new Bundle();

        ProfileListFragment fragment = new ProfileListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_profile_list;
    }

    @Override
    public String getScreenTitle() {
        return getString(R.string.title_profile_select);
    }

    @Override
    public void initView() {
        super.initView();
        setupRecycleView();
    }

    private void setupRecycleView() {
        mAdapter = new ProfileListAdapter();
        mRecyclerView.addItemDecoration(new VerticalSpacesItemDecoration(getResources().getDimensionPixelOffset(R.dimen.padding_normal)));
        ViewHelper.setupRecycle(mRecyclerView,new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false),mAdapter);
    }
}
