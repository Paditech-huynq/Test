package com.unza.wipro.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.helper.StringUtil;
import com.paditech.core.image.GlideApp;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
import com.unza.wipro.main.models.Order;
import com.unza.wipro.main.views.customs.PlaceHolderDrawableHelper;
import com.unza.wipro.utils.DateTimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class OrderListAdapter extends BaseRecycleViewAdapter implements AppConstans {

    private static final int TYPE_SECTION = 0;
    private static final int TYPE_ITEM = 1;
    private List mData = new ArrayList<>();
    private Context context;

    public OrderListAdapter(Context context) {
        this.context = context;
    }

    public void insertData(List<Order> data) {
        if (data == null) {
            return;
        }
        for (int i = 0; i < data.size(); i++) {
            Date dateCounting = DateTimeUtils.getDateFromServerDayMonthYear(String.valueOf(data.get(i).getCreatedAt()));
            if (mData.size() == 0) {
                mData.add(context.getResources().getString(R.string.section_month_year, DateTimeUtils.getStringMonthYear(dateCounting)));
            } else {
                Date dateBefore;
                if (i == 0) {
                    dateBefore = DateTimeUtils.getDateFromServerDayMonthYear(String.valueOf(((Order) mData.get(mData.size() - 1)).getCreatedAt()));
                } else {
                    dateBefore = DateTimeUtils.getDateFromServerDayMonthYear(String.valueOf(data.get(i - 1).getCreatedAt()));
                }
                if (!DateTimeUtils.getStringMonthYear(dateBefore).equals(DateTimeUtils.getStringMonthYear(dateCounting))) {
                    mData.add(context.getResources().getString(R.string.section_month_year, DateTimeUtils.getStringMonthYear(dateCounting)));
                }
            }
            mData.add(data.get(i));
        }
        notifyDataSetChanged();
    }

    public void replaceData(List<Order> news) {
        mData.clear();
        insertData(news);
    }

    @Override
    public Order getItem(int position) {
        Object currentItem = mData.get(position);
        if (currentItem instanceof Order) {
            return (Order) currentItem;
        }
        return null;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder;
        switch (viewType) {
            case TYPE_SECTION:
                holder = new SectionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_order_section, parent, false));
                break;
            case TYPE_ITEM:
                holder = new ChildViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_order_child, parent, false));
                break;
            default:
                holder = new SectionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_order_child, parent, false));
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position) instanceof Order) {
            return TYPE_ITEM;
        }
        if (mData.get(position) instanceof String) {
            return TYPE_SECTION;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class SectionViewHolder extends BaseViewHolder {

        @BindView(R.id.section_date)
        TextView textDateSection;

        SectionViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindingData(int position) {
            textDateSection.setText((String) mData.get(position));
        }

    }

    class ChildViewHolder extends BaseViewHolder {

        @BindView(R.id.img_product_order)
        ImageView img_propduct;
        @BindView(R.id.tx_title)
        TextView tx_title;
        @BindView(R.id.tx_time)
        TextView tx_time;
        @BindView(R.id.tx_number)
        TextView tx_number;
        @BindView(R.id.tx_price)
        TextView tx_price;

        ChildViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindingData(int position) {
            Context context = itemView.getContext();
            Order order = (Order) mData.get(position);
            if (order == null) return;
            if (!StringUtil.isEmpty(order.getAvatarOrder()))
                GlideApp.with(context)
                        .load(order.getAvatarOrder())
                        .placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(position))
                        .error(R.drawable.bg_place_holder)
                        .into(img_propduct);
            tx_title.setText(order.getName());
            tx_time.setText(context.getString(R.string.time_create_in_item,DateTimeUtils.getStringTimeAll(new Date(order.getCreatedAt() * 1000))));
            tx_price.setText(context.getString(R.string.currency_unit, StringUtil.formatMoney(order.getMoney())));
            tx_number.setText(context.getString(R.string.quality_product_in_item,String.format("%02d", order.getQuantity())));
        }

    }
}
