package com.unza.wipro.main.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.paditech.core.common.BaseRecycleViewAdapter;

public class LookupAdaper extends BaseRecycleViewAdapter {
    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class LookupItemHolder extends BaseRecycleViewAdapter.BaseViewHolder {

        public LookupItemHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindingData(int position) {

        }
    }
}
