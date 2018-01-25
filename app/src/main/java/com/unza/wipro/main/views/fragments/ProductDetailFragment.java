package com.unza.wipro.main.views.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
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

    private ProductImageAdapter mImageAdapter;
    private Product mProduct;

    public static ProductDetailFragment newInstance(Product product) {

        Bundle args = new Bundle();

        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.mProduct = product;
        fragment.setArguments(args);
        return fragment;
    }

    public static ProductDetailFragment newInstance(Product product, Transition transition) {
        ProductDetailFragment fragment = ProductDetailFragment.newInstance(product);
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
            TextView shopPhone = view.findViewById(R.id.tv_shop_phone);
            shopName.setText(stock.getName());
            shopAddress.setText(stock.getAddress());
            shopPhone.setText("ĐT: 02240474043 - Khoảng cách 580m");
            mShopLayout.addView(view);
        }
    }

    @OnClick(R.id.imvAvatar)
    protected void back() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.btnRegister)
    protected void addToCart() {
        if (mProduct == null) return;
        app.editCart().insert(mProduct);
        showToast(getString(R.string.product_add_to_cart));
    }
}
