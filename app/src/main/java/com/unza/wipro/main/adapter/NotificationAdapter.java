package com.unza.wipro.main.adapter;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paditech.core.common.BaseRecycleViewAdapter;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;

import java.util.List;

import butterknife.BindView;

public class NotificationAdapter extends BaseRecycleViewAdapter implements AppConstans {

    private List mData;

    public void setData(List data) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public String getItem(int position) {
        return imagesDummy[position];
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotificationHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_notification_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return imagesDummy.length;
    }

    public class NotificationHolder extends BaseViewHolder {
        int index;

        @BindView(R.id.tv_notification_title)
        TextView tvTitle;
        @BindView(R.id.tv_notification_content)
        TextView tvContent;

        NotificationHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindingData(final int position) {
            tvTitle.setText("Thông báo số #1");
            tvContent.setText("Thông báo có 1 số thay đổi blah blah blah");
        }

        public void updateView() {
            tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            tvTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dot_transparent, 0, 0, 0);
        }
    }
}
