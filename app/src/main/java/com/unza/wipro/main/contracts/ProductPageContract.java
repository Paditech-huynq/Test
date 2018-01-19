package com.unza.wipro.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;
import com.unza.wipro.main.models.Product;

import java.util.List;

public interface ProductPageContract {
    interface ViewImpl extends BaseViewImpl {
        void updateItemToList(List<Product> products);

        void clearProductList(boolean isClear);
    }

    interface Presenter extends BasePresenterImpl {
        void onLoadMore();

        void onRefresh(List<Product> products);
    }
}