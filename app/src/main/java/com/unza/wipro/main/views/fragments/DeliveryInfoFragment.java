package com.unza.wipro.main.views.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.paditech.core.helper.StringUtil;
import com.paditech.core.mvp.MVPFragment;
import com.unza.wipro.AppState;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.DeliveryInfoContract;
import com.unza.wipro.main.presenters.DeliveryInfoPresenter;
import com.unza.wipro.transaction.user.DeliveryInfo;
import com.unza.wipro.utils.DateTimeUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/25/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class DeliveryInfoFragment extends MVPFragment<DeliveryInfoPresenter> implements DeliveryInfoContract.ViewImpl {

    @BindView(R.id.edt_delivery_name)
    EditText mNameText;
    @BindView(R.id.edt_delivery_phone)
    EditText mPhoneText;
    @BindView(R.id.edt_delivery_address)
    EditText mAddressText;
    @BindView(R.id.edt_delivery_date)
    TextView mDateText;
    @BindView(R.id.edt_delivery_note)
    EditText mNoteText;

    public static DeliveryInfoFragment newInstance() {
        Bundle args = new Bundle();

        DeliveryInfoFragment fragment = new DeliveryInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_delivery_info;
    }

    @Override
    public String getScreenTitle() {
        return getString(R.string.delivery_info_title);
    }

    @Override
    public void initView() {
        super.initView();
        String name =  AppState.getInstance().getCurrentUser().getName();
        String phone =  AppState.getInstance().getCurrentUser().getPhone();
        mNameText.setText(name);
        mPhoneText.setText(phone);
    }

    @Override
    public void onResult(final boolean result, final String message) {
        if (result) {
            getActivity().onBackPressed();
        } else {
            String alert = StringUtil.isEmpty(message) ? getString(R.string.message_login_failure) : message;
            showToast(alert);
        }
    }

    @OnClick({R.id.edt_delivery_date, R.id.btn_delivery_date})
    protected void chooseDeliveryDate() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        String deliveryDate = mDateText.getText().toString().trim();
        if (!StringUtil.isEmpty(deliveryDate)) {
            Date date = DateTimeUtils.getDateFromStringDayMonthYear(deliveryDate);
            if (date != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
            }
        }
        DatePickerDialog mDateSelectorDialog = new DatePickerDialog(getActivity(), R.style.Theme_AppCompat_Light_Dialog,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, dayOfMonth);
                        if (calendar.before(Calendar.getInstance())) {
                            showToast(getString(R.string.delivery_info_invalid_date));
                            mDateText.setText(null);
                            return;
                        }
                        mDateText.setText(StringUtil.formatDate(calendar.getTime()));
                    }
                }, year, month, day);
//        mDateSelectorDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        mDateSelectorDialog.show();
    }

    private boolean validate(String name, String phone) {
        if (StringUtil.isEmpty(name)) {
            showToast(getString(R.string.delivery_info_name_empty));
            return false;
        }
        if (StringUtil.isEmpty(phone)) {
            showToast(getString(R.string.delivery_info_phone_empty));
            return false;
        }
        return true;
    }

    @OnClick(R.id.btnRegister)
    protected void submit() {
        String name = mNameText.getText().toString().trim();
        String phone = mPhoneText.getText().toString().trim();
        String address = mAddressText.getText().toString().trim();
        String date = mDateText.getText().toString().trim();
        String note = mNoteText.getText().toString().trim();
        if (!validate(name, phone)) return;
        getActivity().onBackPressed();
        EventBus.getDefault().postSticky(new DeliveryInfo(name, phone, address, date, note));
    }
}
