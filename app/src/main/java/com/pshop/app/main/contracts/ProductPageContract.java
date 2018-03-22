package com.pshop.app.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;
import com.pshop.app.main.models.Product;

import java.util.List;

public interface ProductPageContract {
    interface ViewImpl extends BaseViewImpl {
        void addItemToList(List<Product> products);

        void refreshData(List<Product> products);

        void scrollToTop();
    }

    interface Presenter extends BasePresenterImpl {
        void onLoadMore();

        void onRefresh();
    }
}