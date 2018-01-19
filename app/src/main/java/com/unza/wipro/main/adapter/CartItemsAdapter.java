package com.unza.wipro.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.helper.ImageHelper;
import com.paditech.core.helper.StringUtil;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
import com.unza.wipro.main.models.Cart;
import com.unza.wipro.main.models.CartItem;
import com.unza.wipro.main.views.customs.AmountView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class CartItemsAdapter extends BaseRecycleViewAdapter implements AppConstans {
    private final static int TYPE_INFO = 0;
    private final static int TYPE_ITEM = 1;

    @Override
    public CartItem getItem(int position) {
        return Cart.getInstance().getCartItem(position);
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
        return Cart.getInstance().getTotalProduct() + 1;
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
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.tvTotalPrice)
        TextView tvTotalPrice;
        @BindView(R.id.av_amount)
        AmountView amountView;

        CartItemHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindingData(int position) {
            final Context context = itemView.getContext();
            final CartItem item = getItem(position - 1);
            if (item == null) return;
            if (item.getProduct() != null) {
                tvName.setText(item.getProduct().getName());
                if (item.getProduct().getProductThumbnail() != null && !StringUtil.isEmpty(item.getProduct().getProductThumbnail().getLink()))
                    ImageHelper.loadThumbImage(itemView.getContext(), item.getProduct().getProductThumbnail().getLink(), imvProduct);
                tvPrice.setText(context.getString(R.string.cart_item_price, StringUtil.formatMoney(item.getProduct().getPrice())));
                tvTotalPrice.setText(context.getString(R.string.cart_item_price, StringUtil.formatMoney(item.getTotalPrice())));
            }
            amountView.setValue(item.getAmount());
            amountView.setOnValueChangeListener(new AmountView.OnValueChangeListener() {
                @Override
                public void onValueChange(int value) {
                    item.setAmount(value);
                    tvTotalPrice.setText(context.getString(R.string.cart_item_price, StringUtil.formatMoney(item.getTotalPrice())));
                }
            });
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
