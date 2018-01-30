package com.unza.wipro.main.views.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.helper.ViewHelper;
import com.paditech.core.mvp.MVPFragment;
import com.squareup.otto.Subscribe;
import com.unza.wipro.AppAction;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.CartItemsAdapter;
import com.unza.wipro.main.contracts.OrderDetailContract;
import com.unza.wipro.main.models.Order;
import com.unza.wipro.main.models.UserData;
import com.unza.wipro.main.presenters.OrderDetailPresenter;
import com.unza.wipro.main.views.customs.VerticalSpacesItemDecoration;
import com.unza.wipro.transaction.user.Customer;
import com.unza.wipro.transaction.user.User;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderDetailFragment extends MVPFragment<OrderDetailPresenter> implements OrderDetailContract.ViewImpl, AppConstans {
    @BindView(R.id.rcvProduct)
    RecyclerView mRecyclerView;
    @BindView(R.id.bottomBar)
    View bottomBar;

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            //Remove swiped item from list and notify the RecyclerView
        }

        @Override
        public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
            super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
        }
    };

    ItemTouchHelper itemTouchHelper;

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
        Log.e("orderId", orderID + "");
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
        enablePullToRefresh(true);
//        itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
//        itemTouchHelper.attachToRecyclerView(mRecyclerView);
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
    public void setCustomer(Customer customer) {
        mAdapter.setCustomer(customer);

    }

    @Override
    public Customer getCustomer() {
        return mAdapter.getCustomer();
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
                Log.e("Customer selected", user.getClass().getSimpleName());
                if (user instanceof Customer) {
                    mAdapter.setCustomer((Customer) user);
                }
                break;
        }
    }
}
