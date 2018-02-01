package com.unza.wipro.main.views.fragments;

import android.animation.Animator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionInflater;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.helper.Utils;
import com.paditech.core.mvp.MVPFragment;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.LookupAdapter;
import com.unza.wipro.main.contracts.LookupContract;
import com.unza.wipro.main.models.Product;
import com.unza.wipro.main.presenters.LookupPresent;
import com.unza.wipro.main.views.activities.MainActivity;
import com.unza.wipro.main.views.customs.VerticalSpacesItemDecoration;
import com.unza.wipro.utils.AddToCartAnimation;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

import static com.unza.wipro.AppConstans.app;

public class LookupFragment extends MVPFragment<LookupPresent> implements LookupContract.ViewImpl, BaseRecycleViewAdapter.LoadMoreListener {
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.rcvLookup)
    RecyclerView mRecyclerView;
    @BindView(R.id.noResult)
    View noResult;
    private static final int DRAWABLE_RIGHT = 2;
    private static final int SEARCH_DELAY = 500;
    private Runnable searchRunnable = new Runnable() {
        @Override
        public void run() {
            getPresenter().searchByKeyWord();
        }
    };
    private Handler searchHandler = new Handler();
    LookupAdapter mAdapter;
    private boolean isShowCartButton;

    @BindView(R.id.layoutLoading)
    View layoutLoading;

    public static LookupFragment newInstance() {

        Bundle args = new Bundle();

        LookupFragment fragment = new LookupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static LookupFragment newInstance(boolean isFromCart) {
        LookupFragment fragment = newInstance();
        fragment.isShowCartButton = isFromCart;
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home_lookup;
    }

    @Override
    public String getScreenTitle() {
        return getString(R.string.title_home_lookup);
    }

    @Override
    public void setScreenTitle(String title) {
        if (!isShowCartButton){
            super.setScreenTitle(getString(R.string.title_home_lookup));
        }
    }

    @Override
    public void initView() {
        super.initView();
        setupRecycleView();
        setupSearchView();
    }

    private void setupSearchView() {
        edtSearch.setHint(getString(R.string.hint_lookup_input));
        edtSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && edtSearch.getCompoundDrawables()[DRAWABLE_RIGHT] != null) {
                    if (event.getRawX() >= (edtSearch.getRight() - edtSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width() - edtSearch.getCompoundDrawablePadding() * 2)) {
                        edtSearch.setText("");
                        return true;
                    }
                }
                return false;
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchHandler.removeCallbacks(searchRunnable);
                if (edtSearch.getText().length() > 0) {
                    edtSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lookup3, 0, R.drawable.ic_cancel, 0);
                } else {
                    edtSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lookup3, 0, 0, 0);
                }
                searchHandler.postDelayed(searchRunnable, SEARCH_DELAY);
            }
        });

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Utils.hideSoftKeyboard(LookupFragment.this.getActivity());
                }
                return false;
            }
        });
    }

    private void setupRecycleView() {
        if (mAdapter == null) {
            mAdapter = new LookupAdapter();
        }
        mRecyclerView.addItemDecoration(new VerticalSpacesItemDecoration(getResources().getDimensionPixelOffset(R.dimen.padding_normal)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(new BaseRecycleViewAdapter.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                getPresenter().onLoadMore();
            }
        });
        mAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleViewAdapter.BaseViewHolder holder, View view, int position) {
                startTransition(view, position);
            }
        });

        mAdapter.setOnProductItemClickListenner(new LookupAdapter.OnProductItemClickListenner() {
            @Override
            public void onAddCartButtonClick(View view, int index) {
                Product product = mAdapter.getItem(index);
                if (isShowCartButton) {
                    makeFlyAnimation((ImageView) view, product);
                } else {
                    insertItemToCart(product);
                }
            }
        });
        setPullToRefreshColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        enablePullToRefresh(true);
        setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().onRefresh();
            }
        });
    }

    private void startTransition(View view, int position) {
        ImageView imvProduct = view.findViewById(R.id.imvProduct);
        ViewCompat.setTransitionName(imvProduct, String.valueOf(Calendar.getInstance().getTimeInMillis()));

        ProductDetailFragment detailFragment = ProductDetailFragment.newInstance(mAdapter.getItem(position), TransitionInflater.from(LookupFragment.this.getContext()).
                inflateTransition(R.transition.change_image_transform));

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(com.paditech.core.R.anim.abc_fade_in, com.paditech.core.R.anim.abc_fade_out, com.paditech.core.R.anim.abc_fade_in, com.paditech.core.R.anim.abc_fade_out);
        ft.addSharedElement(imvProduct,
                getString(R.string.transition_list_product_to_product_detail))
                .replace(R.id.container, detailFragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    private void insertItemToCart(Product product) {
        if (product == null) {
            return;
        }
        app.editCart().insert(product);
    }

    private void makeFlyAnimation(ImageView targetView, final Product product) {
        if (product == null) return;

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

//    @Override
//    protected boolean isKeepFragment() {
//        return true;
//    }

    @Override
    public boolean isActionShow(int resId) {
        if (resId == R.id.btnCart) {
            return isShowCartButton;
        }
        return super.isActionShow(resId);
    }

    @Override
    public void updateItemToList(List<Product> productList) {
        mAdapter.updateItemToList(productList);
    }

    @Override
    public void refreshProductList(List<Product> productList) {
        mAdapter.refreshProductList(productList);
    }

    @Override
    public String getCurrentKeyword() {
        return edtSearch.getText().toString();
    }

    @Override
    public void showMessageNoResult(boolean isShow) {
        noResult.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onLoadMore() {
        getPresenter().onLoadMore();
    }

    @Override
    public void showProgressDialog(boolean isShown) {
        layoutLoading.setVisibility(isShown ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onNetworkOnline() {
        super.onNetworkOnline();
        if (mAdapter.getItemCount() == 0) {
            getPresenter().onRefresh();
        }
    }
}
