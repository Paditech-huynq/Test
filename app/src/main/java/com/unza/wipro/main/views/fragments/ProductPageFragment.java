package com.unza.wipro.main.views.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.paditech.core.BaseFragment;
import com.paditech.core.common.BaseRecycleViewAdapter;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.ProductListAdapter;
import com.unza.wipro.main.views.customs.StaggeredSpacesItemDecoration;

import butterknife.BindView;

public class ProductPageFragment extends BaseFragment {
    @BindView(R.id.rcvProduct)
    RecyclerView mRecyclerView;

    ProductListAdapter mAdapter;

    public static ProductPageFragment newInstance() {

        Bundle args = new Bundle();

        ProductPageFragment fragment = new ProductPageFragment();
        fragment.setArguments(args);
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
            mAdapter = new ProductListAdapter();
        }
        mRecyclerView.addItemDecoration(spacesItemDecoration);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleViewAdapter.BaseViewHolder holder, View view, int position) {
                switchFragment(ProductDetailFragment.newInstance(), true);
//                switchFragment(NewsDetailFragment.newInstance(), true);
            }
        });
    }

    @Override
    protected boolean isKeepFragment() {
        return true;
    }
}
