package com.unza.wipro.main.views.fragments;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.mvp.MVPFragment;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.NotificationAdapter;
import com.unza.wipro.main.contracts.NotificationContract;
import com.unza.wipro.main.models.Notice;
import com.unza.wipro.main.presenters.NotificationPresenter;
import com.unza.wipro.main.views.activities.MainActivity;

import java.util.List;

import butterknife.BindView;

import static com.unza.wipro.AppConstans.app;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/18/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class NotificationFragment extends MVPFragment<NotificationPresenter> implements NotificationContract.ViewImpl,
        SwipeRefreshLayout.OnRefreshListener, BaseRecycleViewAdapter.LoadMoreListener {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.rv_notification)
    RecyclerView mRecyclerView;

    private NotificationAdapter mAdapter;

    public static NotificationFragment newInstance() {
        Bundle args = new Bundle();
        NotificationFragment fragment = new NotificationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_notification;
    }

    @Override
    public String getScreenTitle() {
        return getString(R.string.notification_title);
    }

    @Override
    public void initView() {
        super.initView();
        if (mAdapter == null) {
            mAdapter = new NotificationAdapter();
        }

        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleViewAdapter.BaseViewHolder holder, View view, int position) {
                getPresenter().read(mAdapter.getItem(position));
            }
        });
    }

    @Override
    public void onViewAppear() {
        super.onViewAppear();
        updateTitle();
    }

    @Override
    public void onRefresh() {
        getPresenter().loadData(true);
    }

    @Override
    public void onLoadMore() {
        getPresenter().loadData(false);
    }

    @Override
    public void setRefreshing(boolean isComplete) {
        super.setRefreshing(isComplete);
        mSwipeRefreshLayout.setRefreshing(isComplete);
    }

    @Override
    public void showData(List<Notice> data) {
        mAdapter.setData(data);
        updateTitle();
    }

    @Override
    public void addData(List<Notice> data) {
        mAdapter.addData(data);
        updateTitle();
    }

    @Override
    public void updateView(Notice notice) {
        mAdapter.updateData(notice);
        updateTitle();
    }

    private void updateTitle() {
        int unread = mAdapter != null && mAdapter.getUnreadCount() > 0 ? mAdapter.getUnreadCount() : 0;
        String count = mAdapter != null && mAdapter.getUnreadCount() > 0 ? String.format("(%d)", mAdapter.getUnreadCount()) : "";
        app.setNotifyCount(unread);
        setScreenTitle(getString(R.string.notification_title) + count);
        ((MainActivity) getActivity()).updateNoticeCount();
    }
}
