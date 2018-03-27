package com.pshop.app.main.views.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.helper.ViewHelper;
import com.paditech.core.mvp.MVPFragment;
import com.pshop.app.AppAction;
import com.pshop.app.AppConstans;
import com.pshop.app.R;
import com.pshop.app.main.adapter.CartItemsAdapter;
import com.pshop.app.main.contracts.OrderDetailContract;
import com.pshop.app.main.models.Order;
import com.pshop.app.main.models.UserData;
import com.pshop.app.main.presenters.OrderDetailPresenter;
import com.pshop.app.main.views.activities.LoginActivity;
import com.pshop.app.main.views.activities.MainActivity;
import com.pshop.app.main.views.customs.VerticalSpacesItemDecoration;
import com.pshop.app.transaction.cart.Cart;
import com.pshop.app.transaction.user.Customer;
import com.pshop.app.transaction.user.User;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderDetailFragment extends MVPFragment<OrderDetailPresenter> implements OrderDetailContract.ViewImpl, AppConstans {
    @BindView(R.id.rcvProduct)
    RecyclerView mRecyclerView;
    @BindView(R.id.bottomBar)
    View bottomBar;

    private int mOrderID = -1;

    private int scrollX, scrollY;

    private CartItemsAdapter mAdapter;

    public static OrderDetailFragment newInstance() {
        Bundle args = new Bundle();
        OrderDetailFragment fragment = new OrderDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static OrderDetailFragment newInstance(int orderID) {
        OrderDetailFragment fragment = newInstance();
        fragment.mOrderID = orderID;
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_order_detail;
    }

    @Override
    public String getScreenTitle() {
        if (hasOrder()) {
            return getString(R.string.order_detail_title, String.valueOf(mOrderID));
        } else {
            return getString(R.string.title_cart_detail);
        }
    }

    @Override
    public void initView() {
        super.initView();
        setupRecycleView();
        setupCreateCart();
        app.addCartChangeListener(new Cart.CartChangeListener() {
            @Override
            public void onCartUpdate() {
                try {
                    if (isVisible()) {
                        ((MainActivity) getActivity()).updateActionButtonAppearance(OrderDetailFragment.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setupCreateCart() {
        bottomBar.setVisibility(hasOrder() ? View.GONE : View.VISIBLE);

    }

    private boolean hasOrder() {
        return mOrderID > 0;
    }

    @Override
    public boolean isActionShow(int resId) {
        if (resId == R.id.btnTrash) {
            return !hasOrder() && app.getCurrentCart().getItemCount() > 0;
        }
        return super.isActionShow(resId);
    }

    @Override
    public void onActionSelected(int resId) {
        if (resId == R.id.btnTrash) {
            if (app.getCurrentCart().getItemCount() > 0) {
                showConfirmDialog(getString(R.string.msg_cornfirm_delete), getString(R.string.action_confirm), getString(R.string.action_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        app.editCart().clear();
                        mAdapter.notifyItemRangeRemoved(1, mAdapter.getItemCount() - 1);
                    }
                }, null);
            }
        }
        super.onActionSelected(resId);
    }

    @Override
    public int getOrderId() {
        return mOrderID;
    }

    @Override
    public void showOrderDetail(Order order) {
        if (order == null) {
            return;
        }
        mAdapter.updateOrder(order);
    }

    @Override
    public Customer getCustomer() {
        return mAdapter.getCustomer();
    }

    @Override
    public void setCustomer(Customer customer) {
        mAdapter.setCustomer(customer);

    }

    @Override
    public void backToLastScreen() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    private void setupRecycleView() {
        if (mAdapter == null) {
            if (hasOrder()) {
                mAdapter = new CartItemsAdapter(new Order());
            } else {
                mAdapter = new CartItemsAdapter(null);
            }
        }
        mAdapter.setOnViewClickListener(new BaseRecycleViewAdapter.ViewClickListener() {
            @Override
            public void onViewItemClock(int resId, BaseRecycleViewAdapter.BaseViewHolder holder, int position) {
                if (resId == R.id.btnChangeCustomer) {
                    switchFragment(ProfileListFragment.newInstance(), true);
                } else if (resId == R.id.btnLogin) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
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
        getPresenter().onSubmitTransactionButtonClick();
    }

    @OnClick(R.id.btnLookup)
    void onLookupBtnClick() {
        switchFragment(LookupFragment.newInstance(), true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bus.register(this);
    }

    @Override
    public void onDetach() {
        bus.unregister(this);
        super.onDetach();
    }

    @Subscribe
    public void onAction(AppAction action) {
        switch (action) {
            case NOTIFY_CUSTOMER_SELECTED:
            case NOTIFY_CUSTOMER_SELECTED_AFTER_CREATE:
                UserData userData = action.getData(UserData.class);
                final User user = new User.Builder(userData).build();
                if (user instanceof Customer) {
                    mAdapter.setCustomer((Customer) user);
                }
                break;
        }
    }

    @Override
    public void onViewAppear() {
        super.onViewAppear();
        mAdapter.notifyItemChanged(0);
    }
}
