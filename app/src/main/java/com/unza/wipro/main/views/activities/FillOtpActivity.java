package com.unza.wipro.main.views.activities;

import android.content.Intent;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.paditech.core.helper.StringUtil;
import com.paditech.core.mvp.MVPActivity;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.OtpContract;
import com.unza.wipro.main.presenters.OtpPresenter;
import com.unza.wipro.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/12/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class FillOtpActivity extends MVPActivity<OtpPresenter> implements OtpContract.ViewImpl {

    private long mLastClickTime = 0;

    @BindView(R.id.txt_code_1)
    EditText mCode1Text;
    @BindView(R.id.txt_code_2)
    EditText mCode2Text;
    @BindView(R.id.txt_code_3)
    EditText mCode3Text;
    @BindView(R.id.txt_code_4)
    EditText mCode4Text;
    @BindView(R.id.txt_code_5)
    EditText mCode5Text;
    @BindView(R.id.txt_code_6)
    EditText mCode6Text;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_fill_otp;
    }

    @Override
    public String getScreenTitle() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        Utils.dismissSoftKeyboard(findViewById(R.id.layout_main), this);
        Utils.setStatusBarTranslucent(true, this);

        mCode1Text.setSelectAllOnFocus(true);
        mCode2Text.setSelectAllOnFocus(true);
        mCode3Text.setSelectAllOnFocus(true);
        mCode4Text.setSelectAllOnFocus(true);
        mCode5Text.setSelectAllOnFocus(true);
        mCode6Text.setSelectAllOnFocus(true);

        mCode1Text.addTextChangedListener(new FocusSwitchingTextWatcher(mCode2Text));
        mCode2Text.addTextChangedListener(new FocusSwitchingTextWatcher(mCode3Text));
        mCode3Text.addTextChangedListener(new FocusSwitchingTextWatcher(mCode4Text));
        mCode4Text.addTextChangedListener(new FocusSwitchingTextWatcher(mCode5Text));
        mCode5Text.addTextChangedListener(new FocusSwitchingTextWatcher(mCode6Text));

        mCode2Text.setOnKeyListener(new OnDelEditor(mCode1Text));
        mCode3Text.setOnKeyListener(new OnDelEditor(mCode2Text));
        mCode4Text.setOnKeyListener(new OnDelEditor(mCode3Text));
        mCode5Text.setOnKeyListener(new OnDelEditor(mCode4Text));
        mCode6Text.setOnKeyListener(new OnDelEditor(mCode5Text));
    }

    @Override
    public void onConfirmOtpResult(final boolean result, final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (result) {
                    Intent intent = new Intent(FillOtpActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    String alert = StringUtil.isEmpty(message) ? getString(R.string.message_otp_failure) : message;
                    showToast(alert);
                }
            }
        });
    }

    @OnClick(R.id.imv_cancel)
    protected void back() {
        onBackPressed();
    }

    @OnClick(R.id.btn_confirm)
    protected void confirm() {
        String code1 = mCode1Text.getText().toString().trim();
        String code2 = mCode2Text.getText().toString().trim();
        String code3 = mCode3Text.getText().toString().trim();
        String code4 = mCode4Text.getText().toString().trim();
        String code5 = mCode5Text.getText().toString().trim();
        String code6 = mCode6Text.getText().toString().trim();
        final String code = code1 + code2 + code3 + code4 + code5 + code6;
        if (StringUtil.isEmpty(code) || code.length() != 6) {
            showToast(getString(R.string.message_otp_empty));
            return;
        }
        getPresenter().confirmOtp(code);
    }

    private boolean isDoubleClick() {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 100) {
            return true;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        return false;
    }

    private static class FocusSwitchingTextWatcher implements TextWatcher {

        private final View nextViewToFocus;

        FocusSwitchingTextWatcher(View nextViewToFocus) {
            this.nextViewToFocus = nextViewToFocus;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() >= 1) {
                nextViewToFocus.requestFocus();
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void afterTextChanged(Editable s) {

        }

    }

    private class OnDelEditor implements View.OnKeyListener {

        private final View nextViewToFocus;

        OnDelEditor(View nextViewToFocus) {
            this.nextViewToFocus = nextViewToFocus;
        }

        @Override
        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
            if (keyCode == KeyEvent.KEYCODE_BACK) return false;
            if (isDoubleClick()) return true;
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (view instanceof EditText) {
                    ((EditText) view).setText("");
                }
                nextViewToFocus.requestFocus();
                return true;
            }
            return false;
        }
    }
}
