package com.unza.wipro.main.views.fragments;

import android.content.Context;
import android.os.Bundle;

import com.paditech.core.BaseFragment;
import com.paditech.core.helper.FragmentHelper;
import com.squareup.otto.Subscribe;
import com.unza.wipro.AppAction;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;

public class FakeScannerFragment extends BaseFragment implements AppConstans {
    private boolean isFocus = true;

    public static FakeScannerFragment newInstance() {

        Bundle args = new Bundle();

        FakeScannerFragment fragment = new FakeScannerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.view_blank;
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
            if (isFocus) {
                bus.post(AppAction.REQUEST_CAMERA_OPEN);
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onViewDisappear() {
        super.onViewDisappear();
        try {
            bus.post(AppAction.REQUEST_CAMERA_CLOSE);
        } catch (Exception ignored) {
        }
    }

    @Override
    public boolean isActionShow(int resId) {
        return getActivity().getSupportFragmentManager().findFragmentById(FragmentHelper.getRoot()) instanceof HomeFragment && resId != R.id.btnTrash;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bus.register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        bus.unregister(this);
    }

    @Subscribe
    public void onAction(AppAction action) {
        switch (action) {
            case NOTIFY_FAKE_SCANNER_FOCUS:
                isFocus = true;
                break;
            case NOTIFY_FAKE_SCANNER_UN_FOCUS:
                isFocus = false;
                break;
        }
    }
}
