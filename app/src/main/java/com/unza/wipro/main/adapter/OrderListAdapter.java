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
import com.unza.wipro.main.models.OrderClass;
import com.unza.wipro.main.models.Product;
import com.unza.wipro.utils.DateTimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class OrderListAdapter extends BaseRecycleViewAdapter implements AppConstans {

    private static final int TYPE_SECTION = 0;
    private static final int TYPE_ITEM = 1;
    private List mData = new ArrayList<>();

    public void insertData(List<Order> data) {
        if (data == null) {
            return;
        }
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void replaceData(List<Order> news) {
        mData.clear();
        insertData(news);
    }
    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder;
        switch (viewType){
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
        if(mData.get(position) instanceof Order){
            return TYPE_ITEM;
        }
        if(mData.get(position) instanceof String){
            return TYPE_SECTION;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class SectionViewHolder extends BaseViewHolder{

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

    class ChildViewHolder extends BaseViewHolder{

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
            GlideApp.with(context).load(R.mipmap.ic_launcher).into(img_propduct);
            String items = "";
            for (int i = 0; i < order.getProducts().size(); i++) {
                items += i == 0 ? order.getProducts().get(i).getName() : ", " + order.getProducts().get(i).getName();
            }
            tx_title.setText(items);
            tx_time.setText(String.valueOf("Thời gian: "+ DateTimeUtils.getStringTimeAll(new Date(order.getCreatedAt() * 1000))));
            tx_price.setText(context.getString(R.string.currency_unit, StringUtil.formatMoney(order.getMoney())));
            tx_number.setText(String.valueOf("Số lượng: "+ order.getQuantity()));
        }

    }
}
