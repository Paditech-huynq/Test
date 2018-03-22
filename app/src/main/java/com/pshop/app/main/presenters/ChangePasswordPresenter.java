package com.pshop.app.main.presenters;

import android.util.Log;

import com.paditech.core.helper.StringUtil;
import com.paditech.core.mvp.BasePresenter;
import com.pshop.app.main.contracts.ChangePasswordContract;
import com.pshop.app.main.models.responses.CommonRSP;
import com.pshop.app.services.AppClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/12/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class ChangePasswordPresenter extends BasePresenter<ChangePasswordContract.ViewImpl> implements ChangePasswordContract.Presenter {

    @Override
    public void changePass(String phone, String otp, String newPassword, String confirmPassword) {
        if (StringUtil.isEmpty(phone) || StringUtil.isEmpty(otp)) {
            getView().onChangePassResult(true, "");
            return;
        }
        getView().showProgressDialog(true);
        AppClient.newInstance().getService().resetPassword(phone, otp, newPassword, confirmPassword)
                .enqueue(new Callback<CommonRSP>() {
                    @Override
                    public void onResponse(Call<CommonRSP> call, Response<CommonRSP> response) {
                        try {
                            Log.e("testresetPassword", String.valueOf(response.code()));
                            getView().showProgressDialog(false);
                            if (response.body() != null) {
                                boolean result = response.body().isSuccess();
                                String message = response.body().getMessage() != null ? response.body().getMessage() : "";
                                getView().onChangePassResult(result, message);
                            } else {
                                getView().onChangePassResult(true, "");
                            }
                        } catch (Exception e) {
                            getView().onChangePassResult(true, "");
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonRSP> call, Throwable t) {
                        try {
                            getView().showProgressDialog(false);
                            getView().onChangePassResult(false, "");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
