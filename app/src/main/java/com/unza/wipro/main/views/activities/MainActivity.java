package com.unza.wipro.main.views.activities;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.paditech.core.BaseFragment;
import com.paditech.core.mvp.MVPActivity;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.MainContract;
import com.unza.wipro.main.presenters.MainPresenter;
import com.unza.wipro.main.views.fragments.HomeFragment;
import com.unza.wipro.main.views.fragments.NotificationFragment;
import com.unza.wipro.main.views.fragments.OrderDetailFragment;

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

    public void setShowHeader(boolean isShowHeader) {
        layoutHeader.setVisibility(isShowHeader ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onActionSelected(int resId) {
        super.onActionSelected(resId);
        switch (resId) {
            case R.id.btnCart:
                switchFragment(OrderDetailFragment.newInstance(OrderDetailFragment.ViewMode.CREATE_MODE), true);
                break;
            case R.id.btnNotification:
                switchFragment(NotificationFragment.newInstance(), true);
                break;
            case R.id.imvAvatar:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
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
}
