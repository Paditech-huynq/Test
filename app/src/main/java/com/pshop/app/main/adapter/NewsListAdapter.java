package com.pshop.app.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.paditech.core.common.BaseRecycleViewAdapter;
import com.paditech.core.helper.ViewHelper;
import com.paditech.core.image.GlideApp;
import com.pshop.app.AppConstans;
import com.pshop.app.R;
import com.pshop.app.main.models.News;
import com.pshop.app.main.models.Thumbnail;
import com.pshop.app.main.views.customs.DynamicHeightImageView;
import com.pshop.app.main.views.customs.PlaceHolderDrawableHelper;
import com.pshop.app.utils.Utils;

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

    public void addNewsList(List<News> list) {
        newsList.addAll(list);
        notifyDataSetChanged();
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> list) {
        newsList = list;
        notifyDataSetChanged();
    }

    class NewsHolder extends BaseViewHolder {
        int index;

        @BindView(R.id.imv_news)
        DynamicHeightImageView imvNews;

        @BindView(R.id.tv_news_title)
        TextView tvTitle;

        @BindView(R.id.tv_news_date)
        TextView tvDate;

        public NewsHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindingData(final int position) {
            final News newsItem = getItem(position);
            if (newsItem != null) {
                ViewHelper.setText(tvTitle, newsItem.getTitle(), null);
                tvDate.setText(Utils.getTimeCreated(itemView.getContext(), newsItem.getCreatedAt()));
                updateImageSize(newsItem.getThumbnail());
                if (newsItem.getThumbnail().getLink() != null) {
                    GlideApp.with(itemView.getContext()).load(newsItem.getThumbnail().getLink())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(position))
                            .error(R.drawable.bg_place_holder)
                            .into(imvNews);
                } else {
                    GlideApp.with(itemView.getContext())
                            .load(R.drawable.bg_place_holder)
                            .into(imvNews);
                }
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
}
