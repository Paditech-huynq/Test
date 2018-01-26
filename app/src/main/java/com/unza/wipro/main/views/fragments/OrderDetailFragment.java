package com.unza.wipro.main.views.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.unza.wipro.transaction.DirectTransaction;
import com.unza.wipro.transaction.OrderTransaction;
import com.unza.wipro.transaction.Transaction;
import com.unza.wipro.transaction.cart.Cart;
import com.unza.wipro.transaction.user.Customer;
import com.unza.wipro.transaction.user.DeliveryInfo;
import com.unza.wipro.transaction.user.User;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private int mOrderID;

    private int scrollX, scrollY;

    private CartItemsAdapter mAdapter;
    Transaction mTransaction;

    public static OrderDetailFragment newInstance() {

        Bundle args = new Bundle();

        OrderDetailFragment fragment = new OrderDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static OrderDetailFragment newInstance(ViewMode viewMode, int orderID) {
        OrderDetailFragment fragment = newInstance();
        fragment.viewMode = viewMode;
        fragment.mOrderID = orderID;
        return fragment;
    }

    private void setupReceiver() {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.MAIN");
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String customerJSON = intent.getStringExtra("customer");
                Customer customer = new Gson().fromJson(customerJSON, Customer.class);
                mAdapter.setUser(customer);
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
        String id = mOrderID != 0 ? String.valueOf(mOrderID) : "";
        return getString(R.string.order_detail_title, id);
    }

    @Override
    public void initView() {
        super.initView();
        setupRecycleView();
        setupCreateCart();
        setupReceiver();
    }

    private void setupCreateCart() {
        bottomBar.setVisibility(viewMode == ViewMode.MODE_CREATE ? View.VISIBLE : View.GONE);
        if (viewMode == ViewMode.MODE_SEE && mOrderID != 0) {
            String id = mOrderID != 0 ? String.valueOf(mOrderID) : "";
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
    public int getOrderId() {
        return mOrderID;
    }

    @Override
    public void showOrderDetail(Order order) {
        if (order == null) return;
        mAdapter.updateOrder(order);
    }

    @Override
    public void setUser(User user) {
        mAdapter.setUser(user);
    }

    @Override
    public void onViewAppear() {
        super.onViewAppear();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onViewDisappear() {
        super.onViewDisappear();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onUpdateDeliveryInfo(DeliveryInfo info) {
        if (mTransaction != null && mTransaction instanceof OrderTransaction) {
            ((OrderTransaction) mTransaction).setDeliveryInfo(info);
        }
        EventBus.getDefault().removeStickyEvent(info);
    }

    private void setupRecycleView() {
        mAdapter = new CartItemsAdapter(viewMode);

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
            if (mTransaction == null) mTransaction = new OrderTransaction();
            mTransaction.create(1, (Cart) AppState.getInstance().getCurrentCart());
            DeliveryInfo info = ((OrderTransaction) mTransaction).getDeliveryInfo();
            if (info == null) {
                switchFragment(DeliveryInfoFragment.newInstance(), true);
            } else {
                getPresenter().submitOrder(mTransaction);
            }
        } else {
            if (mTransaction == null) mTransaction = new DirectTransaction();
            mTransaction.create(1, (Cart) AppState.getInstance().getCurrentCart());
            getPresenter().submitOrder(mTransaction);
        }
    }

    @OnClick(R.id.btnLookup)
    void onLookupBtnClick() {
        switchFragment(LookupFragment.newInstance(), true);
    }
}
