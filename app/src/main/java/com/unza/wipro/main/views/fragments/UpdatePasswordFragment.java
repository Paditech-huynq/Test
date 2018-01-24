package com.unza.wipro.main.views.fragments;

import android.os.Bundle;
import android.widget.EditText;

import com.paditech.core.helper.StringUtil;
import com.paditech.core.mvp.MVPFragment;
import com.unza.wipro.AppState;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.UpdatePasswordContract;
import com.unza.wipro.main.presenters.UpdatePasswordPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdatePasswordFragment extends MVPFragment<UpdatePasswordPresenter> implements UpdatePasswordContract.ViewImpl {
    @BindView(R.id.edt_old_pass)
    EditText edtOldPass;
    @BindView(R.id.edt_new_pass)
    EditText edtNewPass;
    @BindView(R.id.edt_confirm_pass)
    EditText edtConfirmPass;

    public static UpdatePasswordFragment newInstance() {
        Bundle args = new Bundle();
        UpdatePasswordFragment fragment = new UpdatePasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_update_password;
    }

    @Override
    public String getScreenTitle() {
        if (AppState.getInstance().getCurrentUser() == null) {
            showToast(getString(R.string.not_found_user));
            getActivity().onBackPressed();
            return null;
        } else {
            return AppState.getInstance().getCurrentUser().getName();
        }
    }

    @Override
    public void initView() {
        super.initView();
    }

    @OnClick(R.id.btn_change_pass)
    protected void changePassword() {
        String oldPass = edtOldPass.getText().toString();
        String newPass = edtNewPass.getText().toString();
        String confirmPass = edtConfirmPass.getText().toString();

        if (!validate(oldPass, newPass, confirmPass)) {
            getPresenter();
        }
    }

    private boolean validate(String oldPass, String newPass, String confirmPass) {

        if (StringUtil.isEmpty(oldPass)) {
            showToast(getString(R.string.update_pass_old_pass_empty));
            return false;
        }

        if (StringUtil.isEmpty(newPass)) {
            showToast(getString(R.string.update_pass_new_pass_empty));
            return false;
        }

        if (StringUtil.isEmpty(confirmPass)) {
            showToast(getString(R.string.update_pass_confirm_pass_empty));
            return false;
        }

        if (!newPass.equals(confirmPass)) {
            showToast(getString(R.string.message_pass_confirm_invalid));
            return false;
        }

        return true;
    }

    @Override
    public void onChangePasswordResult(boolean result, String message) {
        if (result) {
            // todo: todo somethings (back)
        } else {
            String error = StringUtil.isEmpty(message) ? getString(R.string.message_change_pass_failure) : message;
            showToast(error);
        }
    }
}
