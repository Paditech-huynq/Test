package com.unza.wipro.main.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.helper.ViewHelper;
import com.paditech.core.image.GlideApp;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
import com.unza.wipro.main.views.customs.PlaceHolderDrawableHelper;
import com.unza.wipro.transaction.user.Promoter;
import com.unza.wipro.utils.DateTimeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProfilePromoterListAdapter extends BaseRecycleViewAdapter implements AppConstans {
    private List<Promoter> promoterList = new ArrayList<>();

    @Override
    public Promoter getItem(int position) {
        return promoterList.get(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProfilePromoterListAdapter.ProfileHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_profile_promoter_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return promoterList.size();
    }

    public void addItemToList(List<Promoter> promoterList) {
        int lastCustomerCount = this.promoterList.size();
        this.promoterList.addAll(promoterList);
        notifyItemRangeInserted(lastCustomerCount, this.promoterList.size());
    }

    public void refreshData(List<Promoter> promoterList) {
        notifyItemRangeRemoved(0, this.promoterList.size());
        this.promoterList.clear();
        addItemToList(promoterList);
    }

    class ProfileHolder extends BaseRecycleViewAdapter.BaseViewHolder {
        @BindView(R.id.imvAvatar)
        ImageView imvAvatar;
        @BindView(R.id.tvPhone)
        TextView tvPhone;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvTimeSales)
        TextView tvTimeSales;
        @BindView(R.id.tvSales)
        TextView tvSales;

        private ProfileHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindingData(int position) {
            Context context = itemView.getContext();
            Promoter promoter = promoterList.get(position);
            GlideApp.with(context)
                    .load(promoter.getAvatar())
                    .placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(position))
                    .error(R.drawable.bg_place_holder)
                    .centerCrop()
                    .into(imvAvatar);
            ViewHelper.setText(tvName, promoter.getName(), null);
            ViewHelper.setText(tvPhone, String.format(context.getString(R.string.phone_with_format), promoter.getPhone()), null);
            ViewHelper.setText(tvTimeSales, String.format(context.getString(R.string.time_sale),
                    DateTimeUtils.getStringDayMonthYear(DateTimeUtils.getDateFromServerDayMonthYear(promoter.getFrom())),
                    DateTimeUtils.getStringDayMonthYear(DateTimeUtils.getDateFromServerDayMonthYear(promoter.getTo()))), null);
            ViewHelper.setText(tvSales, String.format(context.getString(R.string.sale), promoter.getSalesActual(), promoter.getSalesExpect()), null);
        }
    }
}
