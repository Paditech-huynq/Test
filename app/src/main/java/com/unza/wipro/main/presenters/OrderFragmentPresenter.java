package com.unza.wipro.main.presenters;

import android.os.Handler;
import android.os.Looper;

import com.paditech.core.helper.StringUtil;
import com.paditech.core.mvp.BasePresenter;
import com.squareup.otto.Subscribe;
import com.unza.wipro.AppConstans;
import com.unza.wipro.main.contracts.OrderListContract;
import com.unza.wipro.main.models.responses.GetOrdersRSP;
import com.unza.wipro.services.AppClient;
import com.unza.wipro.transaction.Transaction;
import com.unza.wipro.utils.DateTimeUtils;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragmentPresenter extends BasePresenter<OrderListContract.ViewImpl> implements OrderListContract.Presenter, AppConstans {
    private final static int START_PAGE_INDEX = 1;
    private boolean isFull;
    private int mPage = 1;
    private Long fromDate;
    private Long toDate;
    boolean isPending;

    @Override
    public void onLoadMore() {
        getOrdersListFromServer(false);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bus.register(this);
        getOrdersListFromServer(false);
        updateDateTime();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    private void getOrdersListFromServer(final boolean isRefresh) {
        if (isPending) {
            return;
        }
        if (isRefresh) {
            isFull = false;
        }
        if (isFull || !app.isLogin()) {
            return;
        }

        isPending = true;
        mPage = isRefresh ? START_PAGE_INDEX : mPage;
        getView().showProgressDialog(!isRefresh && mPage == START_PAGE_INDEX);

        AppClient.newInstance().getService().getOrders(app.getToken(),
                app.getAppKey(), fromDate, toDate, mPage, PAGE_SIZE)
                .enqueue(new Callback<GetOrdersRSP>() {
                    @Override
                    public void onResponse(Call<GetOrdersRSP> call, Response<GetOrdersRSP> response) {
                        try {
                            isPending = false;
                            getView().showProgressDialog(false);
                            getView().setRefreshing(false);
                            if (response.body() != null) {
                                if (response.body().getData() != null && response.body().getData().size() > 0) {
                                    mPage++;
                                }
                                isFull = response.body().getData().size() < PAGE_SIZE;
                                if (isRefresh) {
                                    isFull = false;
                                    getView().refreshData(response.body().getData());
                                } else {
                                    getView().addItemToList(response.body().getData());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetOrdersRSP> call, Throwable t) {
                        try {
                            isPending = false;
                            getView().showProgressDialog(false);
                            getView().showToast(t.getLocalizedMessage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void updateDateTime() {
        getView().updateDayInFilter(DateTimeUtils.getStringFirstDayInCurrentMonth(), DateTimeUtils.getStringDayMonthYear(Calendar.getInstance().getTime()));
        getView().changeColorButtonThisMonth();
    }

    @Override
    public void onFilterClick() {
        getView().updateFilterAppearance();
    }

    @Override
    public void onSearch(String from, String to) {
        if (StringUtil.isEmpty(from) && StringUtil.isEmpty(to)) {
            fromDate = null;
            toDate = null;
            getOrdersListFromServer(true);
            getView().dismissFilter();
            return;
        }
        if (StringUtil.isEmpty(from) || StringUtil.isEmpty(to)) {
            getView().findOrder(false);
            return;
        }
        Date startDate = DateTimeUtils.getDateFromStringDayMonthYear(from);
        Date endDate = DateTimeUtils.getEndOfeDateFromStringDayMonthYear(to);
        assert startDate != null;
        if (startDate.before(endDate)) {
            getView().findOrder(true);
            fromDate = startDate.getTime() / 1000;
            toDate = endDate.getTime() / 1000;
            getOrdersListFromServer(true);
            getView().dismissFilter();
        } else {
            getView().findOrder(false);
        }
    }

    @Override
    public void onBtAllClick() {
        onSearch("","");
        getView().changeColorButtonAll();
        getView().updateDayInFilter("", "");
    }

    @Override
    public void onBtThisWeekClick() {
        onSearch(DateTimeUtils.getStringFirstDayInCurrentWeek(), DateTimeUtils.getStringDayMonthYear(Calendar.getInstance().getTime()));
        getView().changeColorButtonThisWeek();
        getView().updateDayInFilter(DateTimeUtils.getStringFirstDayInCurrentWeek(), DateTimeUtils.getStringDayMonthYear(Calendar.getInstance().getTime()));
    }

    @Override
    public void onBtLastWeekClick() {
        onSearch(DateTimeUtils.getStringFirstDayInLastWeek(), DateTimeUtils.getStringLastDayInLastWeek());
        getView().changeColorButtonLastWeek();
        getView().updateDayInFilter(DateTimeUtils.getStringFirstDayInLastWeek(), DateTimeUtils.getStringLastDayInLastWeek());
    }

    @Override
    public void onBtThisMonthClick() {
        onSearch(DateTimeUtils.getStringFirstDayInCurrentMonth(), DateTimeUtils.getStringDayMonthYear(Calendar.getInstance().getTime()));
        getView().changeColorButtonThisMonth();
        getView().updateDayInFilter(DateTimeUtils.getStringFirstDayInCurrentMonth(), DateTimeUtils.getStringDayMonthYear(Calendar.getInstance().getTime()));
    }

    @Override
    public void onBtCalenderClick(int whatCalenderInFilter, String dayCalenderFilter) {
        if (StringUtil.isEmpty(dayCalenderFilter)) {
            getView().displayDatePicker(whatCalenderInFilter, Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.YEAR));
        } else {
            String[] time = dayCalenderFilter.split("/");
            getView().displayDatePicker(whatCalenderInFilter, Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]));
        }
        getView().changeColorButtonToDefault();
    }

    @Override
    public void onChooseDate(int whatCalenderInFilter, Date dayChose) {
        getView().displayDateChose(whatCalenderInFilter, DateTimeUtils.getStringDayMonthYear(dayChose));
    }

    @Override
    public void onUserTouchOutside() {
        getView().dismissFilter();
    }

    @Override
    public void onItemClick(int position) {
        getView().goToOrderDetailScreen(position);
    }

    /**
     * Refresh UI when transaction success
     *
     * @param transaction
     */
    @Subscribe
    public void onTransactionSuccess(Transaction transaction) {
        onRefresh();
    }

    @Override
    public void onRefresh() {
        //todo: implement refresh logic here
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                getView().scrollToTop();
                getOrdersListFromServer(true);
            }
        },100);
    }
}
