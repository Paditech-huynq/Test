package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.main.contracts.UpdatePasswordContract;

public class UpdatePasswordPresenter extends BasePresenter<UpdatePasswordContract.ViewImpl> implements UpdatePasswordContract.Presenter {
    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public void updatePassword(String oldPass, String newPass) {
        // todo: call update password api (get results and message)
        getView().onChangePasswordResult(true, "");
    }
}
