package com.unza.wipro.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;
import com.unza.wipro.main.models.Order;
import com.unza.wipro.main.models.OrderClass;
import com.unza.wipro.main.models.Product;

import java.util.Date;
import java.util.List;

public interface OrderListContract {
    interface ViewImpl extends BaseViewImpl {
        void addItemToList(List<Order> orders);
        void refreshData(List<Order> orders);
        void updateFilterAppearance();
        void dismissFilter();
        void appearFilter();
        void changeColorButtonAll();
        void changeColorButtonThisWeek();
        void changeColorButtonLastWeek();
        void changeColorButtonThisMonth();
        void updateDayInFilter(String from, String to);
        void displayDatePicker(int whatCalenderInFilter, int day, int month, int year);
        void displayDateChose(int whatCalenderInFilter, String day);
        void goToOrderDetailScreen(int position);
        void changeColorButtonToDefault();
        void findOrder(boolean canFind);
    }

    interface Presenter extends BasePresenterImpl {
        void loadData();
        void loadMore();
        void onFilterClick();
        void onSearchClick(String from, String to);
        void onBtAllClick();
        void onBtThisWeekClick();
        void onBtLastWeekClick();
        void onBtThisMonthClick();
        void onBtCalenderClick(int whatCalenderInFilter, String dayCalenderFilter);
        void onChooseDate(int whatCalenderInFilter, Date dayChose);
        void onUserTouchOutside();
        void onItemClick(int position);
    }
}
