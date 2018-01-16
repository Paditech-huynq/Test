package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.main.contracts.OrderListContract;
import com.unza.wipro.main.models.OrderData;

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
}
