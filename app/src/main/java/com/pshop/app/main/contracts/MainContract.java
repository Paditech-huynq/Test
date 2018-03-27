package com.pshop.app.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;

public interface MainContract {
    interface ViewImpl extends BaseViewImpl {
        void updateCartCount();

        void updateNoticeCount();
    }

    interface Presenter extends BasePresenterImpl {
        void getUnreadNoticeCount();
    }
}
