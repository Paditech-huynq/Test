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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * Created by bangindong on 1/12/2018.
 */

public class OrderListAdapter extends BaseRecycleViewAdapter implements AppConstans {

    private static final int TYPE_SECTION = 0;
    private static final int TYPE_ITEM = 1;
    private List<Object> listOrder = new ArrayList<>();


    public void setListOrder(List<OrderClass> listOrder) {
        checkDay(listOrder);
    }

    private void checkDay(List<OrderClass> list){
        for (int i = 0; i < list.size(); i++) {
            Date date_counting = new Date(list.get(i).getDate().getTime());
            Date date_before;
            if(listOrder.size() == 0){
                insertSection(0, date_counting);
            } else {
                if (i == 0) {
                    date_before = new Date(((OrderClass) listOrder.get(listOrder.size() - 1)).getDate().getTime());
                } else {
                    date_before = new Date(list.get(i - 1).getDate().getTime());
                }
                {
                    if (getYear(date_before) != getYear(date_counting)) {
                        insertSection(this.listOrder.size(), date_counting);
                    } else {
                        if (getMonth(date_before) != getMonth(date_counting)) {
                            insertSection(this.listOrder.size(), date_counting);
                        }
                    }
                }
            }
            listOrder.add(list.get(i));
        }
    }

    private int getYear(Date date){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String year = dateFormat.format(date);
        Log.e("getYear: ", Integer.parseInt(year) +"" );
        return Integer.parseInt(year)-1900;
    }

    private int getMonth(Date date){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
        String month = dateFormat.format(date);
        Log.e("getMonth: ", Integer.parseInt(month) +"" );
        if(Integer.parseInt(month)-1 == 0){
            return 12;
        }
        else {
            return Integer.parseInt(month) - 1;
        }
    }

    private void insertSection(int position, Date date_section){
        listOrder.add(position, String.valueOf(getMonth(date_section)+"/"+String.valueOf(getYear(date_section))));
    }

    @Override
    public Object getItem(int position) {
        return null;
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
        if(listOrder.get(position) instanceof OrderClass){
            return TYPE_ITEM;
        }
        if(listOrder.get(position) instanceof String){
            return TYPE_SECTION;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return listOrder.size();
    }

    class SectionViewHolder extends BaseViewHolder{

        @BindView(R.id.section_date)
        TextView textDateSection;

        SectionViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindingData(int position) {
            textDateSection.setText((String) listOrder.get(position));
        }

    }

    class ChildViewHolder extends BaseViewHolder implements View.OnClickListener{

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
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onBindingData(int position) {
            OrderClass order = (OrderClass) listOrder.get(position);
            Date date = ((OrderClass) listOrder.get(position)).getDate();
            String strDate;
            strDate = getYear(date) +"-"+getMonth(date)+"-"+String.valueOf(date.getDate()) +" "+String.valueOf(date.getHours())
                    +":"+String.valueOf(date.getMinutes());
            GlideApp.with(itemView.getContext()).load(order.getImg()).into(img_propduct);
            tx_title.setText(order.getTitle());
            tx_time.setText("Thời gian: "+strDate);
            tx_price.setText(order.getPrice()+"");
            tx_number.setText("Số lượng:"+ order.getNumberOrder() );
        }

        @Override
        public void onClick(View view) {
        }
    }
}
