package com.unza.wipro.main.views.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    @BindView(R.id.rcvOrder)
    RecyclerView rcvOrder;
    @BindView(R.id.view_up_rcv)
    View viewUpRecycleView;
    @BindView(R.id.filter)
    LinearLayout filter;
    @BindView(R.id.card_view_header)
    CardView cardViewHeader;
    @BindView(R.id.bt_all)
    Button btnAll;
    @BindView(R.id.bt_lastweek)
    Button btnLastWeek;
    @BindView(R.id.bt_this_week)
    Button btnThisWeek;
    @BindView(R.id.bt_thismonth)
    Button btnThisMonth;
    @BindView(R.id.tv_calender_left_filter)
    TextView tvCalenderLeftFilter;
    @BindView(R.id.tv_calender_right_filter)
    TextView tvCalenderRightFilter;
    @BindView(R.id.tv_time_in_header_filter)
    TextView tvTimeInHeaderFilter;

    private OrderListAdapter mAdapter = new OrderListAdapter();
    private static final int DAY_LEFT_CALENDER_FILTER = 0;
    private static final int DAY_RIGHT_CALENDER_FILTER = 1;

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
        mAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleViewAdapter.BaseViewHolder holder, View view, int position) {
                getPresenter().onItemClick();
            }
        });
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
            appearFilter();
        } else {
            dismissFilter();
        }
    }

    @Override
    public void dismissFilter() {
        Animation slideUp = AnimationUtils.loadAnimation(this.getContext(), R.anim.slide_up);
        viewUpRecycleView.setVisibility(View.GONE);
        filter.setAnimation(slideUp);
        filter.setVisibility(View.GONE);
        cardViewHeader.setElevation(getResources().getDimensionPixelOffset(R.dimen.cardview_default_elevation));
    }

    @Override
    public void appearFilter() {
        Animation slideDown = AnimationUtils.loadAnimation(this.getContext(), R.anim.slide_down);
        ColorDrawable[] color = {new ColorDrawable(Color.TRANSPARENT), new ColorDrawable(getResources().getColor(R.color.bg_view_up_recycle_view_screen_list_order))};
        TransitionDrawable trans = new TransitionDrawable(color);
        viewUpRecycleView.setBackground(trans);
        trans.startTransition(500);
        viewUpRecycleView.setVisibility(View.VISIBLE);
        filter.startAnimation(slideDown);
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
    public void updateDayInFilter(String dayInLeft, String dayInRight) {
        tvCalenderLeftFilter.setText(dayInLeft);
        tvCalenderRightFilter.setText(dayInRight);
    }

    @Override
    public void displayDatePicker(final int whatCalenderInFilter, int today, int thisMonth, int thisYear) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(OrderListFragment.this.getContext(), R.style.Theme_AppCompat_Light_Dialog,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        getPresenter().onChooseDate(whatCalenderInFilter, dayOfMonth, monthOfYear + 1, year);
                    }
                }, thisYear, thisMonth - 1, today);
        datePickerDialog.show();
    }

    @Override
    public void displayDateChose(int whatCalenderInFilter, String day) {
        switch (whatCalenderInFilter) {
            case DAY_LEFT_CALENDER_FILTER:
                tvCalenderLeftFilter.setText(day);
                break;
            case DAY_RIGHT_CALENDER_FILTER:
                tvCalenderRightFilter.setText(day);
                break;
        }
        updateDayInHeaderOfFilter(tvCalenderLeftFilter.getText().toString(),tvCalenderRightFilter.getText().toString());
    }

    @Override
    public void goToOrderDetailScreen() {
        switchFragment(OrderDetailFragment.newInstance(), true);
    }

    @Override
    public void updateDayInHeaderOfFilter(String firstDayInMonth, String lastDayInMonth) {
        tvTimeInHeaderFilter.setText(getResources().getString(R.string.display_time_day_month_year_in_header_filter,firstDayInMonth,
                lastDayInMonth));
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

    @OnClick(R.id.bt_this_week)
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

    @OnClick(R.id.btn_calender_left_filter)
    public void onBtCalenderLeftClick() {
        getPresenter().onBtCalenderClick(DAY_LEFT_CALENDER_FILTER, tvCalenderLeftFilter.getText().toString());
    }

    @OnClick(R.id.btn_calender_right_filter)
    public void onBtCalenderRightClick() {
        getPresenter().onBtCalenderClick(DAY_RIGHT_CALENDER_FILTER, tvCalenderRightFilter.getText().toString());
    }

    @OnClick(R.id.view_up_rcv)
    public void onRecycleViewWhenDisTouchClick(){
        getPresenter().onRecycleViewWhenDisTouchClick();
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

    @Override
    public boolean isActionShow(int resId) {
        return true;
    }
}
