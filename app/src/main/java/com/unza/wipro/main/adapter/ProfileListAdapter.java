package com.unza.wipro.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.image.GlideApp;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;

import butterknife.BindView;

public class ProfileListAdapter extends BaseRecycleViewAdapter implements AppConstans {
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
        return imagesDummy.length;
    }

    class ProfileHolder extends BaseRecycleViewAdapter.BaseViewHolder {
        @BindView(R.id.imvAvatar)
        ImageView imvAvatar;

        public ProfileHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindingData(int position) {
            GlideApp.with(imvAvatar.getContext()).load(imagesDummy[position]).centerCrop().thumbnail(0.2f).into(imvAvatar);
//            ImageHelper.loadThumbImage(imvAvatar.getContext(), imagesDummy[position], imvAvatar);
        }
    }
}
