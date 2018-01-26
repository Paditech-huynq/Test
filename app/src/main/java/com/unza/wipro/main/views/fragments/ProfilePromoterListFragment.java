package com.unza.wipro.main.views.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.helper.ViewHelper;
import com.paditech.core.mvp.MVPFragment;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.ProfileListAdapter;
import com.unza.wipro.main.adapter.ProfilePromoterListAdapter;
import com.unza.wipro.main.contracts.ProfilePromoterListContract;
import com.unza.wipro.main.presenters.ProfilePromoterListFresenter;
import com.unza.wipro.main.views.customs.VerticalSpacesItemDecoration;
import com.unza.wipro.transaction.user.Customer;
import com.unza.wipro.transaction.user.Promoter;

import java.util.List;

import butterknife.BindView;

public class ProfilePromoterListFragment extends MVPFragment<ProfilePromoterListFresenter> implements ProfilePromoterListContract.ViewImpl {
    @BindView(R.id.rcvProfile)
    RecyclerView mRecyclerView;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    private final static String LAST_SCROLL_Y = "last_scroll_y";
    private ProfilePromoterListAdapter mAdapter;
    private static final int DRAWABLE_RIGHT = 2;
    private static final int SEARCH_DELAY = 500;
    private Runnable searchRunnable = new Runnable() {
        @Override
        public void run() {
            getPresenter().searchByKeyWord();
        }
    };
    private Handler searchHandler = new Handler();

    public static ProfilePromoterListFragment newInstance() {

        Bundle args = new Bundle();

        ProfilePromoterListFragment fragment = new ProfilePromoterListFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_profile_list;
    }

    @Override
    public String getScreenTitle() {
        return getString(R.string.title_list_member);
    }

    @Override
    public void initView() {
        super.initView();
        setupRecycleView();
        setupSearchView();
        setupUI();
    }

    private void setupUI() {
        btnRegister.setVisibility(View.GONE);
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
        mAdapter = new ProfilePromoterListAdapter();
        mRecyclerView.addItemDecoration(new VerticalSpacesItemDecoration(getResources().getDimensionPixelOffset(R.dimen.padding_normal)));
        ViewHelper.setupRecycle(mRecyclerView, new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false), mAdapter);
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
    public void onSaveViewInstanceState(@NonNull Bundle outState) {
        super.onSaveViewInstanceState(outState);
        outState.putInt(LAST_SCROLL_Y, mRecyclerView.computeVerticalScrollOffset());
    }

    @Override
    public void onRestoreViewState(@Nullable Bundle savedInstanceState) {
        super.onRestoreViewState(savedInstanceState);
        if (savedInstanceState != null) {
            mRecyclerView.scrollBy(0, savedInstanceState.getInt(LAST_SCROLL_Y));
        }
    }
    @Override
    public void addItemToList(List<Promoter> customerList) {
        mAdapter.addItemToList(customerList);
    }

    @Override
    public String getCurrentKeyWord() {
        return edtSearch.getText().toString();
    }

    public void refreshData(List<Promoter> customerList) {
        mAdapter.refreshData(customerList);
    }
}
