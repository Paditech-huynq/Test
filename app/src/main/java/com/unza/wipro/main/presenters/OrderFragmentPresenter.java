package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.main.contracts.OrderListContract;
import com.unza.wipro.main.models.OrderClass;
import com.unza.wipro.main.models.OrderData;

import java.util.List;

public class OrderFragmentPresenter extends BasePresenter<OrderListContract.ViewImpl>  implements OrderListContract.Presenter {
    @Override
    public void loadData() {
        OrderData orderData = new OrderData();
        getView().getData(orderData.getData());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loadData();
    }

    @Override
    public void onFilterClick() {

    }

    @Override
    public void onSearchClick() {

    }

    @Override
    public void onBtAllClick() {

    }

    @Override
    public void onBtThisWeekClick() {

    }

    @Override
    public void onBtLastWeekClick() {

    }

    @Override
    public void onBtThisMonthClick() {

    }


}
