package com.unza.wipro.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.image.GlideApp;
import com.unza.wipro.R;
import com.unza.wipro.main.models.Product;
import com.unza.wipro.main.views.customs.PlaceHolderDrawableHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LookupAdapter extends BaseRecycleViewAdapter {

    private List<Product> mProducts = new ArrayList();

    @Override
    public Object getItem(int position) {
        return mProducts.get(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LookupItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_lookup_item, parent, false));
    }

    public void updateItemToList(List<Product> productList) {
        int lastProductCount = mProducts.size();
        if (productList != null) {
            mProducts.addAll(productList);
        }
        notifyItemRangeInserted(lastProductCount, mProducts.size());
    }

    public void refreshProductList(List<Product> productList) {
        notifyItemRangeRemoved(0, mProducts.size());
        mProducts.clear();
        updateItemToList(productList);
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    class LookupItemHolder extends BaseViewHolder {
        @BindView(R.id.imvProduct)
        ImageView imvProduct;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvPrice)
        TextView tvPrice;

        private LookupItemHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindingData(int position) {
            Product product = mProducts.get(position);
            GlideApp.with(itemView.getContext())
                    .load(product.getProductThumbnail().getLink())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(position))
                    .error(R.drawable.bg_place_holder)
                    .into(imvProduct);
            tvName.setText(product.getName());
            tvPrice.setText(String.valueOf(product.getPrice()));
        }
    }
}
