package com.unza.wipro.main.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.paditech.core.BaseApplication;
import com.paditech.core.helper.StringUtil;
import com.paditech.core.image.GlideApp;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
import com.unza.wipro.main.models.ProductThumbnail;
import com.unza.wipro.main.views.customs.PlaceHolderDrawableHelper;

import java.util.ArrayList;
import java.util.List;

public class ProductImageAdapter extends PagerAdapter implements AppConstans {

    private List<ProductThumbnail> mData;
    private LayoutInflater mLayoutInflater;

    public ProductImageAdapter(Context context) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addData(ProductThumbnail thumbnail) {
        if (this.mData == null) this.mData = new ArrayList<>();
        this.mData.add(thumbnail);
        notifyDataSetChanged();
    }

    public void setData(List<ProductThumbnail> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    private ProductThumbnail getThumbnail(int position) {
        if (mData != null && position >= 0 && position < mData.size())
            return mData.get(position);
        return null;
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ProductThumbnail thumbnail = getThumbnail(position);
        View itemView = mLayoutInflater.inflate(R.layout.view_product_image, container, false);
        container.addView(itemView);
        if (thumbnail != null && !StringUtil.isEmpty(thumbnail.getLink())) {
            GlideApp
                    .with(BaseApplication.getAppContext())
                    .load(thumbnail.getLink()).priority(Priority.IMMEDIATE)
                    .placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(position))
                    .error(R.drawable.bg_place_holder)
                    .thumbnail(0.5f)
                    .override(itemView.getWidth() / 2, itemView.getHeight() / 2)
                    .centerCrop()
                    .into((ImageView) itemView.findViewById(R.id.imvProduct));
        } else {
            ((ImageView) itemView.findViewById(R.id.imvProduct)).setImageResource(R.drawable.bg_place_holder);
        }
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
