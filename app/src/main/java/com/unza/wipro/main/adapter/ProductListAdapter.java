package com.unza.wipro.main.adapter;

import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.helper.ViewHelper;
import com.paditech.core.image.GlideApp;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
import com.unza.wipro.main.models.Product;
import com.unza.wipro.main.models.ProductThumbnail;
import com.unza.wipro.main.views.customs.DynamicHeightImageView;
import com.unza.wipro.main.views.customs.PlaceHolderDrawableHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProductListAdapter extends BaseRecycleViewAdapter implements AppConstans {
    private OnProductItemClickListenner mOnProductItemClickListenner;
    private List<Product> productList = new ArrayList<>();

    public void setOnProductItemClickListenner(OnProductItemClickListenner mOnProductItemClickListenner) {
        this.mOnProductItemClickListenner = mOnProductItemClickListenner;
    }

    @Override
    public Product getItem(int position) {
        return productList.get(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_product_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductHolder extends BaseViewHolder {
        int index;

        @BindView(R.id.imvProduct)
        DynamicHeightImageView imvProduct;

        @BindView(R.id.tvDescription)
        TextView tvDescription;

        @BindView(R.id.tvPrice)
        TextView tvPrice;

        ProductHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindingData(final int position) {
            final Product product = getItem(position);
            final ProductThumbnail productThumbnail = product.getProductThumbnail();
            ViewHelper.setText(tvDescription, product.getName(), null);
            ViewHelper.setText(tvPrice, product.getPrice(), null);
            updateImageSize(productThumbnail);

            GlideApp.with(itemView.getContext()).load(productThumbnail.getLink())
                    .diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(position))
                    .into(imvProduct);

            itemView.findViewById(R.id.btn_add_cart).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnProductItemClickListenner != null)
                        mOnProductItemClickListenner.onAddCartButtonClick(itemView.findViewById(R.id.imvProduct), position);
                }
            });
        }

        private void updateImageSize(ProductThumbnail productThumbnail) {
            float width = Float.parseFloat(productThumbnail.getWidth());
            float height = Float.parseFloat(productThumbnail.getHeight());
            float ratio = width / height;
            ViewGroup.LayoutParams rlp = imvProduct.getLayoutParams();
            rlp.height = (int) (rlp.width * ratio);
            imvProduct.setLayoutParams(rlp);
            imvProduct.setRatio(ratio);
        }
    }

    public interface OnProductItemClickListenner {
        void onAddCartButtonClick(View view, int index);
    }

    public void addProductList(List<Product> productList) {
        int lastProductCount = productList.size();
        this.productList.addAll(productList);
        notifyItemRangeInserted(lastProductCount, productList.size());
    }
}
