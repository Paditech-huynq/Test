package com.unza.wipro.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;
import com.unza.wipro.main.models.User;

public interface ProfileContract {
    interface ViewImpl extends BaseViewImpl {
        void updateUI(User dummyData);
        void goToChangePassFragment();
        void goToOrderFragment();
        void goToListProfileFragment();
        void goToHomeProfile();
    }

    interface Presenter extends BasePresenterImpl {
        void getUserDataFromServer();
        void onChangePassClick();
        void onListOrderClick();
        void onManagerSalesClick();
        void onLogOutClick();
    }
}
