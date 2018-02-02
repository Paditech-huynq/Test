package com.unza.wipro.main.views.fragments;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.ImageView;

import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.mvp.MVPFragment;
import com.unza.wipro.AppConstans;
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

public class ProductPageFragment extends MVPFragment<ProductPagePresenter> implements ProductPageContract.ViewImpl, AppConstans {
    @BindView(R.id.rcvProduct)
    RecyclerView mRecyclerView;

    @BindView(R.id.layoutLoading)
    View layoutLoading;

    private ProductListAdapter mAdapter;
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

    @Override
    public void onViewAppear() {
        super.onViewAppear();
        setupPullToRefresh();
    }

    private void setupPullToRefresh() {
        setPullToRefreshColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        enablePullToRefresh(true);
        setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().onRefresh();
            }
        });
    }

    private void setupRecycleView() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        StaggeredSpacesItemDecoration spacesItemDecoration = new StaggeredSpacesItemDecoration(getResources().getDimensionPixelOffset(R.dimen.padding_small));
        if (mAdapter == null) {
            mAdapter = new ProductListAdapter();
        }
        mRecyclerView.addItemDecoration(spacesItemDecoration);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleViewAdapter.BaseViewHolder holder, View view, int position) {
                startTransition(view, position);
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
                Product product = mAdapter.getItem(index);
                makeFlyAnimation((ImageView) view, product);
            }
        });
    }

    private void insertItemToCart(Product product) {
        if (product == null) {
            return;
        }
        app.editCart().insert(product);
    }

    private void startTransition(View view, int position) {
        DynamicHeightImageView imvProduct = view.findViewById(R.id.imvProduct);
        ViewCompat.setTransitionName(imvProduct, String.valueOf(Calendar.getInstance().getTimeInMillis()));

        ProductDetailFragment detailFragment = ProductDetailFragment.newInstance(mAdapter.getItem(position), TransitionInflater.from(ProductPageFragment.this.getContext()).
                inflateTransition(R.transition.change_image_transform));

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(com.paditech.core.R.anim.abc_fade_in, com.paditech.core.R.anim.abc_fade_out, com.paditech.core.R.anim.abc_fade_in, com.paditech.core.R.anim.abc_fade_out);
        ft.addSharedElement(imvProduct,
                getString(R.string.transition_list_product_to_product_detail))
                .replace(R.id.container, detailFragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    private void makeFlyAnimation(ImageView targetView, final Product product) {
        if (product == null) return;
        MainActivity activity = (MainActivity) getActivity();

        String url = product.getProductThumbnail() != null ? product.getProductThumbnail().getLink() : null;
        int top = getResources().getDimensionPixelSize(R.dimen.actionbar_button_size) + activity.getLayoutHeader().getHeight();
        new AddToCartAnimation().attachActivity(activity)
                .setTargetView(targetView)
                .setItemDuration(500)
                .setMoveDuration(500)
                .setDestView(activity.getCartView())
                .setImageSource(url)
                .setTopLimit(top)
                .setAnimationListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        insertItemToCart(product);
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
    public void addItemToList(List<Product> productList) {
        mAdapter.insertData(productList);
    }

    @Override
    public void refreshData(List<Product> productList) {
        mAdapter.replaceData(productList);
    }

    @Override
    public void scrollToTop() {
        mRecyclerView.smoothScrollToPosition(0);
    }

    public String getCategoryId() {
        return categoryId;
    }

    @Override
    public void showProgressDialog(boolean isShown) {
        layoutLoading.setVisibility(isShown ? View.VISIBLE : View.GONE);
    }
}

