package com.unza.wipro.main.adapter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.helper.StringUtil;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
import com.unza.wipro.main.models.Order;
import com.unza.wipro.main.models.Product;
import com.unza.wipro.main.views.customs.AmountView;
import com.unza.wipro.transaction.cart.Cart;
import com.unza.wipro.transaction.cart.CartInfo;
import com.unza.wipro.transaction.user.Customer;
import com.unza.wipro.transaction.user.Promoter;
import com.unza.wipro.utils.ImageHelper;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;


public class CartItemsAdapter extends BaseRecycleViewAdapter implements AppConstans {
    private final static int TYPE_INFO = 0;
    private final static int TYPE_ITEM = 1;
    private Order mOrder;
    private Customer currentCustomer;

    public void updateOrder(Order mOrder) {
        this.mOrder = mOrder;
        currentCustomer = mOrder.getCustomer();
        notifyDataSetChanged();
    }

    public CartItemsAdapter(Order order) {
        if (order == null) {
            if (app.getCurrentUser() instanceof Customer) {
                currentCustomer = (Customer) app.getCurrentUser();
            }
            return;
        }

        this.mOrder = order;
        currentCustomer = order.getCustomer();
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

    public void setCustomer(Customer customer) {
        Log.e("updat customer", customer.getCustomerId());
        this.currentCustomer = customer;
        notifyItemChanged(0);
    }

    public Customer getCustomer() {
        return currentCustomer;
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
        @BindView(R.id.avAmount)
        AmountView amountView;
        @BindView(R.id.tvCount)
        TextView tvCount;
        @BindView(R.id.swipe)
        SwipeLayout swipeLayout;

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
        protected void onBindingData(final int position) {

            swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
            swipeLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

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
                    if (value == 0) {
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, getItemCount());
                    } else {
                        updatePrice();
                    }
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
            tvTotalPrice.setText(itemView.getContext().getString(R.string.cart_singale_item_total_price, currentTotalPrice));
        }
    }

    class CartInfoHolder extends BaseViewHolder implements Cart.CartChangeListener {
        @BindView(R.id.imvAvatar)
        ImageView imvAvatar;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvPromoterName)
        TextView tvPromoterName;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.btnChangeCustomer)
        TextView btnChangeCustomer;

        boolean isOrder = mOrder != null;

        CartInfoHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindingData(int position) {
            fillCustomerInfo(currentCustomer);
            if (!isOrder) {
                setUpViewForCart();
            }
            updatePrice();
        }

        private void fillPromoterInfo(Promoter promoter) {
            tvDate.setVisibility(View.VISIBLE);
            tvPromoterName.setVisibility(View.VISIBLE);
            Date date = mOrder != null ? new Date(mOrder.getCreatedAt()) : new Date();
            tvDate.setText(Html.fromHtml(itemView.getContext().getResources().getString(R.string.cart_date_sell, StringUtil.formatDate(date))));
            tvPromoterName.setText(Html.fromHtml(itemView.getContext().getResources().getString(R.string.cart_person_sell, promoter.getName())));
            tvAddress.setVisibility(promoter.getAddress() == null || promoter.getAddress().trim().isEmpty() ? View.GONE : View.VISIBLE);
            tvAddress.setText(Html.fromHtml(itemView.getContext().getString(R.string.att_address_with_input, promoter.getAddress())));
        }

        /**
         * if not the order, show Cart
         */
        private void setUpViewForCart() {
            btnChangeCustomer.setVisibility(isOrder || !app.isLogin() || app.getCurrentUser() instanceof Customer ? View.GONE : View.VISIBLE);
            tvName.setVisibility(!app.isLogin() ? View.GONE : View.VISIBLE);
            if (app.getCurrentUser() != null) {
                if (app.getCurrentUser() instanceof Promoter) {
                    fillPromoterInfo((Promoter) app.getCurrentUser());

                }
                if (app.getCurrentUser().getAddress() != null && !app.getCurrentUser().getAddress().trim().isEmpty()) {
                    tvAddress.setText(Html.fromHtml(itemView.getContext().getString(R.string.att_address_with_input, app.getCurrentUser().getAddress())));
                    tvAddress.setVisibility(View.VISIBLE);
                } else {
                    tvAddress.setVisibility(View.GONE);
                }
            } else {
                tvAddress.setVisibility(View.GONE);
            }
            app.addCartChangeListener(this);
        }

        private void fillCustomerInfo(Customer customer) {
            if (customer != null) {
                ImageHelper.loadAvatar(itemView.getContext(), customer.getAvatar() + "", imvAvatar);
                tvName.setTextColor(itemView.getContext().getResources().getColor(R.color.colorPrimary));
                tvName.setText(customer.getName());
                btnChangeCustomer.setText(itemView.getContext().getString(R.string.action_change));
                if (customer.getAddress() != null && !customer.getAddress().trim().isEmpty()) {
                    tvAddress.setText(Html.fromHtml(itemView.getContext().getString(R.string.att_address_with_input, customer.getAddress())));
                }
            }
        }

        @OnClick(R.id.btnChangeCustomer)
        void onAvatarClick() {
            onViewClick(R.id.btnChangeCustomer);
        }

        void updatePrice() {
            double totalPrice;
            if (isOrder) {
                totalPrice = mOrder.getMoney();
            } else {
                totalPrice = app.getCurrentCart().getTotalPrice();
            }
            String currentTotalPrice = StringUtil.formatMoney(totalPrice);
            tvPrice.setText(itemView.getContext().getString(R.string.currency_unit, currentTotalPrice));
        }

        @Override
        public void onCartUpdate() {
            updatePrice();
        }
    }
}
