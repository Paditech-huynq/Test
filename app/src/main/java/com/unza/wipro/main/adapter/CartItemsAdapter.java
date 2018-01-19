package com.unza.wipro.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.helper.ImageHelper;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
import com.unza.wipro.main.views.customs.AmountView;

import butterknife.BindView;
import butterknife.OnClick;


public class CartItemsAdapter extends BaseRecycleViewAdapter implements AppConstans {
    private final static int TYPE_INFO = 0;
    private final static int TYPE_ITEM = 1;
    private boolean isCreateCart;

    public CartItemsAdapter(boolean isCreateCart) {
        this.isCreateCart = isCreateCart;
    }

    @Override
    public String getItem(int position) {
        return imagesDummy[position];
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_INFO) {
            return new CartInfoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cart_info, parent, false));
        }
        return new CartItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cart_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return imagesDummy.length + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_INFO;
        }
        return TYPE_ITEM;
    }

    class CartItemHolder extends BaseRecycleViewAdapter.BaseViewHolder {
        @BindView(R.id.imvProduct)
        ImageView imvProduct;
        @BindView(R.id.av_amount)
        AmountView avAmount;
        @BindView(R.id.tvCount)
        TextView tvCount;

        CartItemHolder(View itemView) {
            super(itemView);
            setupCreateCart();
        }

        private void setupCreateCart() {
            avAmount.setVisibility(isCreateCart ? View.VISIBLE : View.GONE);
            tvCount.setVisibility(isCreateCart ? View.GONE : View.VISIBLE);
        }

        @Override
        protected void onBindingData(int position) {
            String url = getItem(position - 1);
            ImageHelper.loadThumbImage(itemView.getContext(), url, imvProduct);
        }
    }

    class CartInfoHolder extends BaseViewHolder {
        @BindView(R.id.imvAvatar)
        ImageView imvAvatar;

        CartInfoHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindingData(int position) {
//            ImageHelper.loadThumbCircleImage(itemView.getContext(), imagesDummy[15], imvAvatar);
        }

        @OnClick(R.id.btnChange)
        void onAvatarClick() {
            onViewClick(R.id.btnChange);
        }
    }
}
