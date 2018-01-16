package com.unza.wipro.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.helper.ViewHelper;
import com.paditech.core.image.GlideApp;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
import com.unza.wipro.main.views.customs.DynamicHeightImageView;
import com.unza.wipro.main.views.customs.PlaceHolderDrawableHelper;

import butterknife.BindView;

public class NewsListAdapter extends BaseRecycleViewAdapter implements AppConstans {
    @Override
    public String getItem(int position) {
        return imagesDummy[position];
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_news_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return imagesDummy.length;
    }

    class NewsHolder extends BaseViewHolder {
        int index;

        @BindView(R.id.imv_news)
        DynamicHeightImageView imvNews;

        @BindView(R.id.tv_news_desc)
        TextView tvDescription;

        @BindView(R.id.tv_news_date)
        TextView tvDate;

        public NewsHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindingData(final int position) {
            final String url = getItem(position);
            ViewHelper.setText(tvDescription, position + " - " + url, null);
            tvDate.setText("4 giờ trước");
            updateImageSize(position);

            GlideApp.with(itemView.getContext()).load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(position))
                    .into(imvNews);
        }

        private void updateImageSize(int pos) {
            ViewGroup.LayoutParams rlp = imvNews.getLayoutParams();
            rlp.height = (int) (rlp.width * ratios[pos]);
            imvNews.setLayoutParams(rlp);
            imvNews.setRatio(ratios[pos]);
        }
    }
}
