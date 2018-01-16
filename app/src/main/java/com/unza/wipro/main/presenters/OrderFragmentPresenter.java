package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.main.contracts.OrderListContract;
import com.unza.wipro.main.models.OrderClass;
import com.unza.wipro.main.models.OrderData;

import java.util.List;

public class OrderFragmentPresenter extends BasePresenter<OrderListContract.ViewImpl>  implements OrderListContract.Presenter {


    @Override
    public List<OrderClass> loadData() {
        OrderData orderData = new OrderData();
        return orderData.getData();
    }
}
