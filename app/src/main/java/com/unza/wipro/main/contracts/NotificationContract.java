package com.unza.wipro.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;

import java.util.List;

public interface NotificationContract {
    interface ViewImpl extends BaseViewImpl {
        void showData(List data);
    }

    interface Presenter extends BasePresenterImpl {
        void loadData(boolean isRefresh);
    }
}
