package com.pshop.app.main.views.customs;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pshop.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * wipro-crm-android
 * <p>
 * Created by Paditech on 1/16/2018.
 * Copyright (c) 2018 Paditech. All rights reserved.
 */

public class AmountView extends RelativeLayout {

    private static final int MAX_TIME_CHANGE_VALUE = 500;
    private static final int MIN_TIME_CHANGE_VALUE = 10;

    @BindView(R.id.tv_amount)
    TextView mAmountText;
    @BindView(R.id.imv_increase)
    ImageView imvIncrease;
    @BindView(R.id.imv_decrease)
    ImageView imvDecrease;

    private int value = 0;
    private int interval = 1;
    private int minValue = 0;
    private int maxValue = 9999;
    private OnValueChangeListener mOnValueChangeListener;

    private float timeRatio = 0.5F;
    private int countValueChange = 0;
    private boolean isIncrease = true;
    int timeChangeValue = MAX_TIME_CHANGE_VALUE;

    final Handler changeValueHandler = new Handler();
    final Runnable changeValueRunnable = new Runnable() {
        @Override
        public void run() {
            if (isIncrease) {
                increaseValue();
            } else {
                decreaseValue();
            }

            if (timeChangeValue * timeRatio > MIN_TIME_CHANGE_VALUE && timeChangeValue * timeRatio < MAX_TIME_CHANGE_VALUE) {
                if (countValueChange == 5 || countValueChange == 15 || countValueChange == 30 || countValueChange == 50 || countValueChange == 100) {
                    timeChangeValue *= timeRatio;
                }
            }
            changeValueHandler.postDelayed(this, timeChangeValue);
        }
    };

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

        imvIncrease.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                changeValue(v, motionEvent, true);
                return true;
            }
        });

        imvDecrease.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                changeValue(v, motionEvent, false);
                return true;
            }
        });
    }

    public void setOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
        this.mOnValueChangeListener = onValueChangeListener;
    }

    private void changeValue(View v, MotionEvent motionEvent, boolean increase) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            this.isIncrease = increase;
            if (increase) {
                increaseValue();
            } else {
                decreaseValue();
            }
            changeValueHandler.postDelayed(changeValueRunnable, timeChangeValue);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            stopChangeValue();
        } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            Rect rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
            if (!rect.contains(v.getLeft() + (int) motionEvent.getX(), v.getTop() + (int) motionEvent.getY())) {
                stopChangeValue();
            }
        } else if (motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
            stopChangeValue();
        }
    }

    private void stopChangeValue() {
        changeValueHandler.removeCallbacks(changeValueRunnable);
        countValueChange = 0;
        timeChangeValue = MAX_TIME_CHANGE_VALUE;
    }

    protected void decreaseValue() {
        if (value > minValue) {
            value -= interval;
            countValueChange += interval;
            if (mOnValueChangeListener != null) mOnValueChangeListener.onValueChange(true, value);
        }
        setValue(value);
    }

    protected void increaseValue() {
        if (value < maxValue) {
            value += interval;
            countValueChange += interval;
            if (mOnValueChangeListener != null) mOnValueChangeListener.onValueChange(false, value);
        }
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

    public interface OnValueChangeListener {
        void onValueChange(boolean isReduce, int value);
    }
}
