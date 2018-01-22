package com.unza.wipro.main.views.activities;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.paditech.core.BaseFragment;
import com.paditech.core.helper.ImageHelper;
import com.paditech.core.helper.StringUtil;
import com.paditech.core.image.GlideApp;
import com.paditech.core.mvp.MVPActivity;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.MainContract;
import com.unza.wipro.main.models.LoginClient;
import com.unza.wipro.main.presenters.MainPresenter;
import com.unza.wipro.main.views.fragments.HomeFragment;
import com.unza.wipro.main.views.fragments.NotificationFragment;
import com.unza.wipro.main.views.fragments.OrderDetailFragment;
import com.unza.wipro.main.views.fragments.ProfileFragment;
import com.unza.wipro.utils.Utils;

import butterknife.BindView;

public class MainActivity extends MVPActivity<MainPresenter> implements MainContract.ViewImpl {
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.layoutHeader)
    View layoutHeader;

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
        if (LoginClient.isLogin(this)) {
            if (!StringUtil.isEmpty(LoginClient.getLoginInfo(this).getAvatar())) {
                GlideApp.with(this)
                        .load(LoginClient.getLoginInfo(this).getAvatar())
                        .placeholder(R.drawable.ic_avatar_holder)
                        .thumbnail(0.5f).circleCrop()
                        .into(imageView);
            } else {
                imageView.setImageResource(R.drawable.ic_avatar_holder);
            }
        } else {
            imageView.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override
    public void onActionSelected(int resId) {
        super.onActionSelected(resId);
        switch (resId) {
            case R.id.btnCart:
                switchFragment(OrderDetailFragment.newInstance(OrderDetailFragment.ViewMode.MODE_CREATE, null), true);
                break;
            case R.id.btnNotification:
                switchFragment(NotificationFragment.newInstance(), true);
                break;
            case R.id.imvAvatar:
                if (LoginClient.isLogin(this)) {
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
}
