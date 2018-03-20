package com.unza.wipro.main.views.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.paditech.core.BaseDialog;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.ProductImageAdapter;
import com.unza.wipro.main.models.ProductThumbnail;
import com.unza.wipro.main.views.customs.HandleTouchViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FullScreenImageDialogFragment extends BaseDialog {
    private static final float MIN_ALPHA = 0f;
    private static final float RANGE_TO_DISMISS = 500;
    private static final long LONG_PRESS_TIME = 200;
    private final Handler handler = new Handler();
    private final Runnable mLongPressed = new Runnable() {
        public void run() {
            getView().setActivated(true);
            currentView.setScaleX(0.95f);
            currentView.setScaleY(0.95f);
            vpgImages.setEnabled(false);
        }
    };

    @BindView(R.id.vpgImages)
    HandleTouchViewPager vpgImages;

    @BindView(R.id.layoutFull)
    View layoutFull;

    private View currentView;
    private float startX, startY;
    private List<ProductThumbnail> data = new ArrayList<>();
    private int currentPos;

    public static FullScreenImageDialogFragment newInstance(List<ProductThumbnail> data, int pos) {
        Bundle args = new Bundle();
        FullScreenImageDialogFragment fragment = new FullScreenImageDialogFragment();
        fragment.setArguments(args);
        fragment.data.addAll(data);
        fragment.currentPos = pos;
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_full_image;
    }

    @Override
    protected void initView(View view) {
        ProductImageAdapter mAdapter = new ProductImageAdapter(getActivity(), touchListener);
        vpgImages.setAdapter(mAdapter);
        mAdapter.setData(data);
        vpgImages.setCurrentItem(currentPos, false);
        vpgImages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                handler.removeCallbacks(mLongPressed);
            }
        });
    }

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            float rawY = event.getRawY();
            float rangeY = rawY - startY;

            float rawX = event.getRawX();
            float rangeX = rawX - startX;
            float alpha = 1f - Math.abs(rangeY / startY);
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    startY = rawY;
                    startX = rawX;
                    currentView = v;
                    handler.postDelayed(mLongPressed, LONG_PRESS_TIME);
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (getView().isActivated()) {
                        v.setTranslationY(rangeY);
                        v.setTranslationX(rangeX);
                    } else {
                        startY = rawY;
                        startX = rawX;
                    }
                    layoutFull.setAlpha(Math.max(MIN_ALPHA, alpha));
                    break;
                case MotionEvent.ACTION_UP:
                    handler.removeCallbacks(mLongPressed);
                    if (getView().isActivated()) {
                        if (Math.abs(rangeY) >= RANGE_TO_DISMISS || Math.abs(rangeX) >= RANGE_TO_DISMISS / 1.5) {
                            dismissAllowingStateLoss();
                        } else {
                            v.setScaleX(1f);
                            v.setScaleY(1f);
                            v.setTranslationX(0);
                            v.setTranslationY(0);
                            layoutFull.setAlpha(1f);
                        }
                    }
                    getView().setActivated(false);
                    vpgImages.setEnabled(true);
                    break;
            }
            return true;
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
