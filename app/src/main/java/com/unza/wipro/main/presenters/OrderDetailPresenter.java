package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.AppState;
import com.unza.wipro.main.contracts.OrderDetailContract;
import com.unza.wipro.main.models.Order;
import com.unza.wipro.main.models.responses.GetOrderDetailRSP;
import com.unza.wipro.services.AppClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailPresenter extends BasePresenter<OrderDetailContract.ViewImpl> implements OrderDetailContract.Presenter {

    @Override
    public void onCreate() {
        super.onCreate();
        loadData();
    }

    @Override
    public void loadData() {
        getProductDetail();
    }

    private void getProductDetail() {
        Order order = getView().getOrder();
        if (order == null || !AppState.getInstance().isLogin()) return;
        getView().showProgressDialog(true);
        AppClient.newInstance().getService().getOrderDetail(AppState.getInstance().getToken(),
                AppState.getInstance().getAppKey(), order.getId())
                .enqueue(new Callback<GetOrderDetailRSP>() {
                    @Override
                    public void onResponse(Call<GetOrderDetailRSP> call, Response<GetOrderDetailRSP> response) {
                        try {
                            getView().showProgressDialog(false);
                            if (response.body() != null) {
                                getView().showOrderDetail(response.body().getOrder());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetOrderDetailRSP> call, Throwable t) {
                        getView().showProgressDialog(false);
                        getView().showToast(t.getLocalizedMessage());
                    }
                });
    }
}
