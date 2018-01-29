package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.AppConstans;
import com.unza.wipro.main.contracts.OrderDetailContract;
import com.unza.wipro.main.models.OrderData;
import com.unza.wipro.main.models.responses.GetOrderDetailRSP;
import com.unza.wipro.main.views.fragments.DeliveryInfoFragment;
import com.unza.wipro.services.AppClient;
import com.unza.wipro.transaction.DirectTransaction;
import com.unza.wipro.transaction.Transaction;
import com.unza.wipro.transaction.user.Customer;
import com.unza.wipro.transaction.user.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailPresenter extends BasePresenter<OrderDetailContract.ViewImpl> implements OrderDetailContract.Presenter, AppConstans {
    private Transaction.TransactionCallback directTransactionCallback = new Transaction.TransactionCallback() {
        @Override
        public void onSuccess(Transaction transaction, OrderData data) {
            getView().showProgressDialog(false);
            onPaymentSuccess(transaction);
        }

        @Override
        public void onFailure(Transaction transaction, Throwable e) {
            getView().showProgressDialog(false);
            onPaymentFailure();
        }
    };

    private Transaction.TransactionCallback orderTransactionCallback = new Transaction.TransactionCallback() {
        @Override
        public void onSuccess(Transaction transaction, OrderData data) {
            getView().showProgressDialog(false);
            onPaymentSuccess(transaction);
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
                        } catch (Exception ignored) {
                        }
                    }
                });
    }

//    public void onSubmitTransaction() {
//
////        if (app.getCurrentUser() instanceof Customer) {
////            if (mTransaction == null) mTransaction = new OrderTransaction();
////            mTransaction.create(1, (Cart) AppState.getInstance().getCurrentCart());
////            DeliveryInfo info = ((OrderTransaction) mTransaction).getDeliveryInfo();
////            if (info == null) {
////                switchFragment(DeliveryInfoFragment.newInstance(), true);
////            } else {
////                getPresenter().submitTransaction(mTransaction);
////            }
////        } else {
////            if (mTransaction == null) mTransaction = new DirectTransaction();
////            mTransaction.create(1, (Cart) AppState.getInstance().getCurrentCart());
////            getPresenter().submitTransaction(mTransaction);
////        }
//    }

    @Override
    public void onSubmitTransactionButtonClick() {
        doTransaction();
    }

    private void doTransaction() {
        final User currentUser = app.getCurrentUser();
        final Customer customer = getView().getCustomer();
        if (customer == null) {
            getView().showToast("Customer is null");
            return;
        }
        if (currentUser == null) {
            getView().showToast("User is null");
            return;
        }

        if (currentUser instanceof Customer) {
            getView().switchFragment(DeliveryInfoFragment.newInstance(), true);
        } else {
            final Transaction transaction = new DirectTransaction();
            if (transaction.create(customer.getId(), app.getCurrentCart())) {
                getView().showProgressDialog(true);
                try {
                    transaction.pay(directTransactionCallback);
                } catch (Exception e) {
                    e.printStackTrace();
                    getView().showToast(e.getLocalizedMessage());
                }
            }
        }
    }


    private void onPaymentFailure() {
        getView().showToast("Payment Failure");

    }

    private void onPaymentSuccess(Transaction transaction) {
        bus.post(transaction);
        getView().showToast("Payment Success");
        app.editCart().clear();
        getView().backToHomeScreen();
    }
}
