package com.unza.wipro.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;

public interface HomeContract {
    interface ViewImpl extends BaseViewImpl {
        void switchTab(int i);

        void updateTitle();

        void updateView();
    }

    interface Presenter extends BasePresenterImpl {
        void onTabSelected(int tabId);
    }
}
