package com.unza.wipro.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;

public interface UpdatePasswordContract {
    interface ViewImpl extends BaseViewImpl{
        void onChangePasswordResult(boolean result, String message);
    }

    interface Presenter extends BasePresenterImpl{
        void updatePassword(String oldPass, String newPass);
    }
}
