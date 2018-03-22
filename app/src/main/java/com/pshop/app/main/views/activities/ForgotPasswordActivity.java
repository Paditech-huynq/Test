package com.pshop.app.main.views.activities;

import android.content.Intent;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.paditech.core.helper.StringUtil;
import com.paditech.core.mvp.MVPActivity;
import com.pshop.app.R;
import com.pshop.app.main.contracts.ForgotPasswordContract;
import com.pshop.app.main.presenters.ForgotPasswordPresenter;
import com.pshop.app.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/12/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class ForgotPasswordActivity extends MVPActivity<ForgotPasswordPresenter> implements ForgotPasswordContract.ViewImpl {

    private static final int CHANGE_PASS_REQ_CODE = 1001;

    @BindView(R.id.edt_username)
    EditText mUsernameText;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_forgot_password;
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
    }

    @Override
    public void onForgotPassResult(final boolean result, final String phone, final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (result) {
                    Intent intent = new Intent(ForgotPasswordActivity.this, FillOtpActivity.class);
                    intent.putExtra("phone", phone);
                    intent.putExtra("otp", message);
                    startActivityForResult(intent, CHANGE_PASS_REQ_CODE);
                } else {
                    String alert = StringUtil.isEmpty(message) ? getString(R.string.message_forgot_pass_failure) : message;
                    showToast(alert);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHANGE_PASS_REQ_CODE && resultCode == RESULT_OK) {
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.imv_close)
    protected void back() {
        onBackPressed();
    }

    @OnClick(R.id.btn_confirm)
    protected void confirm() {
        String username = mUsernameText.getText().toString().trim();
        if (!validate(username)) return;
        getPresenter().forgotPass(username);
    }

    private boolean validate(String username) {
        if (StringUtil.isEmpty(username)) {
            showToast(getString(R.string.message_username_empty));
            return false;
        }
        return true;
    }
}
