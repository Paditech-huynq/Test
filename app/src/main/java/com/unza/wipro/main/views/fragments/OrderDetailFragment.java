package com.unza.wipro.main.views.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.paditech.core.BaseFragment;
import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.helper.ViewHelper;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.CartItemsAdapter;
import com.unza.wipro.main.models.Cart;
import com.unza.wipro.main.views.customs.VerticalSpacesItemDecoration;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderDetailFragment extends BaseFragment {
    @BindView(R.id.rcvProduct)
    RecyclerView mRecyclerView;
    @BindView(R.id.bottomBar)
    View bottomBar;

    public enum ViewMode {
        CREATE_MODE, SEE_MODE
    }
    private ViewMode viewMode;

    private int scrollX, scrollY;

    private CartItemsAdapter mAdapter;

    public static OrderDetailFragment newInstance() {

        Bundle args = new Bundle();

        OrderDetailFragment fragment = new OrderDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static OrderDetailFragment newInstance(ViewMode viewMode) {
        OrderDetailFragment fragment = newInstance();
        fragment.viewMode = viewMode;
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
        setupCreateCart();
    }

    private void setupCreateCart() {
        bottomBar.setVisibility(viewMode == ViewMode.CREATE_MODE ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean isActionShow(int resId) {
        if (resId == R.id.btnTrash) {
            return (viewMode == ViewMode.CREATE_MODE);
        }
        return super.isActionShow(resId);
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
                mRecyclerView.scrollBy(scrollX, scrollY);
            }
        });
    }

    @Override
    public void onDestroyView() {
        scrollX = mRecyclerView.computeHorizontalScrollOffset();
        scrollY = mRecyclerView.computeVerticalScrollOffset();
        super.onDestroyView();
    }

    @OnClick(R.id.btnScan)
    void onScanBtnClick() {
        switchFragment(ScannerFragment.newInstance(), true);
    }

    @OnClick(R.id.btnSubmit)
    void onSubmitBtnClick() {
        Cart.getInstance().clear();
    }

    @OnClick(R.id.btnLookup)
    void onLookupBtnClick() {
        switchFragment(LookupFragment.newInstance(), true);
    }
}
