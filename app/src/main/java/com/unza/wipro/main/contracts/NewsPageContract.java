package com.unza.wipro.main.contracts;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;
import com.unza.wipro.main.models.News;
import java.util.List;

/**
 * Created by admin on 1/17/18.
 */

public interface NewsPageContract {
    interface ViewImpl extends BaseViewImpl {
        void onGetData(List<News> data, boolean isLoadmode);
    }

    interface Presenter extends BasePresenterImpl {
        void loadData(String key, int categoryID, int page, int pageSize);
    }
}
