package com.unza.wipro.main.views.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.paditech.core.BaseFragment;
import com.paditech.core.helper.StringUtil;
import com.paditech.core.image.GlideApp;
import com.paditech.core.mvp.MVPActivity;
import com.squareup.otto.Subscribe;
import com.unza.wipro.AppAction;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.MainContract;
import com.unza.wipro.main.presenters.MainPresenter;
import com.unza.wipro.main.views.fragments.HomeFragment;
import com.unza.wipro.main.views.fragments.NotificationFragment;
import com.unza.wipro.main.views.fragments.OrderDetailFragment;
import com.unza.wipro.main.views.fragments.ProfileFragment;

import butterknife.BindView;

public class MainActivity extends MVPActivity<MainPresenter> implements MainContract.ViewImpl, AppConstans {
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.layoutHeader)
    View layoutHeader;

    @BindView(R.id.tvCartAmount)
    TextView tvCartAmount;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        super.initView();
        switchFragment(HomeFragment.newInstance(), false);
        addToAction(R.id.btnCart, R.id.btnNotification, R.id.imvAvatar, R.id.btnTrash);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus.register(this);
    }

    @Override
    protected void onDestroy() {
        bus.unregister(this);
        super.onDestroy();
    }

    @Override
    public String getScreenTitle() {
        return null;
    }

    @Override
    public void setScreenTitle(String title) {
        super.setScreenTitle(title);
        if (title != null) {
            tvTitle.setText(title);
        }
    }

    @Override
    public void onViewAppear() {
        super.onViewAppear();
        updateAvatar();
    }

    public void setShowHeader(boolean isShowHeader) {
        layoutHeader.setVisibility(isShowHeader ? View.VISIBLE : View.GONE);
    }

    protected void updateAvatar() {
        ImageView imageView = findViewById(R.id.imvAvatar);
        if (imageView == null) return;
        if (app.isLogin()) {
            if (!StringUtil.isEmpty(app.getCurrentUser().getAvatar())) {
                GlideApp.with(this)
                        .load(app.getCurrentUser().getAvatar())
                        .placeholder(R.drawable.ic_avatar_holder_circle)
                        .thumbnail(0.5f).circleCrop()
                        .into(imageView);
            } else {
                imageView.setImageResource(R.drawable.ic_avatar_holder_circle);
            }
        } else {
            imageView.setImageResource(R.drawable.ic_avatar_holder_circle);
        }
    }

    @Override
    public void onActionSelected(int resId) {
        super.onActionSelected(resId);
        switch (resId) {
            case R.id.btnCart:
                switchFragment(OrderDetailFragment.newInstance(), true);
                break;
            case R.id.btnNotification:
                switchFragment(NotificationFragment.newInstance(), true);
                break;
            case R.id.imvAvatar:
                if (app.isLogin()) {
                    switchFragment(ProfileFragment.newInstance(), true);
                } else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    protected int getBackButtonView() {
        return R.id.btnBack;
    }

    public View getCartView() {
        return findViewById(R.id.btnCart);
    }

    public void updateActionButtonAppearance(BaseFragment targetFragment) {
        updateActionAppearance(targetFragment);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View v = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);
        if (v instanceof EditText) {
            View w = getCurrentFocus();
            int scrCoords[] = new int[2];
            if (w != null) {
                w.getLocationOnScreen(scrCoords);
            }
            float x = 0;
            if (w != null) {
                x = event.getRawX() + w.getLeft() - scrCoords[0];
            }
            float y = 0;
            if (w != null) {
                y = event.getRawY() + w.getTop() - scrCoords[1];
            }
            if (w != null && event.getAction() == MotionEvent.ACTION_UP && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom())) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
                }
            }
        }
        return ret;
    }

    public void openCamera(boolean isOpen) {
        findViewById(R.id.layoutCamera).setVisibility(isOpen ? View.VISIBLE : View.GONE);
    }

    @Override
    public void updateCartCount() {
        int cartItemCount = app.getCurrentCart().getTotalQuantity();
        tvCartAmount.setVisibility(cartItemCount == 0 ? View.GONE : View.VISIBLE);
        String count = cartItemCount <= 99 ? String.valueOf(cartItemCount) : "99+";
        tvCartAmount.setText(count);
    }

    @Subscribe
    public void onAction(AppAction action) {
        Log.e("Action", action+"");
        switch (action) {
            case REQUEST_CAMERA_CLOSE:
                openCamera(false);
                break;
            case REQUEST_CAMERA_OPEN:
                openCamera(true);
                break;
        }
    }
}
