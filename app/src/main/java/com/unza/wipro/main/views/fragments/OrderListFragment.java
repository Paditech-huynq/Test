package com.unza.wipro.main.views.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.paditech.core.BaseFragment;
import com.paditech.core.common.BaseRecycleViewAdapter;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.OrderListAdapter;
import com.unza.wipro.main.contracts.OrderListContract;
import com.unza.wipro.main.presenters.OrderFragmentPresenter;

import butterknife.BindView;

public class OrderListFragment extends BaseFragment implements OrderListContract.ViewImpl {

    private OrderFragmentPresenter orderFragmentPresenter = new OrderFragmentPresenter();
    private OrderListAdapter adapter;

    @BindView(R.id.rcvOrder)
    RecyclerView rcvOrder;

    public void getData() {
        rcvOrder.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new OrderListAdapter(rcvOrder);
        adapter.setList(orderFragmentPresenter.LoadMore());
        adapter.setOnLoadMoreListener(new BaseRecycleViewAdapter.LoadMoreListener() {
            @Override
            public void onLoadMore() {

            }
        });
        rcvOrder.setAdapter(adapter);
    }

    public static OrderListFragment newInstance() {
        
        Bundle args = new Bundle();
        
        OrderListFragment fragment = new OrderListFragment();
        fragment.setArguments(args);
        return fragment;
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
        getData();
    }
}
