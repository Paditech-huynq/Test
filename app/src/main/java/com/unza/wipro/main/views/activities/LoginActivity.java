package com.unza.wipro.main.views.activities;

import android.content.Intent;
import android.widget.EditText;

import com.paditech.core.helper.StringUtil;
import com.paditech.core.mvp.MVPActivity;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.LoginContract;
import com.unza.wipro.main.presenters.LoginPresenter;
import com.unza.wipro.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/12/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class LoginActivity extends MVPActivity<LoginPresenter> implements LoginContract.ViewImpl {

    @BindView(R.id.edt_username)
    EditText mUsernameText;
    @BindView(R.id.edt_password)
    EditText mPasswordText;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    public String getScreenTitle() {
        return null;
    }

    @Override
    public void initView() {
        Utils.dismissSoftKeyboard(findViewById(R.id.layout_main), this);
        Utils.setStatusBarTranslucent(true, this);
        super.initView();
    }

    @Override
    public void onLoginResult() {
        goToMain();
    }

    @OnClick(R.id.imv_close)
    protected void back() {
        onBackPressed();
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

    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
