package com.unza.wipro.main.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.paditech.core.BaseApplication;
import com.paditech.core.helper.StringUtil;
import com.paditech.core.image.GlideApp;
import com.paditech.core.image.GlideRequest;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
import com.unza.wipro.main.models.ProductThumbnail;
import com.unza.wipro.main.views.customs.PlaceHolderDrawableHelper;

import java.util.ArrayList;
import java.util.List;

public class ProductImageAdapter extends PagerAdapter implements AppConstans {
    View.OnTouchListener mListener;

    public ProductImageAdapter(Context context, View.OnTouchListener touchListener) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mListener = touchListener;
    }

    public interface Callback {
        void onItemClick(int position, ProductThumbnail thumbnail, View v);
    }

    public void setmCallback(Callback mCallback) {
        this.mCallback = mCallback;
    }

    private Callback mCallback;
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
    public Object instantiateItem(ViewGroup container, final int position) {
        final ProductThumbnail thumbnail = getThumbnail(position);
        final View itemView = mLayoutInflater.inflate(R.layout.view_product_image, container, false);
        container.addView(itemView);
        if (thumbnail != null && !StringUtil.isEmpty(thumbnail.getLink())) {
            GlideRequest<Drawable> request = GlideApp
                    .with(BaseApplication.getAppContext())
                    .load(thumbnail.getLink()).priority(Priority.IMMEDIATE)
                    .placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(position))
                    .error(R.drawable.bg_place_holder)
                    .thumbnail(0.5f)
                    .override(itemView.getWidth() / 2, itemView.getHeight() / 2);
            if (mListener == null) {
                request.centerCrop();
            } else {
                request.fitCenter();
            }
            request.into((ImageView) itemView.findViewById(R.id.imvProduct));
        } else {
            ((ImageView) itemView.findViewById(R.id.imvProduct)).setImageResource(R.drawable.bg_place_holder);
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallback != null) {
                    mCallback.onItemClick(position, thumbnail, v);
                }
            }
        });
        if (mListener != null) {
            itemView.setOnTouchListener(mListener);
        }
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
