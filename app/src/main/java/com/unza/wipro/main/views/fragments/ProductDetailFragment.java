package com.unza.wipro.main.views.fragments;

import android.Manifest;
import android.animation.Animator;
import android.app.Activity;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.paditech.core.helper.StringUtil;
import com.paditech.core.helper.ViewHelper;
import com.paditech.core.mvp.MVPFragment;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.ProductImageAdapter;
import com.unza.wipro.main.contracts.ProductDetailContract;
import com.unza.wipro.main.models.Product;
import com.unza.wipro.main.models.ProductCategory;
import com.unza.wipro.main.models.ProductStock;
import com.unza.wipro.main.presenters.ProductDetailPresenter;
import com.unza.wipro.main.views.activities.MainActivity;
import com.unza.wipro.utils.AddToCartAnimation;
import com.unza.wipro.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ProductDetailFragment extends MVPFragment<ProductDetailPresenter> implements ProductDetailContract.ViewImpl, AppConstans {
    @BindView(R.id.vpgProduct)
    ViewPager mViewPager;
    @BindView(R.id.tv_product_name)
    TextView mNameText;
    @BindView(R.id.tv_product_code)
    TextView mCodeText;
    @BindView(R.id.tv_product_price)
    TextView mPriceText;
    @BindView(R.id.tv_product_desc)
    TextView mDescText;
    @BindView(R.id.layout_category)
    FlexboxLayout mCategoryLayout;
    @BindView(R.id.layout_shop)
    LinearLayout mShopLayout;
    @BindView(R.id.layout_header)
    CollapsingToolbarLayout mHeader;
    @BindView(R.id.tvCartAmount)
    TextView tvCartAmount;
    @BindView(R.id.btnCart)
    View btnCart;

    private ProductImageAdapter mImageAdapter;
    private Product mProduct;
    private int mFlag;
    public static final int COME_FROM_SCANNER_MAIN = 0;
    public static final int COME_FROM_PRODUCT_LIST = 1;
    public static final int COME_FROM_SCANNER_IN_ORDER_DETAIL = 2;

    public static ProductDetailFragment newInstance(Product product, int comeFromWhatFragment) {
        Bundle args = new Bundle();
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.mProduct = product;
        fragment.mFlag = comeFromWhatFragment;
        fragment.setArguments(args);
        return fragment;
    }

    public static ProductDetailFragment newInstance(Product product, Transition transition, int comeFromWhatFragment) {
        ProductDetailFragment fragment = ProductDetailFragment.newInstance(product, comeFromWhatFragment);
        fragment.setSharedElementEnterTransition(transition);
        fragment.setSharedElementReturnTransition(transition);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_product_detail;
    }

    @Override
    public String getScreenTitle() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        setupViewPager();
        setupHeader();
    }

    private void setupViewPager() {
        mImageAdapter = new ProductImageAdapter(getActivity());
        mViewPager.setAdapter(mImageAdapter);
        if (mProduct != null && mProduct.getProductThumbnail() != null) {
            mImageAdapter.addData(mProduct.getProductThumbnail());
        }
    }

    private void setupHeader() {
        mHeader.setContentScrimColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        mHeader.setStatusBarScrimColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        updateCartCount();
    }

    @Override
    public void onViewDisappear() {
        super.onViewDisappear();
        ((MainActivity) getActivity()).setShowHeader(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void onViewAppear() {
        super.onViewAppear();
        ((MainActivity) getActivity()).setShowHeader(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        switch (mFlag){
            case COME_FROM_SCANNER_IN_ORDER_DETAIL:
                btnCart.setVisibility(View.GONE);
                break;
            case COME_FROM_PRODUCT_LIST:
                btnCart.setVisibility(View.VISIBLE);
                break;
            case COME_FROM_SCANNER_MAIN:
                btnCart.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public Product getProduct() {
        return mProduct;
    }

    @Override
    public void showProductDetail(Product product) {
        if (product == null) return;
        mProduct = product;
        mNameText.setText(product.getName());
        mCodeText.setText(getString(R.string.product_detail_code, product.getCode()));
        mPriceText.setText(getString(R.string.product_detail_price, StringUtil.formatMoney(product.getPrice())));
        mDescText.setText(product.getNote());
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            mImageAdapter.setData(product.getImages());
        }
        showCategories(product);
        showShops(product);
    }

    private void showCategories(Product product) {
        if (product == null) return;
        mCategoryLayout.removeAllViews();
        List<ProductCategory> categories = product.getCategories();
        for (ProductCategory category : categories) {
            if (!StringUtil.isEmpty(category.getName())) {
                TextView textView = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.view_product_category, null);
                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                int margin = getResources().getDimensionPixelSize(R.dimen.padding_tiny);
                params.setMargins(margin, margin, margin, margin);
                textView.setText(category.getName());
                mCategoryLayout.addView(textView, params);
            }
        }
    }

    private void showShops(Product product) {
        if (product == null) return;
        mShopLayout.removeAllViews();
        for (ProductStock stock : product.getStocks()) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_product_available_at, null);
            TextView shopName = view.findViewById(R.id.tv_shop_name);
            TextView shopAddress = view.findViewById(R.id.tv_shop_address);
            TextView tvPhone = view.findViewById(R.id.tv_shop_phone);
            tvPhone.setText(getString(R.string.stock_phone_only, stock.getPhone()));
            shopName.setText(stock.getName());
            shopAddress.setText(stock.getAddress());
            view.setTag(stock);
            mShopLayout.addView(view);
        }
    }

    @OnClick(R.id.imvAvatar)
    protected void back() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.btnCart)
    protected void showCart() {
        switchFragment(OrderDetailFragment.newInstance(), true);
    }

    @Override
    public void updateCartCount() {
        int cartItemCount = app.getCurrentCart().getTotalQuantity();
        tvCartAmount.setVisibility(cartItemCount <= 0 ? View.GONE : View.VISIBLE);
        String count = cartItemCount <= 99 ? String.valueOf(cartItemCount) : "99+";
        tvCartAmount.setText(count);
    }

    @Override
    public void requestGrantPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
    }

    @Override
    public void updateViewWithCurrentLocation(Location location) {
        if (location == null) {
            return;
        }
        mShopLayout.getChildCount();
        for (int i = 0; i < mShopLayout.getChildCount(); i++) {
            View view = mShopLayout.getChildAt(i);
            TextView shopPhone = view.findViewById(R.id.tv_shop_phone);
            ProductStock stock = (ProductStock) view.getTag();
            if (stock != null) {
                Float distance = Utils.getDistance(location, stock.getLocation());
                String unit = "m";
                if (distance > 1000) {
                    distance = distance / 1000;
                    unit = "km";
                    distance = (float) Utils.round(distance, 1);
                } else {
                    distance = (float) Utils.round(distance, 0);
                }

                ViewHelper.setText(shopPhone,
                        getString(R.string.stock_phone_and_distance,
                                stock.getPhone(), distance % 1 == 0 ? distance.intValue() : distance + EMPTY,
                                unit), null);
            }
        }
    }

    @OnClick(R.id.btnRegister)
    protected void addToCart() {
        if(mFlag == COME_FROM_PRODUCT_LIST || mFlag == COME_FROM_SCANNER_MAIN) {
            if (mProduct == null) return;
            makeFlyAnimation(mViewPager);
            return;
        }
        app.editCart().insert(mProduct);
        showToast(getResources().getString(R.string.scan_qr_success));
    }

    private void makeFlyAnimation(View targetView) {
        Activity activity = getActivity();

        new AddToCartAnimation().attachActivity(activity)
                .setTargetView(targetView)
                .setItemDuration(500)
                .setMoveDuration(500)
                .setDestView(btnCart)
                .setAnimationListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        app.editCart().insert(mProduct);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).startAnimation();
    }
}
