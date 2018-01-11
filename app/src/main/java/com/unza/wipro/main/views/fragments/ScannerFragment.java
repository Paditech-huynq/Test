package com.unza.wipro.main.views.fragments;

import android.os.Bundle;

import com.paditech.core.BaseFragment;
import com.unza.wipro.R;

public class ScannerFragment extends BaseFragment {
    public static ScannerFragment newInstance() {

        Bundle args = new Bundle();

        ScannerFragment fragment = new ScannerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_default;
    }
}
