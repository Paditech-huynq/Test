package com.unza.wipro.main.views.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import com.unza.wipro.main.adapter.LookupAdaper;
import com.unza.wipro.main.contracts.LookupContract;
import com.unza.wipro.main.models.Product;
import com.unza.wipro.main.presenters.LookupPresent;
import com.unza.wipro.main.views.customs.VerticalSpacesItemDecoration;

import java.util.List;

import butterknife.BindView;

public class LookupFragment extends MVPFragment<LookupPresent> implements LookupContract.ViewImpl, BaseRecycleViewAdapter.LoadMoreListener {
    @BindView(R.id.edtSearch)
    EditText edtSearch;

    public static LookupFragment newInstance() {

        Bundle args = new Bundle();

        LookupFragment fragment = new LookupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.rcvLookup)
    RecyclerView mRecyclerView;

    LookupAdaper mAdaper;

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
    }

    @Override
    public void initView() {
        super.initView();
        setupRecycleView();
        setupSearchView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupSearchView() {
        edtSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP && edtSearch.getCompoundDrawables()[DRAWABLE_RIGHT] != null) {
                    if (event.getRawX() >= (edtSearch.getRight() - edtSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width() - edtSearch.getCompoundDrawablePadding() * 2)) {
                        // your action here
                        edtSearch.setText("");

                        return true;
                    }
                }
                return false;
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            final int DRAWABLE_LEFT = 0;
            final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            final int DRAWABLE_BOTTOM = 3;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getPresenter().searchByText();
                if (edtSearch.getText().length() > 0) {
                    edtSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lookup3, 0, R.drawable.ic_cancel, 0);
                } else {
                    edtSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lookup3, 0, 0, 0);
                }
            }
        });
    }

    private void setupRecycleView() {
        mAdaper = new LookupAdaper();
        mRecyclerView.addItemDecoration(new VerticalSpacesItemDecoration(getResources().getDimensionPixelOffset(R.dimen.padding_normal)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdaper);
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
    public void updateToListItem(List<Product> data) {
        mAdaper.setListProduct(data);
    }

    @Override
    public String getTextSearch() {
        return edtSearch.getText().toString();
    }

    @Override
    public void onLoadMore() {
        getPresenter().loadMore();
    }
}
