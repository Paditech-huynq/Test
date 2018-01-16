package com.unza.wipro.main.views.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.paditech.core.mvp.MVPFragment;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.ProfileContract;
import com.unza.wipro.main.presenters.ProfilePresenter;

import butterknife.BindView;

public class ProfilePragment extends MVPFragment<ProfilePresenter> implements ProfileContract.ViewImpl {

    @BindView(R.id.ln_degree)
    LinearLayout ln_degree;
    @BindView(R.id.ln_manager_sales)
    LinearLayout ln_manager_sales;
    public static final int TYPE_USER_CUSTOM = 0;
    public static final int TYPE_USER_EMPLOYEE = 1;
    public static final int TYPE_USER_MANAGER = 2;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_profile;
    }

    @Override
    public String getScreenTitle() {
        return getString(R.string.title_profile);
    }

    public static ProfilePragment newInstance() {

        Bundle args = new Bundle();

        ProfilePragment fragment = new ProfilePragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        settingView(TYPE_USER_EMPLOYEE);
    }

    public void settingView(int typeUser){
        switch (typeUser){
            case TYPE_USER_CUSTOM:
                break;
            case TYPE_USER_EMPLOYEE:
                ln_degree.setVisibility(View.VISIBLE);
                break;
            case TYPE_USER_MANAGER:
                ln_degree.setVisibility(View.VISIBLE);
                ln_manager_sales.setVisibility(View.VISIBLE);
                break;
        }
    }
}
