package com.unza.wipro.main.views.fragments;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
    @BindView(R.id.cardview_header)
    CardView cardView_header;
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
    public void updateRecycleView(List<OrderClass> data) {
        rcvOrder.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter.addData(data);
        mAdapter.setOnLoadMoreListener(new BaseRecycleViewAdapter.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                //todo
            }
        });
        rcvOrder.setAdapter(mAdapter);
    }

    @Override
    public void updateFilterAppearance() {
        if (filter.getVisibility() == View.GONE) {
            view_up.setVisibility(View.VISIBLE);
            filter.setVisibility(View.VISIBLE);
            cardView_header.setElevation(0);
        } else {
            dismissFilter();
        }
    }

    @Override
    public void dismissFilter() {
        view_up.setVisibility(View.GONE);
        filter.setVisibility(View.GONE);
        cardView_header.setElevation(getResources().getDimensionPixelSize(R.dimen.cardview_default_elevation));
    }

    @Override
    public void changeColorButtonAll() {
        bt_all.setCardBackgroundColor(getResources().getColor(R.color.text_orange));
        bt_lastweek.setCardBackgroundColor(getResources().getColor(R.color.gray));
        bt_thisweek.setCardBackgroundColor(getResources().getColor(R.color.gray));
        bt_thismonth.setCardBackgroundColor(getResources().getColor(R.color.gray));
    }

    @Override
    public void changeColorButtonThisWeek() {
        bt_all.setCardBackgroundColor(getResources().getColor(R.color.gray));
        bt_lastweek.setCardBackgroundColor(getResources().getColor(R.color.gray));
        bt_thisweek.setCardBackgroundColor(getResources().getColor(R.color.text_orange));
        bt_thismonth.setCardBackgroundColor(getResources().getColor(R.color.gray));
    }

    @Override
    public void changeColorButtonLastWeek() {
        bt_all.setCardBackgroundColor(getResources().getColor(R.color.gray));
        bt_lastweek.setCardBackgroundColor(getResources().getColor(R.color.text_orange));
        bt_thisweek.setCardBackgroundColor(getResources().getColor(R.color.gray));
        bt_thismonth.setCardBackgroundColor(getResources().getColor(R.color.gray));
    }

    @Override
    public void changeColorButtonThisMonth() {
        bt_all.setCardBackgroundColor(getResources().getColor(R.color.gray));
        bt_lastweek.setCardBackgroundColor(getResources().getColor(R.color.gray));
        bt_thisweek.setCardBackgroundColor(getResources().getColor(R.color.gray));
        bt_thismonth.setCardBackgroundColor(getResources().getColor(R.color.text_orange));
    }

    @OnClick(R.id.bt_filter)
    public void onFilterClick() {
        getPresenter().onFilterClick();
    }

    @OnClick(R.id.bt_search)
    public void onSearchClick() {
        getPresenter().onSearchClick();
    }

    @OnClick(R.id.bt_all)
    public void onBtAllClick() {
        getPresenter().onBtAllClick();
    }

    @OnClick(R.id.bt_thisweek)
    public void onBtThisWeekClick() {
        getPresenter().onBtThisWeekClick();
    }

    @OnClick(R.id.bt_lastweek)
    public void onBtLastWeekClick() {
        getPresenter().onBtLastWeekClick();
    }

    @OnClick(R.id.bt_thismonth)
    public void onBtThisMonthClick() {
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

    protected boolean isKeepFragment() {
        return true;
    }

}
