package com.unza.wipro.main.adapter;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paditech.core.common.BaseRecycleViewAdapter;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
import com.unza.wipro.main.models.Notice;

import java.util.List;

import butterknife.BindView;

public class NotificationAdapter extends BaseRecycleViewAdapter implements AppConstans {

    private List<Notice> mData;

    public void setData(List<Notice> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void updateData(Notice data) {
        if (mData == null) return;
        for (Notice notice: mData) {
            if (notice.getId() == data.getId()) {
                notice.setRead(data.isRead());
                break;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public Notice getItem(int position) {
        return mData.get(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotificationHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_notification_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
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
            Notice notice = getItem(position);
            if (notice == null) return;
            tvTitle.setText(notice.getTitle());
            tvContent.setText(notice.getContent());
            if (notice.isRead()) {
                tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dot_transparent, 0, 0, 0);
            } else {
                tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tvTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dot_blue, 0, 0, 0);
            }
        }
    }
}
