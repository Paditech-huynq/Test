package com.unza.wipro.main.adapter;

import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.helper.ViewHelper;
import com.paditech.core.image.GlideApp;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
import com.unza.wipro.main.views.customs.DynamicHeightImageView;
import com.unza.wipro.main.views.customs.PlaceHolderDrawableHelper;

import butterknife.BindView;

public class ProductListAdapter extends BaseRecycleViewAdapter implements AppConstans {
    private String fragmentTitle;

    public ProductListAdapter(String fragmentTitle) {
        this.fragmentTitle = fragmentTitle;
    }

    private OnProductItemClickListenner mOnProductItemClickListenner;

    public void setOnProductItemClickListenner(OnProductItemClickListenner mOnProductItemClickListenner) {
        this.mOnProductItemClickListenner = mOnProductItemClickListenner;
    }

    @Override
    public String getItem(int position) {
        return imagesDummy[position];
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_product_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return imagesDummy.length;
    }

    public class ProductHolder extends BaseViewHolder {
        int index;

        @BindView(R.id.imvProduct)
        public DynamicHeightImageView imvProduct;

        @BindView(R.id.tvDescription)
        TextView tvDescription;

        public ProductHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindingData(final int position) {
            ViewCompat.setTransitionName(imvProduct,fragmentTitle + "_" + String.valueOf(position));

            final String url = getItem(position);
            ViewHelper.setText(tvDescription, position + " - " + url, null);
            updateImageSize(position);

            GlideApp.with(itemView.getContext()).load(url)
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

        private void updateImageSize(int pos) {
            ViewGroup.LayoutParams rlp = imvProduct.getLayoutParams();
            rlp.height = (int) (rlp.width * ratios[pos]);
            imvProduct.setLayoutParams(rlp);
            imvProduct.setRatio(ratios[pos]);
        }
    }

    public interface OnProductItemClickListenner {
        void onAddCartButtonClick(View view, int index);
    }
}
