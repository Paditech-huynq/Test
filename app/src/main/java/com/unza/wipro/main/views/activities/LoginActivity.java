package com.unza.wipro.main.views.activities;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
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
    @BindView(R.id.layout_main)
    View layoutMain;
    @BindView(R.id.bottomView)
    View bottomView;

    private ViewTreeObserver.OnGlobalLayoutListener keyboardLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Rect measureRect = new Rect();
            layoutMain.getWindowVisibleDisplayFrame(measureRect);
            int keypadHeight = layoutMain.getRootView().getHeight() - measureRect.bottom;
            if (keypadHeight > 200) {
                bottomView.setVisibility(View.VISIBLE);
            } else {
                bottomView.setVisibility(View.GONE);
            }
        }
    };

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
        super.initView();
        Utils.dismissSoftKeyboard(findViewById(R.id.layout_main), this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        layoutMain.getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);
    }

    @Override
    public void onLoginResult(final boolean result, final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (result) {
//                    Intent intent = new Intent(LoginActivity.this, FillOtpActivity.class);
//                    startActivity(intent);
                    finish();
                } else {
                    String alert = StringUtil.isEmpty(message) ? getString(R.string.message_login_failure) : message;
                    showToast(alert);
                    mPasswordText.setText("");
                }
            }
        });
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
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tv_call_center)
    protected void callCenter() {
        //TODO: call center
        showToast("Cập nhật sau!");
    }

    @OnClick(R.id.tv_term)
    protected void goToTerm() {
        //TODO: term & policy
        showToast("Cập nhật sau!");
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
