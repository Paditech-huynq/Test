package com.pshop.app.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.helper.StringUtil;
import com.paditech.core.helper.ViewHelper;
import com.paditech.core.image.GlideApp;
import com.pshop.app.AppConstans;
import com.pshop.app.R;
import com.pshop.app.main.models.Product;
import com.pshop.app.main.models.ProductThumbnail;
import com.pshop.app.main.views.customs.DynamicHeightImageView;
import com.pshop.app.main.views.customs.PlaceHolderDrawableHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProductListAdapter extends BaseRecycleViewAdapter implements AppConstans {
    private OnProductItemClickListenner mOnProductItemClickListenner;
    private List<Product> productList = new ArrayList<>();
    private static final float DEFAULT_RATIO = 4F / 3F;

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
            ViewHelper.setText(tvPrice,
                    String.format(itemView.getContext().getString(R.string.currency_unit), StringUtil.formatMoney(product.getPrice())),
                    null);
            updateImageSize(productThumbnail);

            GlideApp.with(itemView.getContext()).load(productThumbnail.getLink())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(position))
                    .error(R.drawable.bg_place_holder)
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
            float ratio = DEFAULT_RATIO;
            try {
                float width = Float.parseFloat(productThumbnail.getWidth());
                float height = Float.parseFloat(productThumbnail.getHeight());
                ratio = height / width;
            } catch (Exception e) {
                e.printStackTrace();
            }
            ViewGroup.LayoutParams rlp = imvProduct.getLayoutParams();
            rlp.height = (int) (rlp.width * ratio);
            imvProduct.setLayoutParams(rlp);
            imvProduct.setRatio(ratio);
        }
    }

    public interface OnProductItemClickListenner {
        void onAddCartButtonClick(View view, int index);
    }

    public void insertData(List<Product> productList) {
        if (productList == null) {
            return;
        }
        int lastProductCount = this.productList.size();
        this.productList.addAll(productList);
        notifyItemRangeInserted(lastProductCount, this.productList.size());
    }

    public void replaceData(List<Product> productList) {
        this.productList.clear();
        this.productList.addAll(productList);
        notifyDataSetChanged();
    }
}
