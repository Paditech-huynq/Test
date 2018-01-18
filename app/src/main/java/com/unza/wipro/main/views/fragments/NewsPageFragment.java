package com.unza.wipro.main.views.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.mvp.MVPFragment;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.NewsListAdapter;
import com.unza.wipro.main.contracts.NewsPageContract;
import com.unza.wipro.main.models.News;
import com.unza.wipro.main.models.NewsCategory;
import com.unza.wipro.main.presenters.NewsPagePresenter;
import com.unza.wipro.main.views.customs.StaggeredSpacesItemDecoration;

import java.util.List;

import butterknife.BindView;

public class NewsPageFragment extends MVPFragment<NewsPagePresenter> implements BaseRecycleViewAdapter.LoadMoreListener, NewsPageContract.ViewImpl, AppConstans {
    @BindView(R.id.rcvProduct)
    RecyclerView mRecyclerView;
    private NewsListAdapter mAdapter;
    private NewsCategory mCategory;
    private int mPage = 1;

    public static NewsPageFragment newInstance(NewsCategory newsCategory) {
        Bundle args = new Bundle();
        NewsPageFragment fragment = new NewsPageFragment();
        fragment.mCategory = newsCategory;
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
        getPresenter().loadData("", mCategory.getId(), mPage, PAGE_SIZE);
    }

    private void setupRecycleView() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        StaggeredSpacesItemDecoration spacesItemDecoration = new StaggeredSpacesItemDecoration(getResources().getDimensionPixelOffset(R.dimen.padding_small));
        if (mAdapter == null) {
            mAdapter = new NewsListAdapter();
        }
        mRecyclerView.addItemDecoration(spacesItemDecoration);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleViewAdapter.BaseViewHolder holder, View view, int position) {
                switchFragment(NewsDetailFragment.newInstance(mAdapter.getItem(position)), true);
            }
        });
    }


    @Override
    protected boolean isKeepFragment() {
        return true;
    }

    @Override
    public void onLoadMore() {
        getPresenter().loadData("", mCategory.getId(), mPage, PAGE_SIZE);
    }


    @Override
    public void onGetData(List<News> data, boolean isLoadmode) {
        if (isLoadmode) {
            if (data.size() != 0) {
                mAdapter.addNewsList(data);
                mPage++;
            }
        } else {
            mAdapter.setNewsList(data);
            mPage++;
        }
    }
}
