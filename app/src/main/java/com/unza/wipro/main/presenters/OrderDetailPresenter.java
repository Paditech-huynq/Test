package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.AppConstans;
import com.unza.wipro.main.contracts.OrderDetailContract;
import com.unza.wipro.main.models.responses.GetOrderDetailRSP;
import com.unza.wipro.main.models.OrderData;
import com.unza.wipro.services.AppClient;
import com.unza.wipro.transaction.DirectTransaction;
import com.unza.wipro.transaction.Transaction;
import com.unza.wipro.transaction.user.Customer;
import com.unza.wipro.transaction.user.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailPresenter extends BasePresenter<OrderDetailContract.ViewImpl> implements OrderDetailContract.Presenter, AppConstans {
    int orderId;

    @Override
    public void onCreate() {
        super.onCreate();
        orderId = getView().getOrderId();
        if (orderId >= 0) {
            getProductDetailFromServer();
        }
    }

    private void getProductDetailFromServer() {
        getView().showProgressDialog(true);
        AppClient.newInstance().getService().getOrderDetail(app.getToken(), app.getAppKey(), orderId)
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
//
//    @Override
//    public void submitTransaction(Transaction transaction) {
//        if (!app.isLogin()) {
//            return;
//        }
//        getView().showToast("submited");
//        //todo: implement logic for submit transaction here
//    }
//
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

    private void onPaymentFailure() {
        //todo: handle for payment success
    }

    private void onPaymentSuccess() {
        //todo: handle for payment failure

    }

    @Override
    public void onSubmitTransactionButtonClick() {
        final User currentUser = app.getCurrentUser();
        final Customer customer = getView().getCustomer();
        if (customer == null) {
            return;
        }
        if (currentUser == null) {
            return;
        }

        if (currentUser instanceof Customer) {
            //todo: implement transaction for Customer
        } else {
            final Transaction transaction = new DirectTransaction();
            if (transaction.create(customer.getId(), app.getCurrentCart())) {
                getView().showProgressDialog(true);
                try {
                    transaction.pay(new Transaction.TransactionCallback() {
                        @Override
                        public void onSuccess(OrderData data) {
                            getView().showProgressDialog(false);
                            onPaymentSuccess();
                        }

                        @Override
                        public void onFailure(Throwable e) {
                            getView().showProgressDialog(false);
                            onPaymentFailure();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    getView().showToast(e.getLocalizedMessage());
                }
            }
        }
    }
}
