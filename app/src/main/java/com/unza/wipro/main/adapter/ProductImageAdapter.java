package com.unza.wipro.main.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.paditech.core.BaseApplication;
import com.paditech.core.image.GlideApp;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;

import java.util.List;

public class ProductImageAdapter extends PagerAdapter implements AppConstans {

    private List mData;
    private LayoutInflater mLayoutInflater;

    public ProductImageAdapter(Context context) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.view_product_image, container, false);
        container.addView(itemView);
        GlideApp
                .with(BaseApplication.getAppContext())
                .load(imagesDummy[position]).priority(Priority.IMMEDIATE)
                .thumbnail(0.5f)
                .override(itemView.getWidth() / 2, itemView.getHeight() / 2)
                .centerCrop()
                .into((ImageView) itemView.findViewById(R.id.imvProduct));

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
