package com.unza.wipro.main.views.customs;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.unza.wipro.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/16/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class AmountView extends RelativeLayout {

    @BindView(R.id.tv_amount)
    TextView mAmountText;

    private int value = 0;
    private int interval = 1;
    private int minValue = 0;
    private int maxValue = Integer.MAX_VALUE;

    public AmountView(Context context) {
        super(context);
        initView(context, null);
    }

    public AmountView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public AmountView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public AmountView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_amount, this);
        ButterKnife.bind(this);
        setValue(value);
    }

    @OnClick(R.id.imv_decrease)
    protected void decrease() {
        if (value > minValue) value -= interval;
        setValue(value);
    }

    @OnClick(R.id.imv_increase)
    protected void increase() {
        if (value < maxValue) value += interval;
        setValue(value);
    }

    public AmountView setValue(int value) {
        this.value = value;
        mAmountText.setText(String.valueOf(this.value));
        return this;
    }

    public int getValue() {
        return value;
    }

    public AmountView setInterval(int interval) {
        this.interval = interval;
        return this;
    }

    public AmountView setMinValue(int minValue) {
        this.minValue = minValue;
        return this;
    }

    public AmountView setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        return this;
    }
}
