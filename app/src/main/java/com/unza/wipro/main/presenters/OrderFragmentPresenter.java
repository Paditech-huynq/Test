package com.unza.wipro.main.presenters;

import android.util.Log;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.main.contracts.OrderListContract;
import com.unza.wipro.main.models.OrderClass;
import com.unza.wipro.main.models.OrderData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bangindong on 1/12/2018.
 */

public class OrderFragmentPresenter extends BasePresenter<OrderListContract.ViewImpl>  implements OrderListContract.Presenter {


    @Override
    public List<OrderClass> LoadMore() {
        OrderData orderData = new OrderData();
        List<OrderClass> list1 = orderData.getList();
        List<OrderClass> list = new ArrayList<>();
        list.addAll(list1);
        Log.e("LoadMore: ", list.size()+"     " + list1.size() );
        return list;
    }
}
