package com.unza.wipro.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paditech.core.common.BaseRecycleViewAdapter;
import com.unza.wipro.R;

public class LookupAdaper extends BaseRecycleViewAdapter {
    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LookupItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_lookup_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    class LookupItemHolder extends BaseViewHolder {

        public LookupItemHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindingData(int position) {

        }
    }
}
