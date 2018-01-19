package com.unza.wipro.main.presenters;

import android.util.Log;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.OrderListContract;
import com.unza.wipro.main.models.OrderData;
import com.unza.wipro.utils.DateTimeUtils;

import java.util.Calendar;
import java.util.Date;

public class OrderFragmentPresenter extends BasePresenter<OrderListContract.ViewImpl>  implements OrderListContract.Presenter {

    @Override
    public void loadData() {
        OrderData orderData = new OrderData();
        getView().updateRecycleView(orderData.getData());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loadData();
        loadUI();
    }

    private void loadUI() {
        Date toDay = Calendar.getInstance().getTime();
        getView().updateDayInFilter(DateTimeUtils.getStringDayMonthYear(toDay));
    }

    @Override
    public void onFilterClick() {
        getView().updateFilterAppearance();
    }

    @Override
    public void onSearchClick() {
        getView().dismissFilter();
    }

    @Override
    public void onBtAllClick() {
        getView().changeColorButtonAll();
    }

    @Override
    public void onBtThisWeekClick() {
        getView().changeColorButtonThisWeek();
    }

    @Override
    public void onBtLastWeekClick() {
        getView().changeColorButtonLastWeek();

    }

    @Override
    public void onBtThisMonthClick() {
        getView().changeColorButtonThisMonth();
    }

    @Override
    public void onBtCalenderClick(int whatCalenderInFilter, String dayCalenderFilter) {
        String[] time = dayCalenderFilter.split("/");
        int day = Integer.parseInt(time[0]);
        int month = Integer.parseInt(time[1]);
        int year = Integer.parseInt(time[2]);
        getView().displayDatePicker(whatCalenderInFilter,day,month,year);
    }

    @Override
    public void onChooseDate(int whatCalenderInFilter, int day, int month, int year) {
        getView().displayDateChose(whatCalenderInFilter, DateTimeUtils.getStringDayMonthYear(getView().getContext().getResources().getString(R.string.display_time_day_month_year,day,month,year)));
    }
}
