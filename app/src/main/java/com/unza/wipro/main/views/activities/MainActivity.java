package com.unza.wipro.main.views.activities;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.paditech.core.mvp.MVPActivity;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.MainContract;
import com.unza.wipro.main.presenters.MainPresenter;
import com.unza.wipro.main.views.fragments.HomeFragment;
import com.unza.wipro.main.views.fragments.OrderListFragment;
import com.unza.wipro.main.views.fragments.OrderDetailFragment;

import butterknife.BindView;
import butterknife.OnClick;

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

    @OnClick(R.id.imvAvatar)
    protected void onAvatarClick() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnCart)
    void onBtnCartClick() {
        switchFragment(OrderDetailFragment.newInstance(), true);
    }

    public void setShowHeader(boolean isShowHeader) {
        layoutHeader.setVisibility(isShowHeader ? View.VISIBLE : View.GONE);
    }
}
