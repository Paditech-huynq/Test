package com.unza.wipro.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;
import com.unza.wipro.main.models.News;
import com.unza.wipro.main.models.NewsCategory;

import java.util.List;

public interface NewsPageContract {
    interface ViewImpl extends BaseViewImpl {
        void updateItemToList(List<News> data);
        NewsCategory getCategory();
        void refreshList(List<News> news);
    }

    interface Presenter extends BasePresenterImpl {
        void onLoadMore();

        void onRefresh();
    }
}
