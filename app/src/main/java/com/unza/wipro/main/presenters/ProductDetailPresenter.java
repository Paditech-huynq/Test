package com.unza.wipro.main.presenters;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.AppConstans;
import com.unza.wipro.main.contracts.ProductDetailContract;
import com.unza.wipro.main.models.responses.GetProductDetailRSP;
import com.unza.wipro.transaction.cart.Cart;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailPresenter extends BasePresenter<ProductDetailContract.ViewImpl> implements ProductDetailContract.Presenter, AppConstans, Cart.CartChangeListener {
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    public void onCreate() {
        super.onCreate();
        app.addCartChangeListener(this);
        getView().showProductDetail(getView().getProduct());
        getProductDetailFromServer();
        onGetCurrentLocation();
    }

    private boolean requestGrantPermission() {
        Context context = getView().getContext();
        if (ActivityCompat.checkSelfPermission(getView().getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getView().requestGrantPermission();
            return false;
        }
        return true;
    }

    @SuppressLint("MissingPermission")
    private void onGetCurrentLocation() {
        if (mFusedLocationClient == null) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getView().getContext());
        }
        if (requestGrantPermission()) {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            try {
                                onGetLastLocationSuccess(location);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }

    private void onGetLastLocationSuccess(Location location) {
        if (location != null) {
            getView().updateViewWithCurrentLocation(location);
        } else {
            Log.i("Location", "Can't get current location");
        }
    }

    @Override
    public void onViewAppear() {
        super.onViewAppear();
        onCartUpdate();
        getView().showProductDetail(getView().getProduct());
        onGetCurrentLocation();
    }

    @Override
    public void onCartUpdate() {
        if (getView() != null) getView().updateCartCount();
    }

    private void getProductDetailFromServer() {
        try {
            app.getService().getProductDetail(String.valueOf(getView().getProduct().getId())).enqueue(new Callback<GetProductDetailRSP>() {
                @Override
                public void onResponse(Call<GetProductDetailRSP> call, Response<GetProductDetailRSP> response) {
                    try {
                        if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                            getView().showProductDetail(response.body().getProduct());
                            onGetCurrentLocation();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<GetProductDetailRSP> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
