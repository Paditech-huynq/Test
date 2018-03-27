package com.pshop.app.main.views.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.helper.Utils;
import com.paditech.core.helper.ViewHelper;
import com.paditech.core.mvp.MVPFragment;
import com.pshop.app.R;
import com.pshop.app.main.adapter.ProfilePromoterListAdapter;
import com.pshop.app.main.contracts.ProfilePromoterListContract;
import com.pshop.app.main.presenters.ProfilePromoterLisPresenter;
import com.pshop.app.main.views.customs.VerticalSpacesItemDecoration;
import com.pshop.app.transaction.user.Promoter;

import java.util.List;

import butterknife.BindView;

public class ProfilePromoterListFragment extends MVPFragment<ProfilePromoterLisPresenter> implements ProfilePromoterListContract.ViewImpl {
    private final static String LAST_SCROLL_Y = "last_scroll_y";
    private static final int DRAWABLE_RIGHT = 2;
    private static final int SEARCH_DELAY = 500;
    @BindView(R.id.rcvProfile)
    RecyclerView mRecyclerView;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    private ProfilePromoterListAdapter mAdapter;
    private Runnable searchRunnable = new Runnable() {
        @Override
        public void run() {
            getPresenter().searchByKeyWord();
        }
    };
    private Handler searchHandler = new Handler();
    private TextWatcher textChangeListenner = new TextWatcher() {
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
    };

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
//        setupSearchView();
        setupUI();
    }

    @Override
    public void onViewAppear() {
        super.onViewAppear();
        setupSearchView();
    }

    @Override
    public void onViewDisappear() {
        super.onViewDisappear();
        edtSearch.removeTextChangedListener(textChangeListenner);
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

        edtSearch.addTextChangedListener(textChangeListenner);

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Utils.hideSoftKeyboard(ProfilePromoterListFragment.this.getActivity());
                }
                return false;
            }
        });
    }

    private void setupRecycleView() {
        if (mAdapter == null) {
            mAdapter = new ProfilePromoterListAdapter();
        }
        mRecyclerView.addItemDecoration(new VerticalSpacesItemDecoration(getResources().getDimensionPixelOffset(R.dimen.padding_normal)));
        ViewHelper.setupRecycle(mRecyclerView, new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false), mAdapter);
        mAdapter.setOnLoadMoreListener(new BaseRecycleViewAdapter.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                getPresenter().onLoadMore();
            }
        });
        mAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleViewAdapter.BaseViewHolder holder, View view, int position) {
                switchFragment(ProfileMemberGroupFragment.newInstance(mAdapter.getItem(position)), true);
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

    @Override
    public void scrollToTop() {
        mRecyclerView.scrollToPosition(0);
    }
}
