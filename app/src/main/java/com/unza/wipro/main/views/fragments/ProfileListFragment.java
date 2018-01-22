package com.unza.wipro.main.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.helper.ViewHelper;
import com.paditech.core.mvp.MVPFragment;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.ProfileListAdapter;
import com.unza.wipro.main.contracts.ProfileListContract;
import com.unza.wipro.main.models.Customer;
import com.unza.wipro.main.presenters.ProfileListPresenter;
import com.unza.wipro.main.views.customs.VerticalSpacesItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ProfileListFragment extends MVPFragment<ProfileListPresenter> implements ProfileListContract.ViewImpl {
    @BindView(R.id.rcvProfile)
    RecyclerView mRecyclerView;

    private final static String LAST_SCROLL_Y = "last_scroll_y";

    private ProfileListAdapter mAdapter;

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
        ViewHelper.setupRecycle(mRecyclerView, new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false), mAdapter);
        mAdapter.setOnLoadMoreListener(new BaseRecycleViewAdapter.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                getPresenter().onLoadMore();
            }
        });
    }

    @OnClick(R.id.btnRegister)
    void onRegisterBtnClick() {
        switchFragment(ProfileRegisterFragment.newInstance(), true);
    }

    @Override
    public void onSaveViewInstanceState(@NonNull Bundle outState) {
        super.onSaveViewInstanceState(outState);
        outState.putInt(LAST_SCROLL_Y, mRecyclerView.computeVerticalScrollOffset());
        Log.e("AA", mRecyclerView.computeVerticalScrollOffset() + "");

    }

    @Override
    public void onRestoreViewState(@Nullable Bundle savedInstanceState) {
        super.onRestoreViewState(savedInstanceState);
        Log.e("AA", "fsdfsdf");
        if (savedInstanceState != null) {
            mRecyclerView.scrollBy(0, savedInstanceState.getInt(LAST_SCROLL_Y));
        }
    }

    @Override
    public void addItemToList(List<Customer> customerList) {
        mAdapter.addItemToList(customerList);
    }

    @Override
    public void refreshData(List<Customer> customerList) {
        mAdapter.refreshData(customerList);
    }
}
