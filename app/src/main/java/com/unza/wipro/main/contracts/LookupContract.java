package com.unza.wipro.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;
import com.unza.wipro.main.models.Product;

import java.util.List;

public interface LookupContract {
    interface ViewImpl extends BaseViewImpl {
        void updateItemToList(List<Product> productList);

        void refreshProductList(List<Product> productList);

        String getCurrentKeyword();
    }

    interface Presenter extends BasePresenterImpl {
        void searchByKeyWord();

        void onLoadMore();

        void onRefresh();
    }
}
