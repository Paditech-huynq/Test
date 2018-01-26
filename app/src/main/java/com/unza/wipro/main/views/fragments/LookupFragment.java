package com.unza.wipro.main.views.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.mvp.MVPFragment;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.LookupAdapter;
import com.unza.wipro.main.contracts.LookupContract;
import com.unza.wipro.main.models.Product;
import com.unza.wipro.main.presenters.LookupPresent;
import com.unza.wipro.main.views.customs.VerticalSpacesItemDecoration;

import java.util.List;

import butterknife.BindView;

public class LookupFragment extends MVPFragment<LookupPresent> implements LookupContract.ViewImpl, BaseRecycleViewAdapter.LoadMoreListener {
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.rcvLookup)
    RecyclerView mRecyclerView;
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

    @BindView(R.id.layoutLoading)
    View layoutLoading;

    public static LookupFragment newInstance() {

        Bundle args = new Bundle();

        LookupFragment fragment = new LookupFragment();
        fragment.setArguments(args);
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
        super.setScreenTitle(title);
    }

    @Override
    public void initView() {
        super.initView();
        setupRecycleView();
        setupSearchView();
    }

    private void setupSearchView() {
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
    }

    private void setupRecycleView() {
        mAdapter = new LookupAdapter();
        mRecyclerView.addItemDecoration(new VerticalSpacesItemDecoration(getResources().getDimensionPixelOffset(R.dimen.padding_normal)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(new BaseRecycleViewAdapter.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                getPresenter().onLoadMore();
            }
        });
        setPullToRefreshColor(Color.BLUE);
        enablePullToRefresh(true);
        setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().onRefresh();
            }
        });
    }

    @Override
    protected boolean isKeepFragment() {
        return true;
    }

    @Override
    public boolean isActionShow(int resId) {
        if (resId == R.id.btnCart) {
            return true;
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
    public void onLoadMore() {
        getPresenter().onLoadMore();
    }

    @Override
    public void showProgressDialog(boolean isShown) {
        layoutLoading.setVisibility(isShown ? View.VISIBLE : View.GONE);
    }
}
