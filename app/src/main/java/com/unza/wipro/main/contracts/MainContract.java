package com.unza.wipro.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;

public interface MainContract {
    interface ViewImpl extends BaseViewImpl {
        void updateCartCount();
    }

    interface Presenter extends BasePresenterImpl {
    }
}
