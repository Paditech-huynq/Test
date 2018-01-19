package com.unza.wipro.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;
import com.unza.wipro.main.models.OrderClass;

import java.util.Date;
import java.util.List;

public interface OrderListContract {
    interface ViewImpl extends BaseViewImpl {
        void updateRecycleView(List<OrderClass> data);
        void updateFilterAppearance();
        void dismissFilter();
        void changeColorButtonAll();
        void changeColorButtonThisWeek();
        void changeColorButtonLastWeek();
        void changeColorButtonThisMonth();
        void updateDayInFilter(String toDay);
        void displayDatePicker(int whatCalenderInFilter, int day, int month, int year);
        void displayDateChose(int whatCalenderInFilter, int day, int month, int year);
    }

    interface Presenter extends BasePresenterImpl {
        void loadData();
        void onFilterClick();
        void onSearchClick();
        void onBtAllClick();
        void onBtThisWeekClick();
        void onBtLastWeekClick();
        void onBtThisMonthClick();
        void onBtCalenderClick(int whatCalenderInFilter, String dayCalenderFilter);
        void onChooseDate(int whatCalenderInFilter, int day, int month, int year);
    }
}
