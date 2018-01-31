package com.unza.wipro.main.views.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.helper.StringUtil;
import com.paditech.core.mvp.MVPFragment;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.OrderListAdapter;
import com.unza.wipro.main.contracts.OrderListContract;
import com.unza.wipro.main.models.Order;
import com.unza.wipro.main.presenters.OrderFragmentPresenter;
import com.unza.wipro.main.views.customs.VerticalSpacesItemDecoration;
import com.unza.wipro.utils.Utils;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderListFragment extends MVPFragment<OrderFragmentPresenter> implements OrderListContract.ViewImpl {
    @BindView(R.id.layoutLoading)
    View layoutLoading;

    @BindView(R.id.rcvOrder)
    RecyclerView rcvOrder;

    @BindView(R.id.view_up_rcv)
    View viewUpRecycleView;

    @BindView(R.id.card_view_header)
    CardView cardViewHeader;

    @BindView(R.id.btb_all)
    Button btnAll;

    @BindView(R.id.btn_last_week)
    Button btnLastWeek;

    @BindView(R.id.btn_this_week)
    Button btnThisWeek;

    @BindView(R.id.btn_this_month)
    Button btnThisMonth;

    @BindView(R.id.filter)
    LinearLayout filter;

    @BindView(R.id.tv_calender_left_filter)
    TextView tvCalenderLeftFilter;

    @BindView(R.id.tv_calender_right_filter)
    TextView tvCalenderRightFilter;

    @BindView(R.id.tv_time_in_header_filter)
    TextView tvTimeInHeaderFilter;

    private OrderListAdapter mAdapter;
    private static final int DAY_LEFT_CALENDER_FILTER = 0;
    private static final int DAY_RIGHT_CALENDER_FILTER = 1;
    public static final int COME_FROM_PROFILE_FRAGMENT = 0;
    private int comFromWhatFragment = -1;

    public static OrderListFragment newInstance() {

        Bundle args = new Bundle();

        OrderListFragment fragment = new OrderListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static OrderListFragment newInstance(int comeFromWhatScreen) {

        Bundle args = new Bundle();

        OrderListFragment fragment = new OrderListFragment();
        fragment.comFromWhatFragment = comeFromWhatScreen;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        setupRecycleView();
        if (filter.getVisibility() == View.GONE) {
            enablePullToRefresh(true);
        } else {
            enablePullToRefresh(false);
        }
        setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().onRefresh();
            }
        });
    }

    @Override
    public void addItemToList(List<Order> orders) {
        mAdapter.insertData(orders);
    }

    @Override
    public void refreshData(List<Order> orders) {
        mAdapter.replaceData(orders);
    }

    public void setupRecycleView() {
        if (mAdapter == null) {
            mAdapter = new OrderListAdapter(OrderListFragment.this.getContext());
        }
        rcvOrder.addItemDecoration(new VerticalSpacesItemDecoration(getResources().getDimensionPixelOffset(R.dimen.padding_normal)));
        rcvOrder.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter.setOnLoadMoreListener(new BaseRecycleViewAdapter.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                getPresenter().onLoadMore();
            }
        });
        mAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleViewAdapter.BaseViewHolder holder, View view, int position) {
                if (mAdapter.getItem(position) instanceof Order) {
                    getPresenter().onItemClick(position);
                }
            }
        });
        rcvOrder.setAdapter(mAdapter);
    }

    @Override
    public void updateFilterAppearance() {
        if (filter.getVisibility() == View.GONE) {
            appearFilter();
        } else {
            dismissFilter();
        }
    }

    @Override
    public void dismissFilter() {
        OrderListFragment.this.enablePullToRefresh(true);
        viewUpRecycleView.setVisibility(View.GONE);
        filter.setAnimation(AnimationUtils.loadAnimation(this.getContext(), R.anim.slide_up));
        filter.setVisibility(View.GONE);
        cardViewHeader.setElevation(getResources().getDimensionPixelOffset(R.dimen.cardview_default_elevation));
    }

    @Override
    public void appearFilter() {
        OrderListFragment.this.enablePullToRefresh(false);
        viewUpRecycleView.setBackground(Utils.getTransitionChangeColor(Color.TRANSPARENT, R.color.bg_view_up_recycle_view_screen_list_order));
        viewUpRecycleView.setVisibility(View.VISIBLE);
        filter.startAnimation(AnimationUtils.loadAnimation(this.getContext(), R.anim.slide_down));
        filter.setVisibility(View.VISIBLE);
        cardViewHeader.setElevation(0);
    }

    @Override
    public void changeColorButtonAll() {
        btnAll.setSelected(true);
        btnThisWeek.setSelected(false);
        btnLastWeek.setSelected(false);
        btnThisMonth.setSelected(false);
    }

    @Override
    public void changeColorButtonThisWeek() {
        btnAll.setSelected(false);
        btnThisWeek.setSelected(true);
        btnLastWeek.setSelected(false);
        btnThisMonth.setSelected(false);
    }

    @Override
    public void changeColorButtonLastWeek() {
        btnAll.setSelected(false);
        btnThisWeek.setSelected(false);
        btnLastWeek.setSelected(true);
        btnThisMonth.setSelected(false);
    }

    @Override
    public void changeColorButtonThisMonth() {
        btnAll.setSelected(false);
        btnThisWeek.setSelected(false);
        btnLastWeek.setSelected(false);
        btnThisMonth.setSelected(true);
    }

    @Override
    public void updateDayInFilter(String from, String to) {
        tvCalenderLeftFilter.setText(from);
        tvCalenderRightFilter.setText(to);
        if (StringUtil.isEmpty(from) && StringUtil.isEmpty(to)) {
            tvTimeInHeaderFilter.setText("");
            return;
        }
        tvTimeInHeaderFilter.setText(getResources().getString(R.string.display_time_day_month_year_in_header_filter, from,
                to));
    }

    @Override
    public void displayDatePicker(final int whatCalenderInFilter, int today, int thisMonth, int thisYear) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(OrderListFragment.this.getContext(), R.style.Theme_AppCompat_Light_Dialog,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.YEAR, year);
                        getPresenter().onChooseDate(whatCalenderInFilter, calendar.getTime());
                    }
                }, thisYear, thisMonth - 1, today);
        datePickerDialog.show();
    }

    @Override
    public void displayDateChose(int whatCalenderInFilter, String day) {
        switch (whatCalenderInFilter) {
            case DAY_LEFT_CALENDER_FILTER:
                updateDayInFilter(day, tvCalenderRightFilter.getText().toString());
                break;
            case DAY_RIGHT_CALENDER_FILTER:
                updateDayInFilter(tvCalenderLeftFilter.getText().toString(), day);
                break;
        }
    }

    @Override
    public void goToOrderDetailScreen(int position) {
        OrderDetailFragment orderDetailFragment = OrderDetailFragment
                .newInstance(((Order) mAdapter.getItem(position)).getId());
        OrderListFragment.this.switchFragment(orderDetailFragment, true);
    }

    @Override
    public void changeColorButtonToDefault() {
        btnAll.setSelected(false);
        btnThisWeek.setSelected(false);
        btnLastWeek.setSelected(false);
        btnThisMonth.setSelected(false);
    }

    @Override
    public void findOrder(boolean canFind) {
        if (canFind) {
            //todo
        } else {
            showToast(getResources().getString(R.string.noti_when_choose_day_in_filter_false));
        }
    }

    @Override
    public void scrollToTop() {
        rcvOrder.postDelayed(new Runnable() {
            @Override
            public void run() {
                rcvOrder.scrollTo(0, 0);
            }
        }, 200);
    }

    @Override
    public String getFrom() {
        return tvCalenderLeftFilter.getText().toString();
    }

    @Override
    public String getTo() {
        return tvCalenderRightFilter.getText().toString();
    }


    @OnClick(R.id.tv_time_in_header_filter)
    public void onTvTimeFilterClick() {
        getPresenter().onFilterClick();
    }

    @OnClick(R.id.bt_filter)
    public void onFilterClick() {
        getPresenter().onFilterClick();
    }

    @OnClick(R.id.btn_search)
    public void onSearchClick() {
        getPresenter().onSearch(tvCalenderLeftFilter.getText().toString(), tvCalenderRightFilter.getText().toString());
    }

    @OnClick(R.id.btb_all)
    public void onBtAllClick() {
        getPresenter().onBtAllClick();
    }

    @OnClick(R.id.btn_this_week)
    public void onBtThisWeekClick() {
        getPresenter().onBtThisWeekClick();
    }

    @OnClick(R.id.btn_last_week)
    public void onBtLastWeekClick() {
        getPresenter().onBtLastWeekClick();
    }

    @OnClick(R.id.btn_this_month)
    public void onBtThisMonthClick() {
        getPresenter().onBtThisMonthClick();
    }

    @OnClick(R.id.btn_calender_left_filter)
    public void onBtCalenderLeftClick() {
        getPresenter().onBtCalenderClick(DAY_LEFT_CALENDER_FILTER, tvCalenderLeftFilter.getText().toString());
    }

    @OnClick(R.id.btn_calender_right_filter)
    public void onBtCalenderRightClick() {
        getPresenter().onBtCalenderClick(DAY_RIGHT_CALENDER_FILTER, tvCalenderRightFilter.getText().toString());
    }

    @OnClick(R.id.view_up_rcv)
    public void onRecycleViewWhenDisTouchClick() {
        getPresenter().onUserTouchOutside();
    }

    @OnClick(R.id.tv_calender_left_filter)
    public void onTvCalenderLeftFilterClick() {
        getPresenter().onBtCalenderClick(DAY_LEFT_CALENDER_FILTER, tvCalenderLeftFilter.getText().toString());
    }

    @OnClick(R.id.tv_calender_right_filter)
    public void onTvCalenderRightFilterClick() {
        getPresenter().onBtCalenderClick(DAY_RIGHT_CALENDER_FILTER, tvCalenderRightFilter.getText().toString());
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

//    protected boolean isKeepFragment() {
//        return true;
//    }

    @Override
    public boolean isActionShow(int resId) {
        if (resId == R.id.btnTrash) {
            return false;
        }
        if (comFromWhatFragment == COME_FROM_PROFILE_FRAGMENT) {
            switch (resId) {
                case R.id.imvAvatar:
                case R.id.btnNotification:
                case R.id.btnCart:
                    return false;
            }
        }
        return true;
    }

    @Override
    public void showProgressDialog(boolean isShown) {
        layoutLoading.setVisibility(isShown ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onNetworkOnline() {
        super.onNetworkOnline();
        if (mAdapter.getItemCount() == 0) {
            getPresenter().onRefresh();
        }
    }
}
