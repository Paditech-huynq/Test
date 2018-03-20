package com.unza.wipro.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;
import com.unza.wipro.transaction.user.Customer;

import java.util.List;

public interface ProfileListContract {
    interface ViewImpl extends BaseViewImpl {
        void addItemToList(List<Customer> customerList);

        String getCurrentKeyWord();

        void showMessageNoResult(boolean b);

        void scrollToTop();
    }

    interface Presenter extends BasePresenterImpl {
        void onLoadMore();

        void onRefresh();

        void searchByKeyWord();
    }
}
