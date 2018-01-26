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
            updatePrice();
            Context context = itemView.getContext();
            if (!AppState.getInstance().isLogin()) {
                onBindingDataForNoLogin(context);
            } else if (!(AppState.getInstance().getCurrentUser() instanceof Promoter)) {
                onBindingDataForNotPromoter(context);
            } else {
                onBindingDataForPromoter(context);
            }
        }

        private void onBindingDataForPromoter(Context context) {
            if (viewMode == OrderDetailFragment.ViewMode.MODE_CREATE) {
                btnChange.setVisibility(View.VISIBLE);
                if (user == null) {
                    btnChange.setText(context.getString(R.string.action_select_customer));
                    tvName.setTextColor(context.getResources().getColor(R.color.dark_light));
                    ViewHelper.setText(tvName, context.getString(R.string.cart_please_select_customer), null);
                    if (AppState.getInstance().getCurrentUser().getName() != null) {
                        tvShop.setText(Html.fromHtml(context.getString(R.string.cart_person_sell,
                                AppState.getInstance().getCurrentUser().getName())));
                    }
                } else {
                    btnChange.setText(context.getString(R.string.action_change));
                    tvName.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    ViewHelper.setText(tvName, user.getName(), null);
                    if (user.getAvatar() != null) {
                        ImageHelper.loadThumbCircleImage(context,
                                user.getAvatar(), imvAvatar);
                    }
                    if (user.getName() != null) {
                        tvAddress.setText(Html.fromHtml(context.getString(R.string.cart_address_customer,
                                user.getName())));
                    }
                    if (AppState.getInstance().getCurrentUser().getName() != null) {
                        tvShop.setText(Html.fromHtml(context.getString(R.string.cart_person_sell,
                                AppState.getInstance().getCurrentUser().getName())));
                    }
                }
                tvShop.setVisibility(View.VISIBLE);
                setCreateDate(context);
            } else {
                ImageHelper.loadThumbCircleImage(context,
                        user.getAvatar(), imvAvatar);
                btnChange.setVisibility(View.GONE);
                tvName.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                ViewHelper.setText(tvName, user.getName(), null);
                tvShop.setVisibility(View.VISIBLE);
                ViewHelper.setText(tvShop, mOrder.getCreator(), null);
                tvAddress.setText(Html.fromHtml(context.getString(R.string.cart_address_customer,
                        user.getName())));
                setCreateDate(context);
            }
        }

        private void onBindingDataForNotPromoter(Context context) {
            if (viewMode == OrderDetailFragment.ViewMode.MODE_CREATE) {
                ImageHelper.loadThumbCircleImage(context,
                        AppState.getInstance().getCurrentUser().getAvatar(), imvAvatar);
                btnChange.setVisibility(View.GONE);
                if (user == null) {
                    tvName.setTextColor(context.getResources().getColor(R.color.dark_light));
                    ViewHelper.setText(tvName, context.getString(R.string.cart_please_select_customer), null);
                } else {
                    tvName.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    ViewHelper.setText(tvName, user.getName(), null);
                }
                tvShop.setVisibility(View.GONE);
                tvAddress.setText(Html.fromHtml(context.getString(R.string.cart_address_customer,
                        AppState.getInstance().getCurrentUser().getAddress())));
                setCreateDate(context);
            } else {
                ImageHelper.loadThumbCircleImage(context,
                        user.getAvatar(), imvAvatar);
                btnChange.setVisibility(View.GONE);
                tvName.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                ViewHelper.setText(tvName, user.getName(), null);
                tvShop.setVisibility(View.GONE);
                tvAddress.setText(Html.fromHtml(context.getString(R.string.cart_address_customer,
                        AppState.getInstance().getCurrentUser().getName())));
                setCreateDate(context);
            }
        }

        private void onBindingDataForNoLogin(Context context) {
            btnChange.setVisibility(View.GONE);
            tvName.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            tvName.setText("Chưa đăng nhập");
            tvShop.setVisibility(View.GONE);
            tvAddress.setText(Html.fromHtml("<b>Địa chỉ: </b>"));
            setCreateDate(context);
        }

        private void setCreateDate(Context context) {
            if (viewMode == OrderDetailFragment.ViewMode.MODE_CREATE) {
                String dateString = new SimpleDateFormat("dd/MM/yyyy hh:mm").format(new Date());
                tvDate.setText(Html.fromHtml(context.getString(R.string.cart_date_sell, dateString)));
            } else {
                String dateString = new SimpleDateFormat("dd/MM/yyyy hh:mm").format(new Date(mOrder.getCreatedAt()));
                tvDate.setText(Html.fromHtml(context.getString(R.string.cart_date_sell, dateString)));
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
