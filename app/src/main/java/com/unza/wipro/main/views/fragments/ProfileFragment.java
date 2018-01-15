package com.unza.wipro.main.views.fragments;

import android.os.Bundle;
import android.widget.BaseAdapter;

import com.paditech.core.BaseFragment;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.ProfileContract;

/**
 * Created by bangindong on 1/15/2018.
 */

public class ProfileFragment extends BaseFragment implements ProfileContract.ViewImpl {

    public static ProfileFragment newInstance() {

        Bundle args = new Bundle();

        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_profile;
    }

    @Override
    public String getScreenTitle() {
        return getString(R.string.title_profile);
    }

    @Override
    public void setScreenTitle(String title) {
        super.setScreenTitle(title);
    }

    @Override
    public void initView() {
        super.initView();
        
    }


}
