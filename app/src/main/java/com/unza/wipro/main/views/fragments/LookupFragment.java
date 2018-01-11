package com.unza.wipro.main.views.fragments;

import android.os.Bundle;

import com.paditech.core.BaseFragment;
import com.unza.wipro.R;

public class LookupFragment extends BaseFragment {
    public static LookupFragment newInstance() {

        Bundle args = new Bundle();

        LookupFragment fragment = new LookupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_default;
    }
}
