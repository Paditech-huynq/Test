package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.OrderListContract;
import com.unza.wipro.main.models.OrderData;
import com.unza.wipro.utils.DateTimeUtils;

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
        getView().updateDayInFilter(DateTimeUtils.getStringFirstDayInCurrentMonth(getView().getContext()),DateTimeUtils.getStringLastDayInCurrentMonth(getView().getContext()));
        getView().changeColorButtonThisMonth();
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
        getView().updateDayInFilter(DateTimeUtils.getStringFirstDayInCurrentWeek(getView().getContext()),DateTimeUtils.getStringLastDayInCurrentWeek(getView().getContext()));
        getView().changeColorButtonThisWeek();
    }

    @Override
    public void onBtLastWeekClick() {
        getView().changeColorButtonLastWeek();
        getView().updateDayInFilter(DateTimeUtils.getStringFirstDayInLastWeek(getView().getContext()),DateTimeUtils.getStringLastDayInLastWeek(getView().getContext()));
        getView().changeColorButtonLastWeek();
    }

    @Override
    public void onBtThisMonthClick() {
        getView().changeColorButtonThisMonth();
        getView().updateDayInFilter(DateTimeUtils.getStringFirstDayInCurrentMonth(getView().getContext()),DateTimeUtils.getStringLastDayInCurrentMonth(getView().getContext()));
        getView().changeColorButtonThisMonth();
    }

    @Override
    public void onBtCalenderClick(int whatCalenderInFilter, String dayCalenderFilter) {
        String[] time = dayCalenderFilter.split("/");
        int day = Integer.parseInt(time[0]);
        int month = Integer.parseInt(time[1]);
        int year = Integer.parseInt(time[2]);
        getView().displayDatePicker(whatCalenderInFilter,day,month,year);
        getView().changeColorButtonToDefault();
    }

    @Override
    public void onChooseDate(int whatCalenderInFilter, int day, int month, int year) {
        getView().displayDateChose(whatCalenderInFilter, DateTimeUtils.getStringDayMonthYear(getView().getContext().getResources().getString(R.string.display_time_day_month_year,day,month,year)));
    }

    @Override
    public void onItemClick() {
        getView().goToOrderDetailScreen();
    }

    @Override
    public void onRecycleViewWhenDisTouchClick() {
        getView().dismissFilter();
    }
}
