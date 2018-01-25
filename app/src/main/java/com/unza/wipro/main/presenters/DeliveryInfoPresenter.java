package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.main.contracts.DeliveryInfoContract;

public class DeliveryInfoPresenter extends BasePresenter<DeliveryInfoContract.ViewImpl> implements DeliveryInfoContract.Presenter {

    @Override
    public void submit(String name, String phone, String address, String date, String note) {
        getView().onResult(true, "");
    }
}
