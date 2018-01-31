package com.unza.wipro.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;
import com.unza.wipro.main.models.News;
import com.unza.wipro.transaction.user.Customer;
import com.unza.wipro.transaction.user.Promoter;
import com.unza.wipro.transaction.user.PromoterLeader;
import com.unza.wipro.transaction.user.User;

public interface ProfileContract {
    interface ViewImpl extends BaseViewImpl {
        void goToChangePassFragment();
        void goToOrderFragment();
        void goToListProfileFragment();
        void goToHomeProfile();
        void updateUI();
        void updateUIForCustomer();
        void updateUIForPromoter();
        void startUI();
        void goToPolicyPermisWeb(News news);
        void goToQuestionWeb(News news);
    }

    interface Presenter extends BasePresenterImpl {
        void getUserDataFromServer();
        void onChangePassClick();
        void onListOrderClick();
        void onManagerSalesClick();
        void onLogOutClick();
        void onCreatFragment();
        void onPolicyPermisClick();
        void onQuestionClick();
    }
}
