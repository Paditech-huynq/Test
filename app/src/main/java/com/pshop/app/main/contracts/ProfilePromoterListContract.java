package com.pshop.app.main.contracts;


import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;
import com.pshop.app.transaction.user.Promoter;

import java.util.List;

public interface ProfilePromoterListContract {
    interface ViewImpl extends BaseViewImpl {
        void addItemToList(List<Promoter> customerList);
        String getCurrentKeyWord();
        void refreshData(List<Promoter> customerList);

        void scrollToTop();
    }

    interface Presenter extends BasePresenterImpl {
        void searchByKeyWord();
        void onLoadMore();
        void onRefresh();
    }
}
