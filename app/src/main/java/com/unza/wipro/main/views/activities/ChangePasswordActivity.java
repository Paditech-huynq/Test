package com.unza.wipro.main.views.activities;

import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.paditech.core.helper.StringUtil;
import com.paditech.core.mvp.MVPActivity;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.ChangePasswordContract;
import com.unza.wipro.main.presenters.ChangePasswordPresenter;
import com.unza.wipro.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/12/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class ChangePasswordActivity extends MVPActivity<ChangePasswordPresenter> implements ChangePasswordContract.ViewImpl {

    @BindView(R.id.edt_password)
    EditText mPasswordText;
    @BindView(R.id.edt_confirm_password)
    EditText mConfirmPasswordText;
    private String phone;
    private String otp;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_change_password;
    }

    @Override
    public String getScreenTitle() {
        return null;
    }

    @Override
    public void initView() {
        if (getIntent().getExtras() != null) {
            phone = getIntent().getStringExtra("phone");
            otp = getIntent().getStringExtra("otp");
        }
        super.initView();
        Utils.dismissSoftKeyboard(findViewById(R.id.layout_main), this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void onChangePassResult(final boolean result, final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (result) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
                    builder.setMessage(getString(R.string.message_change_pass_success))
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            });
                    builder.create().show();
                } else {
                    String alert = StringUtil.isEmpty(message) ? getString(R.string.message_change_pass_failure) : message;
                    showToast(alert);
                }
            }
        });
    }

    @OnClick(R.id.imv_close)
    protected void back() {
        onBackPressed();
    }

    @OnClick(R.id.btn_confirm)
    protected void changePass() {
        String password = mPasswordText.getText().toString().trim();
        String confirm = mConfirmPasswordText.getText().toString().trim();
        if (!validate(password, confirm)) return;
        getPresenter().changePass(phone, otp, password, confirm);
    }

    private boolean validate(String newPass, String confirmPass) {
        if (StringUtil.isEmpty(newPass)) {
            showToast(getString(R.string.message_pass_new_empty));
            return false;
        }
        if (StringUtil.isEmpty(confirmPass)) {
            showToast(getString(R.string.message_pass_confirm_empty));
            return false;
        }
        if (!newPass.equals(confirmPass)) {
            showToast(getString(R.string.message_pass_confirm_invalid));
            return false;
        }
        return true;
    }
}
