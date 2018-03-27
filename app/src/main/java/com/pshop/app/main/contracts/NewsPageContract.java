package com.pshop.app.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;
import com.pshop.app.main.models.News;
import com.pshop.app.main.models.NewsCategory;

import java.util.List;

public interface NewsPageContract {
    interface ViewImpl extends BaseViewImpl {
        void updateItemToList(List<News> data);

        NewsCategory getCategory();

        void refreshList(List<News> news);

        void scrollToTop();
    }

    interface Presenter extends BasePresenterImpl {
        void onLoadMore();

        void onRefresh();
    }
}
