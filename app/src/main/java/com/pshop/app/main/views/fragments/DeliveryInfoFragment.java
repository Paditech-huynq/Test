package com.pshop.app.main.views.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.paditech.core.helper.StringUtil;
import com.paditech.core.mvp.MVPFragment;
import com.pshop.app.AppConstans;
import com.pshop.app.R;
import com.pshop.app.main.contracts.DeliveryInfoContract;
import com.pshop.app.main.presenters.DeliveryInfoPresenter;
import com.pshop.app.transaction.user.DeliveryInfo;
import com.pshop.app.utils.DateTimeUtils;

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

public class DeliveryInfoFragment extends MVPFragment<DeliveryInfoPresenter> implements DeliveryInfoContract.ViewImpl, AppConstans {

    private static final String KEY_CUSTOMER_ID = "customer_id";
    @BindView(R.id.edt_delivery_name)
    EditText edtName;
    @BindView(R.id.edt_delivery_phone)
    EditText edtPhone;
    @BindView(R.id.edt_delivery_address)
    EditText edtAddress;
    @BindView(R.id.edt_delivery_date)
    TextView edtDate;
    @BindView(R.id.edt_delivery_note)
    EditText edtNote;

    private String customerId;
    private Calendar mDefaultDate;

    public static DeliveryInfoFragment newInstance(String customerId) {
        Bundle args = new Bundle();
        DeliveryInfoFragment fragment = new DeliveryInfoFragment();
        args.putString(KEY_CUSTOMER_ID, customerId);
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
        String name = app.getCurrentUser().getName();
        String phone = app.getCurrentUser().getPhone();
        String address = app.getCurrentUser().getAddress();
        edtName.setText(name);
        edtPhone.setText(phone);
        edtAddress.setText(address);
        customerId = getArguments().getString(KEY_CUSTOMER_ID);
        mDefaultDate = Calendar.getInstance();
        mDefaultDate.add(Calendar.DAY_OF_MONTH, 2);
        edtDate.setText(StringUtil.formatDate(mDefaultDate.getTime()));
    }

    @Override
    public String getCustomerId() {
        return customerId;
    }

    @Override
    public DeliveryInfo getDeliverInfo() {
        if (validate(edtName.getText().toString(), edtPhone.getText().toString())) {
            DeliveryInfo deliveryInfo = new DeliveryInfo();
            String name = edtName.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();
            Date date = DateTimeUtils.getDateFromStringDayMonthYear(edtDate.getText().toString().trim());
            String note = edtNote.getText().toString().trim();

            deliveryInfo.setAddress(address);
            deliveryInfo.setDate(String.valueOf(date.getTime() / 1000));
            deliveryInfo.setName(name);
            deliveryInfo.setNote(note);
            deliveryInfo.setPhone(phone);
            return deliveryInfo;
        }
        return null;
    }

    @Override
    public void backToHomeScreen() {
        //todo: back to home screen
        getActivity().onBackPressed();
    }

    @OnClick({R.id.edt_delivery_date, R.id.btn_delivery_date})
    protected void chooseDeliveryDate() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        String deliveryDate = edtDate.getText().toString().trim();
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
                        edtDate.setText(StringUtil.formatDate(calendar.getTime()));
                    }
                }, year, month, day) {
            @Override
            public void onDateChanged(@NonNull DatePicker view, int year, int month, int dayOfMonth) {
                super.onDateChanged(view, year, month, dayOfMonth);
                view.setMinDate(mDefaultDate.getTimeInMillis());
            }
        };
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
        } else if (phone.trim().length() < 9) {
            showToast(getString(R.string.delivery_info_phone_incorrect));
            return false;
        }
        return true;
    }

    @OnClick(R.id.btnRegister)
    protected void submit() {
        getPresenter().onSubmitButtonClick();
    }
}
