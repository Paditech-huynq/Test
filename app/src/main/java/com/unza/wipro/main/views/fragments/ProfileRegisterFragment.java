package com.unza.wipro.main.views.fragments;

import android.os.Bundle;

import com.paditech.core.BaseFragment;
import com.unza.wipro.R;

public class ProfileRegisterFragment extends BaseFragment {
    public static ProfileRegisterFragment newInstance() {

        Bundle args = new Bundle();

        ProfileRegisterFragment fragment = new ProfileRegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_profile_register;
    }

    @Override
    public String getScreenTitle() {
        return getString(R.string.title_profile_register);
    }
}
