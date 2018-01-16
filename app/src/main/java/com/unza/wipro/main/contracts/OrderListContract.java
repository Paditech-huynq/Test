package com.unza.wipro.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;
import com.unza.wipro.main.models.OrderClass;

import java.util.List;

public interface OrderListContract {
    interface ViewImpl extends BaseViewImpl {
        void getData(List<OrderClass> data);
    }

    interface Presenter extends BasePresenterImpl {
        void loadData();
        void onFilterClick();
        void onSearchClick();
        void onBtAllClick();
        void onBtThisWeekClick();
        void onBtLastWeekClick();
        void onBtThisMonthClick();
    }
}
