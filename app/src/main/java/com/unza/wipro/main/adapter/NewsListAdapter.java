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
import com.unza.wipro.main.models.News;
import com.unza.wipro.main.models.Thumbnail;
import com.unza.wipro.main.views.customs.DynamicHeightImageView;
import com.unza.wipro.main.views.customs.PlaceHolderDrawableHelper;
import com.unza.wipro.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NewsListAdapter extends BaseRecycleViewAdapter implements AppConstans {
    private List<News> newsList = new ArrayList();
    @Override
    public News getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_news_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return newsList == null ? 0 : newsList.size();
    }

    public void insertData(List<News> data) {
        if (data == null) {
            return;
        }
        int lastCount = newsList.size();
        newsList.addAll(data);
        notifyItemRangeInserted(lastCount, newsList.size());
    }

    public void replaceData(List<News> news) {
        newsList.clear();
        newsList.addAll(news);
        notifyDataSetChanged();
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
            final News newsItem = getItem(position);
            if (newsItem != null) {
                ViewHelper.setText(tvDescription, newsItem.getSummary(), null);
                tvDate.setText(Utils.getTimeCreated(itemView.getContext(), newsItem.getCreatedAt()));
                updateImageSize(null);
                String url = "";
                if (newsItem.getThumbnail().getLink() != null) {
                    url = newsItem.getThumbnail().getLink();
                }
                GlideApp.with(itemView.getContext()).load(url)
                        .diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(position))
                        .error(R.drawable.bg_place_holder)
                        .into(imvNews);
            }
        }

        private void updateImageSize(Thumbnail thumbnail) {
            float thumbnaiRatio = 4f / 3;
            ViewGroup.LayoutParams rlp = imvNews.getLayoutParams();
            try {
                if (thumbnail != null && thumbnail.getHeight() != null && thumbnail.getWidth() != null) {
                    float thumbnailHeight = Float.parseFloat(thumbnail.getHeight());
                    float thumbnailWidth = Float.parseFloat(thumbnail.getWidth());
                    thumbnaiRatio = thumbnailHeight / thumbnailWidth;
                }
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
            rlp.height = (int) (rlp.width * thumbnaiRatio);
            imvNews.setLayoutParams(rlp);
            imvNews.setRatio(thumbnaiRatio);
        }
    }

    public void setNewsList(List<News> list) {
        newsList = list;
        notifyDataSetChanged();
    }

    public void addNewsList(List<News> list) {
        newsList.addAll(list);
        notifyDataSetChanged();
    }

    public List<News> getNewsList() {
        return newsList;
    }
}
