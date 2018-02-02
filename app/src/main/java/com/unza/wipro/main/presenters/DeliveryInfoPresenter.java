package com.unza.wipro.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.AppConstans;
import com.unza.wipro.main.contracts.DeliveryInfoContract;
import com.unza.wipro.main.models.OrderData;
import com.unza.wipro.transaction.OrderTransaction;
import com.unza.wipro.transaction.Transaction;

public class DeliveryInfoPresenter extends BasePresenter<DeliveryInfoContract.ViewImpl> implements DeliveryInfoContract.Presenter, AppConstans {

    @Override
    public void onSubmitButtonClick() {
        final OrderTransaction transaction = new OrderTransaction();
        transaction.setPaymentMethod(Transaction.PaymentMethod.COD).setDeliveryInfo(getView().getDeliverInfo());
        if(getView().getDeliverInfo() == null){
            return;
        }
        final String customerId = getView().getCustomerId();
        if (transaction.create(customerId, app.getCurrentCart())) {
            try {
                getView().showProgressDialog(true);
                transaction.pay(orderTransactionCallback);
            } catch (Exception e) {
                getView().showProgressDialog(false);
                e.printStackTrace();
                getView().showToast(e.getLocalizedMessage());
            }
        }
    }

    private Transaction.TransactionCallback orderTransactionCallback = new Transaction.TransactionCallback() {
        @Override
        public void onSuccess(Transaction transaction, OrderData data) {
            try {
                getView().showProgressDialog(false);
                onPaymentSuccess(transaction);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Transaction transaction, Throwable e) {
            try {
                getView().showProgressDialog(false);
                e.printStackTrace();
                onPaymentFailure();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    };

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
