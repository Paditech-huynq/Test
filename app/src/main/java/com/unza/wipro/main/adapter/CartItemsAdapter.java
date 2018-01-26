package com.unza.wipro.main.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.helper.ImageHelper;
import com.paditech.core.helper.StringUtil;
import com.paditech.core.helper.ViewHelper;
import com.unza.wipro.AppConstans;
import com.unza.wipro.AppState;
import com.unza.wipro.R;
import com.unza.wipro.main.models.Order;
import com.unza.wipro.main.models.Product;
import com.unza.wipro.main.views.customs.AmountView;
import com.unza.wipro.main.views.fragments.OrderDetailFragment;
import com.unza.wipro.transaction.cart.Cart;
import com.unza.wipro.transaction.cart.CartInfo;
import com.unza.wipro.transaction.user.Promoter;
import com.unza.wipro.transaction.user.User;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;


public class CartItemsAdapter extends BaseRecycleViewAdapter implements AppConstans {
    private final static int TYPE_INFO = 0;
    private final static int TYPE_ITEM = 1;

    private Order mOrder = Order.newInstance();

    private User user;
    private CartInfo cartInfo;
    private OrderDetailFragment.ViewMode viewMode;

    public void updateOrder(Order order) {
        this.mOrder = order;
        user = order.getCustomer();
        cartInfo = order.getCart();
        notifyDataSetChanged();
    }

    public void setUser(User user) {
        this.user = user;
        notifyDataSetChanged();
    }

    public CartItemsAdapter(OrderDetailFragment.ViewMode viewMode) {
        this.viewMode = viewMode;
        if (viewMode == OrderDetailFragment.ViewMode.MODE_CREATE) {
            cartInfo = AppState.getInstance().getCurrentCart();
        }
    }

    @Override
    public Product getItem(int position) {
        return cartInfo.getItem(position);
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
        return cartInfo.getItemCount() + 1;
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
            amountView.setVisibility(viewMode == OrderDetailFragment.ViewMode.MODE_CREATE ? View.VISIBLE : View.GONE);
            tvCount.setVisibility(viewMode == OrderDetailFragment.ViewMode.MODE_SEE ? View.VISIBLE : View.GONE);
        }

        @Override
        protected void onBindingData(int position) {
            final Product item = getItem(position - 1);
            if (item == null) {
                return;
            }
            final Context context = itemView.getContext();

            ViewHelper.setText(tvName, item.getName(), null);
            if (item.getProductThumbnail() != null && !StringUtil.isEmpty(item.getProductThumbnail().getLink())) {
                ImageHelper.loadThumbImage(itemView.getContext(), item.getProductThumbnail().getLink(), imvProduct);
            }
            ViewHelper.setText(tvPrice,
                    context.getString(R.string.cart_item_price, StringUtil.formatMoney(item.getPrice())),
                    null);
            ViewHelper.setText(tvTotalPrice,
                    context.getString(R.string.cart_item_total,
                            StringUtil.formatMoney(cartInfo.getTotalPrice(getItem(index - 1).getId()))),
                    null);
            ViewHelper.setText(tvCount, String.valueOf(item.getQuantity()), null);
            amountView.setValue(item.getQuantity());
            amountView.setOnValueChangeListener(new AmountView.OnValueChangeListener() {
                @Override
                public void onValueChange(boolean isReduce, int value) {
                    app.editCart().update(item.getId(), value);
                    ViewHelper.setText(tvTotalPrice,
                            context.getString(R.string.cart_item_total,
                                    StringUtil.formatMoney(cartInfo.getTotalPrice(getItem(index - 1).getId()))),
                            null);
                    notifyItemChanged(0);
                }
            });
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
        @BindView(R.id.tvShop)
        TextView tvShop;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.btnChange)
        TextView btnChange;

        CartInfoHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindingData(int position) {
            Context context = itemView.getContext();
            if (viewMode == OrderDetailFragment.ViewMode.MODE_SEE) {
                onBindingDataForSeeMode(context);
            } else {  // create
                onBindingDataForCreateMode(context);
            }

            updatePrice();
            if (user == null) {
                if (!AppState.getInstance().isLogin()) {
                    tvName.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    tvName.setText("Chưa đăng nhập");
                    tvAddress.setText("Chưa đăng nhập");
                } else {
                    tvName.setTextColor(context.getResources().getColor(R.color.dark_light));
                }
                return;
            } else {
                tvName.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            }
            if (!StringUtil.isEmpty(user.getAvatar()))
                ImageHelper.loadThumbCircleImage(context, user.getAvatar(), imvAvatar);
            ViewHelper.setText(tvName, user.getName(), null);
            ViewHelper.setText(tvName, user.getName(), null);
            tvAddress.setText(Html.fromHtml(context.getString(R.string.cart_address_customer, user.getAddress())));
            if (viewMode == OrderDetailFragment.ViewMode.MODE_CREATE) {
                app.addCartChangeListener(this);
            }
        }

        private void onBindingDataForSeeMode(Context context) {
            String dateString = new SimpleDateFormat("dd/MM/yyyy hh:mm").format(new Date(mOrder.getCreatedAt()));
            tvDate.setText(Html.fromHtml(context.getString(R.string.cart_date_sell, dateString)));
            btnChange.setVisibility(View.GONE);
            if (!AppState.getInstance().isLogin() || !(AppState.getInstance().getCurrentUser() instanceof Promoter)) {
                tvShop.setVisibility(View.GONE);
            } else {
                tvShop.setVisibility(View.VISIBLE);
                tvShop.setText(Html.fromHtml(context.getString(R.string.cart_person_sell, mOrder.getCreator())));
            }
        }

        private void onBindingDataForCreateMode(Context context) {
            String dateString = new SimpleDateFormat("dd/MM/yyyy hh:mm").format(new Date());
            tvDate.setText(Html.fromHtml(context.getString(R.string.cart_date_sell, dateString)));
            if (!AppState.getInstance().isLogin() || !(AppState.getInstance().getCurrentUser() instanceof Promoter)) {
                tvShop.setVisibility(View.GONE);
                btnChange.setVisibility(View.GONE);
            } else {
                tvShop.setVisibility(View.VISIBLE);
                btnChange.setVisibility(View.VISIBLE);
                if (AppState.getInstance().getCurrentUser().getName() != null) {
                    tvShop.setText(Html.fromHtml(context.getString(R.string.cart_person_sell,
                            AppState.getInstance().getCurrentUser().getName())));
                }
            }
        }

        @OnClick(R.id.btnChange)
        void onAvatarClick() {
            onViewClick(R.id.btnChange);
        }

        void updatePrice() {
            ViewHelper.setText(tvPrice,
                    itemView.getContext().getString(R.string.currency_unit, StringUtil.formatMoney(cartInfo.getTotalPrice())),
                    null);
        }

        @Override
        public void onCartUpdate() {
            updatePrice();
        }
    }
}
