package com.pshop.app.main.presenters;

import com.paditech.core.mvp.BasePresenter;
import com.pshop.app.AppConstans;
import com.pshop.app.R;
import com.pshop.app.main.contracts.DeliveryInfoContract;
import com.pshop.app.main.models.OrderData;
import com.pshop.app.transaction.OrderTransaction;
import com.pshop.app.transaction.Transaction;

public class DeliveryInfoPresenter extends BasePresenter<DeliveryInfoContract.ViewImpl> implements DeliveryInfoContract.Presenter, AppConstans {

    @Override
    public void onSubmitButtonClick() {
        final OrderTransaction transaction = new OrderTransaction();
        if(getView().getDeliverInfo() == null){
            return;
        }
        transaction.setPaymentMethod(Transaction.PaymentMethod.COD).setDeliveryInfo(getView().getDeliverInfo());
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
        public void onSuccess(Transaction transaction, OrderData data, String message) {
            try {
                getView().showProgressDialog(false);
                onPaymentSuccess(transaction, message);
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
        getView().showToast(getView().getContext().getString(R.string.purchase_fail));
    }

    private void onPaymentSuccess(Transaction transaction, String message) {
        bus.post(transaction);
        getView().showToast(message);
        app.editCart().clear();
        getView().backToHomeScreen();
    }
}
