package com.unza.wipro.main.views.fragments;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.paditech.core.DisableTouchView;
import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.mvp.MVPFragment;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.ProductListAdapter;
import com.unza.wipro.main.contracts.ProductPageContract;
import com.unza.wipro.main.models.Product;
import com.unza.wipro.main.presenters.ProductPagePresenter;
import com.unza.wipro.main.views.activities.MainActivity;
import com.unza.wipro.main.views.customs.DynamicHeightImageView;
import com.unza.wipro.main.views.customs.StaggeredSpacesItemDecoration;
import com.unza.wipro.utils.AddToCartAnimation;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

public class ProductPageFragment extends MVPFragment<ProductPagePresenter> implements ProductPageContract.ViewImpl {
    @BindView(R.id.rcvProduct)
    RecyclerView mRecyclerView;
    @BindView(R.id.layoutLoading)
    ProgressBar layoutLoading;
    @BindView(R.id.disableTouchView)
    DisableTouchView disableTouchView;

    ProductListAdapter mAdapter;
    private String categoryId;

    public static ProductPageFragment newInstance() {

        Bundle args = new Bundle();

        ProductPageFragment fragment = new ProductPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ProductPageFragment newInstance(String categoryId) {
        ProductPageFragment fragment = ProductPageFragment.newInstance();
        fragment.categoryId = categoryId;
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home_product_page;
    }

    @Override
    public String getScreenTitle() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        setupRecycleView();
    }

    private void setupRecycleView() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        StaggeredSpacesItemDecoration spacesItemDecoration = new StaggeredSpacesItemDecoration(getResources().getDimensionPixelOffset(R.dimen.padding_small));
        if (mAdapter == null) {
            mAdapter = new ProductListAdapter(this.getContext());
        }
        mRecyclerView.addItemDecoration(spacesItemDecoration);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleViewAdapter.BaseViewHolder holder, View view, int position) {
                startTransition(view);
            }
        });

        mAdapter.setOnLoadMoreListener(new BaseRecycleViewAdapter.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                getPresenter().onLoadMore();
            }
        });

        mAdapter.setOnProductItemClickListenner(new ProductListAdapter.OnProductItemClickListenner() {
            @Override
            public void onAddCartButtonClick(View view, int index) {
                makeFlyAnimation((ImageView) view);
            }
        });
    }

    private void startTransition(View view) {
        DynamicHeightImageView imvProduct = view.findViewById(R.id.imvProduct);
        ViewCompat.setTransitionName(imvProduct, Calendar.getInstance().getTimeInMillis() + "_");

        ProductDetailFragment detailFragment = ProductDetailFragment.newInstance(TransitionInflater.from(ProductPageFragment.this.getContext()).
                inflateTransition(R.transition.change_image_transform));

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(com.paditech.core.R.anim.abc_fade_in, com.paditech.core.R.anim.abc_fade_out, com.paditech.core.R.anim.abc_fade_in, com.paditech.core.R.anim.abc_fade_out);
        ft.addSharedElement(imvProduct,
                getString(R.string.transition_list_product_to_product_detail))
                .replace(R.id.container, detailFragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    @Override
    protected boolean isKeepFragment() {
        return true;
    }

    private void makeFlyAnimation(ImageView targetView) {
        MainActivity activity = (MainActivity) getActivity();

        new AddToCartAnimation().attachActivity(activity)
                .setTargetView(targetView)
                .setItemDuration(500)
                .setMoveDuration(500)
                .setDestView(activity.getCartView())
                .setAnimationListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        showToast("Add to Cart");
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).startAnimation();

    }

    @Override
    public void notifyListProduct(List<Product> productList) {
        mAdapter.addProductList(productList);
    }

    public String getCategoryId() {
        return categoryId;
    }

    @Override
    public void showProgressDialog(boolean isShown) {
        layoutLoading.setVisibility(isShown ? View.VISIBLE : View.GONE);
        disableTouchView.setVisibility(isShown ? View.VISIBLE : View.GONE);
    }
}

