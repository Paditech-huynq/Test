package com.unza.wipro.main.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.image.GlideApp;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
import com.unza.wipro.main.models.OrderClass;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * Created by bangindong on 1/12/2018.
 */

public class OrderListAdapter extends BaseRecycleViewAdapter implements AppConstans {

    private static final int TYPE_SECTION = 0;
    private static final int TYPE_CHILD = 1;
    private int lastVisibleItem, totalItemCount;
    private int visibleThreshold = 5;
    private boolean isLoading = false;
    private BaseRecycleViewAdapter.LoadMoreListener onLoadMoreListener;
    private Date date;
    List<Object> list = new ArrayList<>();

    public OrderListAdapter(RecyclerView recyclerView) {
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold) ){
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    changeLoading();
                }
            }
        });
    }

    private void changeLoading(){
        isLoading = !isLoading;
    }

    public void setList(List<OrderClass> list) {
        this.list.addAll(list);
        Log.e( "setList: ",123+"" );
        check_Day(list);
        changeLoading();
    }

    private void check_Day(List<OrderClass> list){

        for (int i = 0; i < list.size(); i++) {
            if(date == null){
                insertSection(0, checkMonth0(list.get(i).getDate()));
            } else {
                if(list.get(i).getDate().getYear()!= date.getYear() ){
                    insertSection(this.list.size()- (list.size()-(i)), checkMonth0(list.get(i).getDate()));
                } else if(list.get(i).getDate().getYear() == date.getYear() ){
                    if(list.get(i).getDate().getMonth()!= date.getMonth()){
                        insertSection(this.list.size()- (list.size()-(i)), checkMonth0(list.get(i).getDate()));
                    }
                }
            }
        }
        Log.e("check_Day: ", this.list.size()+"" );
    }

    private void insertSection(int position, String a){
        list.add(position, a);
    }

    private String checkMonth0(Date date){
        this.date = new Date(date.getYear(),date.getMonth(),date.getDate(),date.getHours()
                ,date.getMinutes(),date.getSeconds());
        String str;
        if(this.date.getMonth() == 0){
            str = "12" + "/" + String.valueOf(this.date.getYear()-1);
        }
        else {
            str = String.valueOf(this.date.getMonth()) + "/" + String.valueOf(this.date.getYear());
        }
        return str;
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
            case TYPE_CHILD:
                holder = new ChildViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_order_child, parent, false));
                break;
            default:
                holder = new SectionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_order_child, parent, false));
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position) instanceof OrderClass){
            return TYPE_CHILD;
        }
        if(list.get(position) instanceof String){
            return TYPE_SECTION;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SectionViewHolder extends BaseViewHolder{

        @BindView(R.id.section_date)
        TextView textDateSection;

        SectionViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindingData(int position) {
            textDateSection.setText((String) list.get(position));
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
            OrderClass order = (OrderClass) list.get(position);
            Date date = ((OrderClass) list.get(position)).getDate();
            String strDate;
            if(date.getMonth() == 0){
                strDate = String.valueOf(date.getYear()-1) +"-"+"12"+"-"+String.valueOf(date.getDate()) +" "+String.valueOf(date.getHours())
                        +":"+String.valueOf(date.getMinutes());
            }
            else {
                strDate = String.valueOf(date.getYear()) +"-"+String.valueOf(date.getMonth())+"-"+String.valueOf(date.getDate()) +" "+String.valueOf(date.getHours())
                        +":"+String.valueOf(date.getMinutes());
            }
            System.out.println("Converted String: " + strDate);
            GlideApp.with(itemView.getContext()).load(order.getImg()).into(img_propduct);
            tx_title.setText(order.getTitle());
            tx_time.setText("Thời gian: "+strDate);
            tx_price.setText(order.getPrice()+"");
            tx_number.setText("Số lượng:"+ order.getNumberOrder() );
        }

        @Override
        public void onClick(View view) {
            Log.e("onClick: ",getLayoutPosition() +"" );
        }
    }
}
