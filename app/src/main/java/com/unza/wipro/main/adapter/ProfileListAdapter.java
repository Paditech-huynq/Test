package com.unza.wipro.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.image.GlideApp;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
import com.unza.wipro.main.models.Customer;
import com.unza.wipro.main.views.customs.PlaceHolderDrawableHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProfileListAdapter extends BaseRecycleViewAdapter implements AppConstans {
    private List<Customer> customerList = new ArrayList<>();

    @Override
    public Customer getItem(int position) {
        return customerList.get(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProfileHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_profile_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public void addItemToList(List<Customer> customerList) {
        int lastCustomerCount = this.customerList.size();
        this.customerList.addAll(customerList);
        notifyItemRangeInserted(lastCustomerCount, this.customerList.size());
    }

    public void refreshData(List<Customer> customerList) {
        this.customerList.clear();
        addItemToList(customerList);
    }

    class ProfileHolder extends BaseRecycleViewAdapter.BaseViewHolder {
        @BindView(R.id.imvAvatar)
        ImageView imvAvatar;
        @BindView(R.id.tvPhone)
        TextView tvPhone;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvEmail)
        TextView tvEmail;
        @BindView(R.id.tvAddress)
        TextView tvAddress;

        private ProfileHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindingData(int position) {
            Context context = itemView.getContext();
            Customer customer = customerList.get(position);
            GlideApp.with(context)
                    .load(customer.getAvatar())
                    .placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(position))
                    .error(R.drawable.bg_place_holder)
                    .centerCrop()
                    .into(imvAvatar);
            tvName.setText(customer.getName());
            tvPhone.setText(String.format(context.getString(R.string.phone_with_format), customer.getPhone()));
            tvEmail.setText(String.format(context.getString(R.string.email_with_format), customer.getEmail()));
            tvAddress.setText(String.format(context.getString(R.string.anddress_with_format), customer.getAddress()));
        }
    }
}
