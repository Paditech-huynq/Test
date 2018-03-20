package com.unza.wipro.main.views.fragments;

import android.os.Bundle;
import android.widget.EditText;

import com.paditech.core.BaseFragment;
import com.paditech.core.helper.StringUtil;
import com.paditech.core.mvp.MVPFragment;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.LoginContract;
import com.unza.wipro.main.presenters.LoginPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginFragment extends MVPFragment<LoginPresenter> implements LoginContract.ViewImpl {

    @BindView(R.id.edt_username)
    EditText mUsernameText;
    @BindView(R.id.edt_password)
    EditText mPasswordText;

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_login;
    }

    @Override
    public String getScreenTitle() {
        return null;
    }

    @Override
    public boolean isShowTitle() {
        return false;
    }

    @Override
    public void onLoginResult(final boolean result, final String message) {

    }

    @OnClick(R.id.imv_close)
    protected void back() {

    }

    @OnClick(R.id.btn_login)
    protected void login() {
        String username = mUsernameText.getText().toString().trim();
        String password = mPasswordText.getText().toString().trim();
        if (!validate(username, password)) return;
        getPresenter().login(username, password);
    }

    @OnClick(R.id.tv_forgot_password)
    protected void goToForgotPassword() {

    }

    @OnClick(R.id.tv_call_center)
    protected void callCenter() {

    }

    @OnClick(R.id.tv_term)
    protected void goToTerm() {

    }

    private boolean validate(String username, String pass) {
        if (StringUtil.isEmpty(username)) {
            showToast(getString(R.string.message_username_empty));
            return false;
        }
        if (StringUtil.isEmpty(pass)) {
            showToast(getString(R.string.message_password_empty));
            return false;
        }
        return true;
    }
}
