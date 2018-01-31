package com.unza.wipro.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.helper.StringUtil;
import com.paditech.core.helper.ViewHelper;
import com.paditech.core.image.GlideApp;
import com.unza.wipro.R;
import com.unza.wipro.main.models.Product;
import com.unza.wipro.main.views.customs.PlaceHolderDrawableHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LookupAdapter extends BaseRecycleViewAdapter {
    public interface OnProductItemClickListenner {
        void onAddCartButtonClick(View view, int index);
    }
    private LookupAdapter.OnProductItemClickListenner mOnProductItemClickListenner;
    private List<Product> mProducts = new ArrayList();

    public void setOnProductItemClickListenner(LookupAdapter.OnProductItemClickListenner mOnProductItemClickListenner) {
        this.mOnProductItemClickListenner = mOnProductItemClickListenner;
    }

    @Override
    public Product getItem(int position) {
        return mProducts.get(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LookupItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_lookup_item, parent, false));
    }

    public void updateItemToList(List<Product> productList) {
        int lastProductCount = mProducts.size();
        mProducts.addAll(productList);
        notifyItemRangeInserted(lastProductCount, mProducts.size());
    }

    public void refreshProductList(List<Product> productList) {
        mProducts.clear();
        mProducts.addAll(productList);
        notifyDataSetChanged();
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
        protected void onBindingData(final int position) {
            Product product = mProducts.get(position);
            GlideApp.with(itemView.getContext())
                    .load(product.getProductThumbnail().getLink())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(position))
                    .error(R.drawable.bg_place_holder)
                    .into(imvProduct);
            ViewHelper.setText(tvName, product.getName(), null);
            ViewHelper.setText(tvPrice,
                    String.format(itemView.getContext().getString(R.string.lookup_price_format),
                            StringUtil.formatMoney(product.getPrice())),
                    null);
            itemView.findViewById(R.id.btnCart).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnProductItemClickListenner != null)
                        mOnProductItemClickListenner.onAddCartButtonClick(itemView.findViewById(R.id.imvProduct), position);
                }
            });
        }
    }
}
