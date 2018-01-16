package com.unza.wipro.main.views.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;

import com.paditech.core.BaseFragment;
import com.paditech.core.common.BaseRecycleViewAdapter;
import com.unza.wipro.R;
import com.unza.wipro.main.adapter.ProductListAdapter;
import com.unza.wipro.main.views.customs.StaggeredSpacesItemDecoration;

import java.util.Calendar;

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
            mAdapter = new ProductListAdapter(String.valueOf(Calendar.getInstance().getTimeInMillis()));
        }
        mRecyclerView.addItemDecoration(spacesItemDecoration);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleViewAdapter.BaseViewHolder holder, View view, int position) {
//                switchFragment(ProductDetailFragment.newInstance(), true);

                Transition changeTransform = null;
                ProductDetailFragment kittenDetails = ProductDetailFragment.newInstance();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    changeTransform = TransitionInflater.from(ProductPageFragment.this.getContext()).
                            inflateTransition(R.transition.change_image_transform);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    kittenDetails.setSharedElementEnterTransition(changeTransform);
                    kittenDetails.setEnterTransition(new Fade());
                    setExitTransition(new Fade());
                    kittenDetails.setSharedElementReturnTransition(changeTransform);
                }

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(com.paditech.core.R.anim.from_right, com.paditech.core.R.anim.to_left, com.paditech.core.R.anim.from_left, com.paditech.core.R.anim.to_right);

                ft.addSharedElement(((ProductListAdapter.ProductHolder) holder).imvProduct,
                        getString(R.string.transition_list_product_to_product_detail))
                        .replace(R.id.container, kittenDetails)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    protected boolean isKeepFragment() {
        return true;
    }
}

