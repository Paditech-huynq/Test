package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.OrderListContract;
import com.unza.wipro.main.models.OrderData;
import com.unza.wipro.utils.DateTimeUtils;

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
        getView().updateDayInFilter(DateTimeUtils.getStringFirstDayInCurrentMonth(),DateTimeUtils.getStringLastDayInCurrentMonth());
        getView().changeColorButtonThisMonth();
    }

    @Override
    public void onFilterClick() {
        getView().updateFilterAppearance();
    }

    @Override
    public void onSearchClick(String from, String to) {
        Date startDate = DateTimeUtils.getDateFromStringDayMonthYear(from);
        Date endDate = DateTimeUtils.getDateFromStringDayMonthYear(to);
        assert startDate != null;
        if(startDate.before(endDate)) {
            getView().findOrder(true);
            getView().dismissFilter();
        } else {
            getView().findOrder(false);
        }
    }

    @Override
    public void onBtAllClick() {
        getView().changeColorButtonAll();
    }

    @Override
    public void onBtThisWeekClick() {
        getView().changeColorButtonThisWeek();
        getView().updateDayInFilter(DateTimeUtils.getStringFirstDayInCurrentWeek(),DateTimeUtils.getStringLastDayInCurrentWeek());
    }

    @Override
    public void onBtLastWeekClick() {
        getView().changeColorButtonLastWeek();
        getView().updateDayInFilter(DateTimeUtils.getStringFirstDayInLastWeek(),DateTimeUtils.getStringLastDayInLastWeek());
    }

    @Override
    public void onBtThisMonthClick() {
        getView().changeColorButtonThisMonth();
        getView().updateDayInFilter(DateTimeUtils.getStringFirstDayInCurrentMonth(),DateTimeUtils.getStringLastDayInCurrentMonth());
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
    public void onChooseDate(int whatCalenderInFilter, Date dayChose) {
        getView().displayDateChose(whatCalenderInFilter, DateTimeUtils.getStringDayMonthYear(dayChose));
    }

    @Override
    public void onItemClick() {
        getView().goToOrderDetailScreen();
    }

    @Override
    public void onUserTouchOutside() {
        getView().dismissFilter();
    }
}
