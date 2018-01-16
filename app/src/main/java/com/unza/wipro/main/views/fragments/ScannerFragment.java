package com.unza.wipro.main.views.fragments;

import android.os.Bundle;
import android.util.Log;

import com.paditech.core.BaseFragment;
import com.unza.wipro.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScannerFragment extends BaseFragment implements ZBarScannerView.ResultHandler {
    private static final String TAG = "CAMERA";

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

    @Override
    public String getScreenTitle() {
        return getString(R.string.title_home_qr);
    }

    @Override
    public void setScreenTitle(String title) {
    }

    @BindView(R.id.layoutScanner)
    ZBarScannerView mScannerView;

    @Override
    public void initView() {
        super.initView();
        setupFormats();
        mScannerView.startCamera();
    }

    private void setupFormats() {
        List<BarcodeFormat> formats = new ArrayList<>();
        formats.add(BarcodeFormat.QRCODE);
        mScannerView.setFormats(formats);
    }

    @Override
    public void onViewAppear() {
        super.onViewAppear();
        if (mScannerView != null) {
            mScannerView.startCamera();
        }
    }

    @Override
    public void onViewDisappear() {
        try {
            if (mScannerView != null) {
                mScannerView.stopCameraPreview();
            }
        } catch (Exception ignored) {
        }
        super.onViewDisappear();
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.e(TAG, rawResult.getContents()); // Prints scan results
        Log.e(TAG, rawResult.getBarcodeFormat().getName()); // Prints the scan format (qrcode, pdf417 etc.)

        showToast(rawResult.getContents());
//        mScannerView.resumeCameraPreview(this);
    }

    public void openCamera() {
        mScannerView.startCamera();
    }

    public void stopCamera() {
        mScannerView.stopCamera();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mScannerView.stopCamera();
    }
}
