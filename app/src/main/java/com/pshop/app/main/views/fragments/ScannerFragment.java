package com.pshop.app.main.views.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.paditech.core.BaseFragment;
import com.paditech.core.helper.FragmentHelper;
import com.paditech.core.helper.PrefUtils;
import com.paditech.core.helper.StringUtil;
import com.pshop.app.AppAction;
import com.pshop.app.AppConstans;
import com.pshop.app.R;
import com.pshop.app.main.models.Product;
import com.pshop.app.main.models.responses.GetProductDetailRSP;
import com.pshop.app.services.AppClient;
import com.pshop.app.transaction.user.Customer;
import com.pshop.app.transaction.user.Promoter;
import com.pshop.app.utils.Utils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScannerFragment extends BaseFragment implements ZBarScannerView.ResultHandler, OnTabSelectListener, AppConstans {
    private static final String TAG = "CAMERA";
    @BindView(R.id.bottomBar2)
    BottomBar mBottomBar;
    @BindView(R.id.tv_count_scanner)
    TextView tvCount;

    boolean isCameraOpen;
    @BindView(R.id.layoutScanner)
    ZBarScannerView mScannerView;
    private int Count = 3;
    private Handler mHandler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (Count == 0) {
                mScannerView.resumeCameraPreview(ScannerFragment.this);
                Count = 3;
                tvCount.setText(EMPTY);
                return;
            }
            tvCount.setText(String.valueOf(Count));
            Count--;
            mHandler.postDelayed(this, 1000);
        }
    };

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

    private void setupFormats() {
        List<BarcodeFormat> formats = new ArrayList<>();
//        formats.add(BarcodeFormat.QRCODE);
        mScannerView.setFormats(BarcodeFormat.ALL_FORMATS);
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
            setScreenTitle(getScreenTitle());
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
    public void handleResult(final Result rawResult) {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getActivity().getApplicationContext(), notification);
        r.play();
        if (StringUtil.isEmpty(rawResult.getContents())) {
            mScannerView.resumeCameraPreview(ScannerFragment.this);
        } else {
            if (getContext() == null) {
                return;
            }
            if (StringUtil.isEmpty(PrefUtils.getPreferences(getContext(), rawResult.getContents(), EMPTY))) {
                AppClient.newInstance().getService().getProductDetailFromBarcode(rawResult.getContents()).enqueue(new Callback<GetProductDetailRSP>() {
                    @Override
                    public void onResponse(Call<GetProductDetailRSP> call, Response<GetProductDetailRSP> response) {
                        try {
                            if (response.body().getResult() == 0) {
                                showAlertDialog(getContext().getString(R.string.scan_title_product, rawResult.getContents()), getContext().getString(R.string.scan_qr_not_find_product), "ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        mScannerView.resumeCameraPreview(ScannerFragment.this);
                                    }
                                });
                                return;
                            }
                            if (response.body().getProduct() == null) {
                                return;
                            }
                            actionScan(response.body().getProduct(), rawResult.getContents());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetProductDetailRSP> call, Throwable t) {
                        t.printStackTrace();
                        showToast(getContext().getString(R.string.scan_qr_failure));
                    }
                });
            } else {
                Product product = new Gson().fromJson(PrefUtils.getPreferences(getContext(), rawResult.getContents(), EMPTY), Product.class);
                actionScan(product, EMPTY);
            }
        }
    }

    private void actionScan(Product product, String barcode) {
        if (getContext() == null) {
            return;
        }
        if (!StringUtil.isEmpty(barcode)) {
            PrefUtils.savePreferences(getContext(), barcode, new Gson().toJson(product));
        }
        showToast(getContext().getString(R.string.scan_qr_success));
        app.editCart().insert(product);
        if (AppConstans.app.getCurrentUser() instanceof Customer || AppConstans.app.getCurrentUser() == null) {
            for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {
                if (fragment instanceof HomeFragment) {
                    switchFragment(ProductDetailFragment.newInstance(product, ProductDetailFragment.COME_FROM_SCANNER_MAIN), true);
                    return;
                }
            }
            switchFragment(ProductDetailFragment.newInstance(product, ProductDetailFragment.COME_FROM_SCANNER_IN_ORDER_DETAIL), true);
            return;
        }
        if (AppConstans.app.getCurrentUser() instanceof Promoter) {
            mHandler.post(runnable);
        }
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

    @Subscribe
    public void onAction(AppAction action) {
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

    @Override
    public String getScreenTitle() {
        return getString(R.string.title_home_qr);
    }

    @Override
    public void setScreenTitle(String title) {
        if (isCameraOpen) {
            super.setScreenTitle(title);
        }
    }
}
