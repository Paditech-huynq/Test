package com.unza.wipro.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;
import com.unza.wipro.main.models.Product;

import java.util.List;

public interface LookupContract {
    interface ViewImpl extends BaseViewImpl {
        void updateToListItem(List<Product> data);
        String getTextSearch();
    }

    interface Presenter extends BasePresenterImpl {
        void searchByText();
        void loadMore();
    }
}
