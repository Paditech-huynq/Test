package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.main.contracts.ForgotPasswordContract;
import com.unza.wipro.main.models.responses.CommonRSP;
import com.unza.wipro.services.AppClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordPresenter extends BasePresenter<ForgotPasswordContract.ViewImpl> implements ForgotPasswordContract.Presenter {

    @Override
    public void forgotPass(final String username) {
        getView().showProgressDialog(true);
        AppClient.newInstance().getService().forgotPassword(username).enqueue(new Callback<CommonRSP>() {
            @Override
            public void onResponse(Call<CommonRSP> call, Response<CommonRSP> response) {
                try {
                    getView().showProgressDialog(false);
                    if (response.body() != null) {
                        boolean result = response.body().isSuccess();
                        String message = response.body().isSuccess() ? response.body().getMessage() : "";
                        getView().onForgotPassResult(result, username, message);
                    }
                } catch (Exception e) {
                    getView().onForgotPassResult(false, username, "");
                }
            }

            @Override
            public void onFailure(Call<CommonRSP> call, Throwable t) {
                try {
                    getView().showProgressDialog(false);
                    getView().onForgotPassResult(false, username, "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
