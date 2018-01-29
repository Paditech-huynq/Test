package com.unza.wipro.main.views.fragments;

import android.os.Bundle;

import com.paditech.core.BaseFragment;
import com.paditech.core.helper.FragmentHelper;
import com.unza.wipro.R;
import com.unza.wipro.main.views.activities.MainActivity;

public class FakeScannerFragment extends BaseFragment {
    public static FakeScannerFragment newInstance() {

        Bundle args = new Bundle();

        FakeScannerFragment fragment = new FakeScannerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return 0;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public String getScreenTitle() {
        return getString(R.string.title_home_qr);
    }

    @Override
    public void onViewAppear() {
        super.onViewAppear();
        try {
            ((MainActivity) getActivity()).openCamera(true);
            ((ScannerFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.frmCamera)).onViewAppear();
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onViewDisappear() {
        super.onViewDisappear();
        try {
            ((MainActivity) getActivity()).openCamera(false);
            ((ScannerFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.frmCamera)).onViewDisappear();
        } catch (Exception ignored) {
        }
    }

    @Override
    public boolean isActionShow(int resId) {
        return getActivity().getSupportFragmentManager().findFragmentById(FragmentHelper.getRoot()) instanceof HomeFragment && resId != R.id.btnTrash;
    }
}
