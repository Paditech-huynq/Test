package com.unza.wipro.main.views.fragments;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.paditech.core.BaseFragment;
import com.paditech.core.helper.FragmentHelper;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.unza.wipro.R;
import com.unza.wipro.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScannerFragment extends BaseFragment implements ZBarScannerView.ResultHandler, OnTabSelectListener {
    private static final String TAG = "CAMERA";

    @BindView(R.id.bottomBar2)
    BottomBar mBottomBar;

    boolean isCameraOpen;

    public static ScannerFragment newInstance() {
        Bundle args = new Bundle();
        ScannerFragment fragment = new ScannerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home_scanner;
    }


    @BindView(R.id.layoutScanner)
    ZBarScannerView mScannerView;

    private void setupFormats() {
        List<BarcodeFormat> formats = new ArrayList<>();
        formats.add(BarcodeFormat.QRCODE);
        mScannerView.setFormats(formats);
    }

    @Override
    public void onViewAppear() {
        super.onViewAppear();
        try {
            mBottomBar.setVisibility(getActivity().getSupportFragmentManager().findFragmentById(FragmentHelper.getRoot()) instanceof HomeFragment ? View.VISIBLE : View.GONE);
            getView().setVisibility(View.VISIBLE);
            setupFormats();
            mScannerView.setResultHandler(this);
            mBottomBar.selectTabAtPosition(2);
            mBottomBar.setOnTabSelectListener(this);
            Utils.checkCameraPermission(this.getActivity());
            openCamera();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewDisappear() {
        super.onViewDisappear();
        getView().setVisibility(View.GONE);
        stopCamera(350);
    }

    public void openCamera() {
        try {
            if (isCameraOpen) {
                return;
            }

            isCameraOpen = true;
            mScannerView.startCamera();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopCamera(long delay) {
        if (!isCameraOpen) {
            return;
        }
        isCameraOpen = false;
        mScannerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (isCameraOpen) {
                        return;
                    }
                    getView().setVisibility(View.GONE);
                    mScannerView.stopCamera();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, delay);
    }

    @Override
    public void handleResult(Result rawResult) {
        //todo: handle scan result here
        Log.e(TAG, rawResult.getContents()); // Prints scan results
        Log.e(TAG, rawResult.getBarcodeFormat().getName()); // Prints the scan format (qrcode, pdf417 etc.)
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getActivity().getApplicationContext(), notification);
        r.play();
        showToast(rawResult.getContents());
    }

    @Override
    public void onTabSelected(int tabId) {
        if (tabId == R.id.tab_qr) {
            return;
        }
        if (getActivity().getSupportFragmentManager().findFragmentById(FragmentHelper.getRoot()) instanceof HomeFragment) {
            HomeFragment home = (HomeFragment) getActivity().getSupportFragmentManager().findFragmentById(FragmentHelper.getRoot());
            home.onTabSelected(tabId);
        }
        stopCamera(50);
    }

    @Override
    public void setScreenTitle(String title) {
    }
}
