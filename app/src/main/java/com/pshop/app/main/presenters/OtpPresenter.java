package com.pshop.app.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.pshop.app.main.contracts.OtpContract;

public class OtpPresenter extends BasePresenter<OtpContract.ViewImpl> implements OtpContract.Presenter {

    @Override
    public void confirmOtp(String otp) {
        // TODO: confirm code
        try {
            getView().onConfirmOtpResult(true, otp, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
