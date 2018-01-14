package com.unza.wipro.main.views.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.paditech.core.BaseFragment;
import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.helper.ViewHelper;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.CartItemsAdapter;
import com.unza.wipro.main.views.customs.VerticalSpacesItemDecoration;

import butterknife.BindView;

public class OrderDetailFragment extends BaseFragment {
    @BindView(R.id.rcvProduct)
    RecyclerView mRecyclerView;

    private int scrollX, scrollY;

    private CartItemsAdapter mAdapter;

    public static OrderDetailFragment newInstance() {

        Bundle args = new Bundle();

        OrderDetailFragment fragment = new OrderDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_order_detail;
    }

    @Override
    public String getScreenTitle() {
        return "Đơn hàng số #";
    }

    @Override
    public void initView() {
        super.initView();
        setupRecycleView();

    }

    private void setupRecycleView() {
        mAdapter = new CartItemsAdapter();
        mAdapter.setOnViewClickListener(new BaseRecycleViewAdapter.ViewClickListener() {
            @Override
            public void onViewItemClock(int resId, BaseRecycleViewAdapter.BaseViewHolder holder, int position) {
                if (resId == R.id.btnChange) {
                    switchFragment(ProfileListFragment.newInstance(), true);
                }
            }
        });
        mRecyclerView.addItemDecoration(new VerticalSpacesItemDecoration(getResources().getDimensionPixelOffset(R.dimen.padding_normal)));
        ViewHelper.setupRecycle(mRecyclerView, new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false), mAdapter);
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.scrollBy(scrollX,scrollY);
            }
        });
    }

    @Override
    public void onDestroyView() {
        scrollX = mRecyclerView.computeHorizontalScrollOffset();
        scrollY = mRecyclerView.computeVerticalScrollOffset();
        super.onDestroyView();
    }
}
