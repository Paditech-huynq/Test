package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.main.contracts.OtpContract;

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
