package com.pshop.app.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.pshop.app.AppConstans;
import com.pshop.app.R;
import com.pshop.app.main.contracts.OrderDetailContract;
import com.pshop.app.main.models.OrderData;
import com.pshop.app.main.models.UserInfo;
import com.pshop.app.main.models.responses.GetOrderDetailRSP;
import com.pshop.app.main.views.fragments.DeliveryInfoFragment;
import com.pshop.app.services.AppClient;
import com.pshop.app.transaction.DirectTransaction;
import com.pshop.app.transaction.Transaction;
import com.pshop.app.transaction.user.Customer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailPresenter extends BasePresenter<OrderDetailContract.ViewImpl> implements OrderDetailContract.Presenter, AppConstans {
    private Transaction.TransactionCallback directTransactionCallback = new Transaction.TransactionCallback() {
        @Override
        public void onSuccess(Transaction transaction, OrderData data, String message) {
            getView().showProgressDialog(false);
            onPaymentSuccess(transaction, message);
        }

        @Override
        public void onFailure(Transaction transaction, Throwable e) {
            getView().showProgressDialog(false);
            onPaymentFailure();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        bus.register(this);
        if (getView().getOrderId() >= 0) {
            getProductDetailFromServer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    private void getProductDetailFromServer() {
        getView().showProgressDialog(true);
        AppClient.newInstance().getService().getOrderDetail(app.getToken(), app.getAppKey(), getView().getOrderId())
                .enqueue(new Callback<GetOrderDetailRSP>() {
                    @Override
                    public void onResponse(Call<GetOrderDetailRSP> call, Response<GetOrderDetailRSP> response) {
                        try {
                            getView().showProgressDialog(false);
                            if (response.body() != null) {
                                getView().showOrderDetail(response.body().getOrder());
                            }
                        } catch (Exception ignored) {
                        }
                    }

                    @Override
                    public void onFailure(Call<GetOrderDetailRSP> call, Throwable t) {
                        try {
                            getView().showProgressDialog(false);
                            getView().showToast(t.getLocalizedMessage());
                            t.printStackTrace();
                        } catch (Exception ignored) {
                        }
                    }
                });
    }

    @Override
    public void onSubmitTransactionButtonClick() {
        doTransaction();
    }

    private void doTransaction() {
        final UserInfo currentUser = app.getCurrentUser();
        final Customer customer = getView().getCustomer();
        if (!app.isLogin()) {
            getView().showToast(getView().getContext().getString(R.string.err_msg_transaction_no_login));
            return;
        }
        if (customer == null) {
            getView().showToast(getView().getContext().getString(R.string.err_msg_transaction_no_customer));
            return;
        }
        if (currentUser == null) {
            getView().showToast(getView().getContext().getString(R.string.err_msg_transaction_no_creator));
            return;
        }

        if (app.getCurrentCart().getTotalQuantity() == 0) {
            getView().showToast(getView().getContext().getString(R.string.err_msg_cart_empty));
            return;
        }
        if (currentUser instanceof Customer) {
            getView().switchFragment(DeliveryInfoFragment.newInstance(customer.getId()), true);
        } else {
            final Transaction transaction = new DirectTransaction();
            if (transaction.create(customer.getId(), app.getCurrentCart())) {
                try {
                    getView().showProgressDialog(true);
                    transaction.pay(directTransactionCallback);
                } catch (Exception e) {
                    getView().showProgressDialog(false);
                    e.printStackTrace();
                    getView().showToast(e.getLocalizedMessage());
                }
            }
        }
    }


    private void onPaymentFailure() {
        getView().showToast(getView().getContext().getString(R.string.purchase_fail));
    }

    private void onPaymentSuccess(Transaction transaction, String message) {
        bus.post(transaction);
        getView().showToast(message);
        app.editCart().clear();
        getView().backToLastScreen();
    }
}
