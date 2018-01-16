package com.unza.wipro.main.views.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.mvp.MVPFragment;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.OrderListAdapter;
import com.unza.wipro.main.contracts.OrderListContract;
import com.unza.wipro.main.models.OrderClass;
import com.unza.wipro.main.presenters.OrderFragmentPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderListFragment extends MVPFragment<OrderFragmentPresenter> implements OrderListContract.ViewImpl {

    private OrderListAdapter mAdapter = new OrderListAdapter();

    @BindView(R.id.rcvOrder)
    RecyclerView rcvOrder;
    @BindView(R.id.bt_filter)
    ImageButton bt_filter;
    @BindView(R.id.view_up_rcv)
    View view_up;
    @BindView(R.id.filter)
    LinearLayout filter;
    @BindView(R.id.bt_search)
    CardView bt_search;
    @BindView(R.id.bt_all)
    CardView bt_all;
    @BindView(R.id.bt_lastweek)
    CardView bt_lastweek;
    @BindView(R.id.bt_thisweek)
    CardView bt_thisweek;
    @BindView(R.id.bt_thismonth)
    CardView bt_thismonth;

    public static OrderListFragment newInstance() {

        Bundle args = new Bundle();

        OrderListFragment fragment = new OrderListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void getData(List<OrderClass> data) {
        rcvOrder.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter.addData(data);
        mAdapter.setOnLoadMoreListener(new BaseRecycleViewAdapter.LoadMoreListener() {
            @Override
            public void onLoadMore() {
            }
        });
        rcvOrder.setAdapter(mAdapter);
    }

    @OnClick(R.id.bt_filter)
    public void onFilterClick() {
        if (filter.getVisibility() == View.GONE) {
            view_up.setVisibility(View.VISIBLE);
            filter.setVisibility(View.VISIBLE);
        } else {
            view_up.setVisibility(View.GONE);
            filter.setVisibility(View.GONE);
        }
        getPresenter().onFilterClick();
    }

    @OnClick(R.id.bt_search)
    public void onSearchClick() {
        view_up.setVisibility(View.GONE);
        filter.setVisibility(View.GONE);
        getPresenter().onSearchClick();
    }

    @OnClick(R.id.bt_all)
    public void onBtAllClick() {
                bt_all.setCardBackgroundColor(getResources().getColor(R.color.text_orange));
                bt_lastweek.setCardBackgroundColor(getResources().getColor(R.color.gray));
                bt_thisweek.setCardBackgroundColor(getResources().getColor(R.color.gray));
                bt_thismonth.setCardBackgroundColor(getResources().getColor(R.color.gray));
                getPresenter().onBtAllClick();
    }

    @OnClick(R.id.bt_thisweek)
    public void onBtThisWeekClick() {
                bt_all.setCardBackgroundColor(getResources().getColor(R.color.gray));
                bt_lastweek.setCardBackgroundColor(getResources().getColor(R.color.gray));
                bt_thisweek.setCardBackgroundColor(getResources().getColor(R.color.text_orange));
                bt_thismonth.setCardBackgroundColor(getResources().getColor(R.color.gray));
                getPresenter().onBtThisWeekClick();
    }

    @OnClick(R.id.bt_lastweek)
    public void onBtLastWeekClick() {
                bt_all.setCardBackgroundColor(getResources().getColor(R.color.gray));
                bt_lastweek.setCardBackgroundColor(getResources().getColor(R.color.text_orange));
                bt_thisweek.setCardBackgroundColor(getResources().getColor(R.color.gray));
                bt_thismonth.setCardBackgroundColor(getResources().getColor(R.color.gray));
                getPresenter().onBtLastWeekClick();
    }

    @OnClick(R.id.bt_thismonth)
    public void onBtThisMonthClick() {
                bt_all.setCardBackgroundColor(getResources().getColor(R.color.gray));
                bt_lastweek.setCardBackgroundColor(getResources().getColor(R.color.gray));
                bt_thisweek.setCardBackgroundColor(getResources().getColor(R.color.gray));
                bt_thismonth.setCardBackgroundColor(getResources().getColor(R.color.text_orange));
                getPresenter().onBtThisMonthClick();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home_order;
    }

    @Override
    public String getScreenTitle() {
        return getString(R.string.title_home_order);
    }

    @Override
    public void setScreenTitle(String title) {
    }

    @Override
    public void initView() {
        super.initView();
    }

    protected boolean isKeepFragment() {
        return true;
    }

}
