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

public class LookupAdaper extends BaseRecycleViewAdapter {

    private List<Product> mProducts = new ArrayList();

    @Override
    public Object getItem(int position) {
        return mProducts.get(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LookupItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_lookup_item, parent, false));
    }

    public void setListProduct(List<Product> data) {
        mProducts = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    class LookupItemHolder extends BaseViewHolder {

        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.tvName)
        TextView nameView;
        @BindView(R.id.tvPrice)
        TextView priceView;

        public LookupItemHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindingData(int position) {
            Product product = mProducts.get(position);
            GlideApp.with(itemView.getContext()).load(product.getProductThumbnail().getLink())
                    .diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(position))
                    .into(imageView);
            nameView.setText(product.getName());
            priceView.setText(String.valueOf(product.getPrice()));
        }
    }
}
