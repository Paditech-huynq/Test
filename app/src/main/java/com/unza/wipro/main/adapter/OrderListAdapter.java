package com.unza.wipro.main.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.image.GlideApp;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
import com.unza.wipro.main.models.OrderClass;
import com.unza.wipro.utils.DateTimeUntils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class OrderListAdapter extends BaseRecycleViewAdapter implements AppConstans {

    private static final int TYPE_SECTION = 0;
    private static final int TYPE_ITEM = 1;
    private List<Object> mData = new ArrayList<>();

    @SuppressLint("SimpleDateFormat")
    public void addData(List<OrderClass> list){
        for (int i = 0; i < list.size(); i++) {
            Date dateCounting = new Date(list.get(i).getDate().getTime());
            if(mData.size() == 0){
                mData.add(DateTimeUntils.getStringMonthYear(dateCounting));
            } else {
                    Date dateBefore = new Date(list.get(i-1).getDate().getTime());
                    if (!DateTimeUntils.getStringMonthYear(dateBefore).equals(DateTimeUntils.getStringMonthYear(dateCounting))) {
                        mData.add(DateTimeUntils.getStringMonthYear(dateCounting));
                    }
            }
            mData.add(list.get(i));
        }
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
        if(mData.get(position) instanceof OrderClass){
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
            OrderClass order = (OrderClass) mData.get(position);
            Date date = ((OrderClass) mData.get(position)).getDate();
            GlideApp.with(itemView.getContext()).load(order.getImg()).into(img_propduct);
            tx_title.setText(order.getTitle());
            tx_time.setText("Thời gian: "+ DateTimeUntils.getStringMonthYear(date));
            tx_price.setText(order.getPrice()+"");
            tx_number.setText("Số lượng:"+ order.getNumberOrder() );
        }

    }
}
