package com.unza.wipro.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;

public interface UpdatePasswordContract {
    interface ViewImpl extends BaseViewImpl{
    }

    interface Presenter extends BasePresenterImpl{
        void onChangePasswordButtonClick(String oldPass, String newPass, String confirmPass);
    }
}
