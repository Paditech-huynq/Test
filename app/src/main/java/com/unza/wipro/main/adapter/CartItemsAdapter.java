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
import com.unza.wipro.main.models.Order;
import com.unza.wipro.main.models.Product;
import com.unza.wipro.main.views.customs.AmountView;
import com.unza.wipro.transaction.cart.CartInfo;

import butterknife.BindView;
import butterknife.OnClick;


public class CartItemsAdapter extends BaseRecycleViewAdapter implements AppConstans {
    private final static int TYPE_INFO = 0;
    private final static int TYPE_ITEM = 1;
    private Order mOrder;

    public void updateOrder(Order mOrder) {
        this.mOrder = mOrder;
        notifyDataSetChanged();
    }

    public CartItemsAdapter(Order order) {
        this.mOrder = order;
    }

    @Override
    public Product getItem(int position) {
        if (mOrder != null) {
            return mOrder.getCart().getItem(position);
        }
        return app.getCurrentCart().getItem(position);
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
        if (mOrder != null) {
            return mOrder.getCart().getItemCount() + 1;
        }
        return app.getCurrentCart().getItemCount() + 1;
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
        @BindView(R.id.tvCount)
        TextView tvCount;

        CartItemHolder(View itemView) {
            super(itemView);
            setupViewMode();
        }

        private void setupViewMode() {
            boolean isOrder = mOrder != null;
            amountView.setVisibility(!isOrder ? View.VISIBLE : View.GONE);
            tvCount.setVisibility(isOrder ? View.VISIBLE : View.GONE);
        }

        @Override
        protected void onBindingData(int position) {
            final Context context = itemView.getContext();
            final Product item = getItem(position - 1);
            if (item == null) return;
            tvName.setText(item.getName());
            if (item.getProductThumbnail() != null && !StringUtil.isEmpty(item.getProductThumbnail().getLink())) {
                ImageHelper.loadThumbImage(itemView.getContext(), item.getProductThumbnail().getLink(), imvProduct);
            }
            tvPrice.setText(context.getString(R.string.cart_item_price, StringUtil.formatMoney(item.getPrice())));

            amountView.setValue(item.getQuantity());
            tvCount.setText(String.valueOf(item.getQuantity()));
            amountView.setOnValueChangeListener(new AmountView.OnValueChangeListener() {
                @Override
                public void onValueChange(boolean isReduce, int value) {
                    app.editCart().update(item.getId(), value);
                    updatePrice();
                }
            });

            updatePrice();
        }

        void updatePrice() {
            CartInfo cartInfo = app.getCurrentCart();
            if (mOrder != null) {
                cartInfo = mOrder.getCart();
            }
            String currentTotalPrice = StringUtil.formatMoney(cartInfo.getTotalPrice(getItem(index - 1).getId()));
            tvTotalPrice.setText(currentTotalPrice);
        }
    }

    class CartInfoHolder extends BaseViewHolder {
        @BindView(R.id.imvAvatar)
        ImageView imvAvatar;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvShop)
        TextView tvShop;
        @BindView(R.id.tvAddress)
        TextView tvAddress;

        CartInfoHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindingData(int position) {
            String shop = mOrder != null ? mOrder.getCreator() : "";
            tvShop.setText(shop);
//            if (mOrder == null && mOrder.getCustomer() != null) return;
//            if (!StringUtil.isEmpty(mOrder.getCustomer().getAvatar()))
//                ImageHelper.loadThumbCircleImage(itemView.getContext(), mOrder.getCustomer().getAvatar(), imvAvatar);
//            tvName.setText(mOrder.getCustomer().getName());
//            tvDate.setText(StringUtil.formatDate(new Date()));
//            tvAddress.setText(mOrder.getCustomer().getAddress());
            updatePrice();
        }

        @OnClick(R.id.btnChange)
        void onAvatarClick() {
            onViewClick(R.id.btnChange);
        }

        void updatePrice() {
            double totalPrice;
            if (mOrder == null) {
                totalPrice = app.getCurrentCart().getTotalPrice();
            } else {
                totalPrice = mOrder.getMoney();
            }
            String currentTotalPrice = StringUtil.formatMoney(totalPrice);
            tvPrice.setText(itemView.getContext().getString(R.string.currency_unit, currentTotalPrice));
        }
    }
}
