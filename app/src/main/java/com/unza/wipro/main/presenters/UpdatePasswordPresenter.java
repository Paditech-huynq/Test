package com.unza.wipro.main.presenters;

import com.paditech.core.BaseFragment;
import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.UpdatePasswordContract;
import com.unza.wipro.main.models.responses.ChangePasswordRSP;
import com.unza.wipro.services.AppClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePasswordPresenter extends BasePresenter<UpdatePasswordContract.ViewImpl> implements UpdatePasswordContract.Presenter, AppConstans {
    private boolean isPending;

    @Override
    public void onChangePasswordButtonClick(String oldPass, String newPass, String confirmPass) {
        requestUpdatePasswordToServer(oldPass, newPass, confirmPass);
    }

    private void requestUpdatePasswordToServer(String oldPass, String newPass, String confirmPass) {
        if (isPending) {
            return;
        }
        isPending = true;
        getView().showProgressDialog(true);
        AppClient.newInstance().getService().changePassword(
                app.getToken(),
                app.getAppKey(),
                oldPass, newPass, confirmPass)
                .enqueue(new Callback<ChangePasswordRSP>() {
                    @Override
                    public void onResponse(Call<ChangePasswordRSP> call, Response<ChangePasswordRSP> response) {
                        try {
                            isPending = false;
                            if (getView() != null) {
                                getView().showProgressDialog(false);
                            }
                            if (response.body() == null) {
                                getView().showToast(getView().getContext().getString(R.string.message_change_pass_failure));
                                return;
                            }
                            if (response.body().getResult() == Api.Success) {
                                getView().showToast(getView().getContext().getString(R.string.message_change_pass_success));
                                ((BaseFragment) getView()).getActivity().onBackPressed();
                            } else {
                                getView().showToast(response.body().getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ChangePasswordRSP> call, Throwable t) {
                        try {
                            isPending = false;
                            if (getView() != null) {
                                getView().showProgressDialog(false);
                                getView().showToast(getView().getContext().getString(R.string.message_change_pass_failure));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
