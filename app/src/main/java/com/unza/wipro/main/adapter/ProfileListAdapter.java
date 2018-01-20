package com.unza.wipro.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.image.GlideApp;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
import com.unza.wipro.main.models.Customer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProfileListAdapter extends BaseRecycleViewAdapter implements AppConstans {
    private List<Customer> customerList = new ArrayList<>();

    @Override
    public Object getItem(int position) {
        return null;
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
        customerList.addAll(customerList);
        notifyItemRangeInserted(lastCustomerCount, this.customerList.size());
    }

    public void refreshData(List<Customer> customerList) {
        customerList.clear();
        addItemToList(customerList);
    }

    class ProfileHolder extends BaseRecycleViewAdapter.BaseViewHolder {
        @BindView(R.id.imvAvatar)
        ImageView imvAvatar;

        public ProfileHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindingData(int position) {
            Customer customer = customerList.get(position);
            GlideApp.with(imvAvatar.getContext()).load(imagesDummy[position]).centerCrop().thumbnail(0.2f).into(imvAvatar);
//            ImageHelper.loadThumbImage(imvAvatar.getContext(), imagesDummy[position], imvAvatar);
        }
    }
}
