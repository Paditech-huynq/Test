package com.unza.wipro.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;
import com.unza.wipro.main.models.User_;

public interface ProfileContract {
    interface ViewImpl extends BaseViewImpl {
        void goToChangePassFragment();
        void goToOrderFragment();
        void goToListProfileFragment();
        void goToHomeProfile();
        void updateUI(User_ dummyData);
    }

    interface Presenter extends BasePresenterImpl {
        void getUserDataFromServer();
        void onChangePassClick();
        void onListOrderClick();
        void onManagerSalesClick();
        void onLogOutClick();
    }
}
