package com.unza.wipro.main.views.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.helper.ViewHelper;
import com.paditech.core.mvp.MVPFragment;
import com.unza.wipro.AppState;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.CartItemsAdapter;
import com.unza.wipro.main.contracts.OrderDetailContract;
import com.unza.wipro.main.models.Order;
import com.unza.wipro.main.presenters.OrderDetailPresenter;
import com.unza.wipro.main.views.customs.VerticalSpacesItemDecoration;
import com.unza.wipro.transaction.user.Customer;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderDetailFragment extends MVPFragment<OrderDetailPresenter> implements OrderDetailContract.ViewImpl {
    @BindView(R.id.rcvProduct)
    RecyclerView mRecyclerView;
    @BindView(R.id.bottomBar)
    View bottomBar;

    private BroadcastReceiver mReceiver;

    public enum ViewMode {
        MODE_CREATE, MODE_SEE
    }

    private ViewMode viewMode;
    private Order mOrder;

    private int scrollX, scrollY;

    private CartItemsAdapter mAdapter;

    public static OrderDetailFragment newInstance() {

        Bundle args = new Bundle();

        OrderDetailFragment fragment = new OrderDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static OrderDetailFragment newInstance(ViewMode viewMode, Order order) {
        OrderDetailFragment fragment = newInstance();
        fragment.viewMode = viewMode;
        fragment.mOrder = order;
        return fragment;
    }


    private void setupReceiver() {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.MAIN");
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String customerJSON = intent.getStringExtra("customer");
                Customer customer = new Gson().fromJson(customerJSON, Customer.class);
                mAdapter.updateCustomer(customer);
            }
        };
        getActivity().registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_order_detail;
    }

    @Override
    public String getScreenTitle() {
        String id = mOrder != null ? String.valueOf(mOrder.getId()) : "";
        return getString(R.string.order_detail_title, id);
    }

    @Override
    public void initView() {
        super.initView();
        setupRecycleView();
        setupCreateCart();
        setupReceiver();

        Log.e("Cart", AppState.getInstance().getCurrentCart().getTotalPrice() + "");
    }

    private void setupCreateCart() {
        bottomBar.setVisibility(viewMode == ViewMode.MODE_CREATE ? View.VISIBLE : View.GONE);
        if (viewMode == ViewMode.MODE_SEE && mOrder != null) {
            String id = mOrder != null ? String.valueOf(mOrder.getId()) : "";
            setScreenTitle(getString(R.string.order_detail_title, id));
        }
    }

    @Override
    public boolean isActionShow(int resId) {
        if (resId == R.id.btnTrash) {
            return (viewMode == ViewMode.MODE_CREATE);
        }
        return super.isActionShow(resId);
    }

    @Override
    public void onActionSelected(int resId) {
        if (resId == R.id.btnTrash) {
            AppState.getInstance().editCart().clear();
            mAdapter.notifyDataSetChanged();
        }
        super.onActionSelected(resId);
    }

    @Override
    public Order getOrder() {
        return mOrder;
    }

    @Override
    public void showOrderDetail(Order order) {
        if (order == null) return;
        mAdapter.updateOrder(order);
    }

    private void setupRecycleView() {
        if (viewMode == ViewMode.MODE_CREATE) {
            mAdapter = new CartItemsAdapter(null);
        } else if (viewMode == ViewMode.MODE_SEE) {
            mAdapter = new CartItemsAdapter(Order.newInstance());
        }

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
        switchFragment(FakeScannerFragment.newInstance(), true);
    }

    @OnClick(R.id.btnSubmit)
    void onSubmitBtnClick() {
        //todo: implement payment logic here
        if (AppState.getInstance().getCurrentUser() instanceof Customer) {
            switchFragment(DeliveryInfoFragment.newInstance(), true);
        } else {

        }
    }

    @OnClick(R.id.btnLookup)
    void onLookupBtnClick() {
        switchFragment(LookupFragment.newInstance(), true);
    }
}
