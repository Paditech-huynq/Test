package com.unza.wipro.main.views.fragments;

import android.os.Bundle;

import com.paditech.core.BaseFragment;
import com.unza.wipro.R;

public class LoginFragment extends BaseFragment{
    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.layout_default;
    }
}
