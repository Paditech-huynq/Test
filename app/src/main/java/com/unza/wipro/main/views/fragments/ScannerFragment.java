package com.unza.wipro.main.views.fragments;

import android.content.Context;
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
import com.squareup.otto.Subscribe;
import com.unza.wipro.AppAction;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
import com.unza.wipro.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScannerFragment extends BaseFragment implements ZBarScannerView.ResultHandler, OnTabSelectListener, AppConstans {
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

    public void openCamera() {
        try {
            mBottomBar.setVisibility(getActivity().getSupportFragmentManager().findFragmentById(FragmentHelper.getRoot()) instanceof HomeFragment ? View.VISIBLE : View.GONE);
            getView().setVisibility(View.VISIBLE);
            setupFormats();
            mScannerView.setResultHandler(this);
            mBottomBar.selectTabAtPosition(2);
            mBottomBar.setOnTabSelectListener(this);
            Utils.checkCameraPermission(this.getActivity());
            mScannerView.startCamera();
            isCameraOpen = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopCamera(long delay) {
        getView().setVisibility(View.GONE);
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

    @Subscribe
    public void onAction(AppAction action) {
        Log.e("Action",action+"");
        switch (action) {
            case REQUEST_CAMERA_OPEN:
                openCamera();
                break;
            case REQUEST_CAMERA_CLOSE:
                stopCamera(350);
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            bus.register(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        try {
            bus.unregister(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        try {
            mScannerView.stopCameraPreview();
            mScannerView.stopCamera();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
