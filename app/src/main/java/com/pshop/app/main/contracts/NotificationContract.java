package com.pshop.app.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;
import com.pshop.app.main.models.Notice;

import java.util.List;

public interface NotificationContract {
    interface ViewImpl extends BaseViewImpl {
        void showData(List<Notice> data);

        void addData(List<Notice> data);

        void updateView(Notice notice);

        void showMessageNotLogin();
    }

    interface Presenter extends BasePresenterImpl {
        void loadData(boolean isRefresh);

        void read(Notice notice);
    }
}
