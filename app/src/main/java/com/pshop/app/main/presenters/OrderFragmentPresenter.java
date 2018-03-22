package com.pshop.app.main.presenters;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.paditech.core.helper.StringUtil;
import com.paditech.core.mvp.BasePresenter;
import com.squareup.otto.Subscribe;
import com.pshop.app.AppConstans;
import com.pshop.app.main.contracts.OrderListContract;
import com.pshop.app.main.models.MyFilter;
import com.pshop.app.main.models.responses.GetOrdersRSP;
import com.pshop.app.services.AppClient;
import com.pshop.app.transaction.Transaction;
import com.pshop.app.utils.DateTimeUtils;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.pshop.app.main.models.MyFilter.BUTTON_ALL;
import static com.pshop.app.main.models.MyFilter.BUTTON_LAST_WEEK;
import static com.pshop.app.main.models.MyFilter.BUTTON_THIS_MONTH;
import static com.pshop.app.main.models.MyFilter.BUTTON_THIS_WEEK;
import static com.pshop.app.main.models.MyFilter.NO_BUTTON;

public class OrderFragmentPresenter extends BasePresenter<OrderListContract.ViewImpl> implements OrderListContract.Presenter, AppConstans {
    private final static int START_PAGE_INDEX = 1;
    private boolean isFull;
    private int mPage = 1;
    private Long fromDate;
    private Long toDate;
    private boolean isPending;
    private MyFilter currentFilter;
    private boolean lastLoginState;
    private boolean isDisappearing;
    private boolean isSearch;

    @Override
    public void onLoadMore() {
        getOrdersListFromServer(false);
    }

    @Override
    public void onViewAppear() {
        super.onViewAppear();
        getView().enablePullToRefresh(true);
        getView().showMessageNoResult(true);
        updateDateTime();
        if (lastLoginState != app.isLogin()) {
            onRefresh();
        }
        lastLoginState = app.isLogin();
    }

    @Override
    public void onViewDisAppear() {
        super.onViewDisAppear();
        if (!isDisappearing) {
            lastLoginState = app.isLogin();
        }
        isDisappearing = true;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bus.register(this);
        updateDateTime();
        onSearch(currentFilter.getFrom(),currentFilter.getTo());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    public void getOrdersListFromServer(final boolean isRefresh) {
        getView().showMessageNoResult(false);
        if (isPending) {
            getView().setRefreshing(false);
            return;
        }
        if (isRefresh) {
            isFull = false;
        }
        if (isFull) {
            return;
        }

        isPending = true;
        getView().enablePullToRefresh(!(isSearch || (!isRefresh && mPage == START_PAGE_INDEX)));
        getView().showProgressDialog(isSearch || (!isRefresh && mPage == START_PAGE_INDEX));
        isSearch = false;
        mPage = isRefresh ? START_PAGE_INDEX : mPage;
        AppClient.newInstance().getService().getOrders(app.getToken(),
                app.getAppKey(), fromDate, toDate, mPage, PAGE_SIZE)
                .enqueue(new Callback<GetOrdersRSP>() {
                    @Override
                    public void onResponse(Call<GetOrdersRSP> call, Response<GetOrdersRSP> response) {
                        try {
                            Log.e("testgetOrders", String.valueOf(response.code()));
                            isPending = false;
                            getView().showProgressDialog(false);
                            getView().enablePullToRefresh(true);
                            getView().setRefreshing(false);
                            if (response.body() != null) {
                                if (response.body().getData() != null && response.body().getData().size() > 0) {
                                    mPage++;
                                } else {
                                    if (mPage == START_PAGE_INDEX) {
                                        getView().showMessageNoResult(true);
                                    }
                                }
                                isFull = response.body().getData().size() < PAGE_SIZE;
                                if (isRefresh) {
                                    isFull = false;
                                    getView().scrollToTopForRefresh();
                                    getView().refreshData(response.body().getData());
                                } else {
                                    getView().addItemToList(response.body().getData());
                                }
                            }
                        } catch (Exception e) {
                            getView().enablePullToRefresh(true);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetOrdersRSP> call, Throwable t) {
                            isPending = false;
                            getView().showProgressDialog(false);
                            getView().showToast(t.getLocalizedMessage());
                            getView().enablePullToRefresh(true);
                    }
                });
    }

    private void updateDateTime() {
        if (currentFilter == null) {
            currentFilter = new MyFilter();
            onBtThisMonthClick();
            return;
        }
        switch (currentFilter.getButtonClicked()) {
            case NO_BUTTON:
                getView().changeColorButtonToDefault();
                break;
            case BUTTON_ALL:
                onBtAllClick();
                break;
            case BUTTON_LAST_WEEK:
                onBtLastWeekClick();
                break;
            case BUTTON_THIS_MONTH:
                onBtThisMonthClick();
                break;
            case BUTTON_THIS_WEEK:
                onBtThisWeekClick();
                break;
        }
        getView().updateDayInFilter(currentFilter.getFrom(), currentFilter.getTo());
        }

    @Override
    public void onFilterClick() {
        getView().updateFilterAppearance();
    }

    @Override
    public void onSearch(String from, String to) {
        if (StringUtil.isEmpty(from) && StringUtil.isEmpty(to)) {
            isSearch = true;
            fromDate = null;
            toDate = null;
            getView().findOrder(true);
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
            isSearch = true;
            fromDate = startDate.getTime() / 1000;
            toDate = endDate.getTime() / 1000;
            getView().findOrder(true);
            getView().dismissFilter();
        } else {
            getView().findOrder(false);
        }
    }

    @Override
    public void onBtAllClick() {
        currentFilter.setButtonClicked(BUTTON_ALL);
        getView().changeColorButtonAll();
        getView().updateDayInFilter(EMPTY, EMPTY);
    }

    @Override
    public void onBtThisWeekClick() {
        currentFilter.setButtonClicked(BUTTON_THIS_WEEK);
        getView().changeColorButtonThisWeek();
        getView().updateDayInFilter(DateTimeUtils.getStringFirstDayInCurrentWeek(), DateTimeUtils.getStringDayMonthYear(Calendar.getInstance().getTime()));
    }

    @Override
    public void onBtLastWeekClick() {
        currentFilter.setButtonClicked(BUTTON_LAST_WEEK);
        getView().changeColorButtonLastWeek();
        getView().updateDayInFilter(DateTimeUtils.getStringFirstDayInLastWeek(), DateTimeUtils.getStringLastDayInLastWeek());
    }

    @Override
    public void onBtThisMonthClick() {
        currentFilter.setButtonClicked(BUTTON_THIS_MONTH);
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
        currentFilter.setButtonClicked(NO_BUTTON);
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
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getView() != null) {
                    getView().scrollToTop();
                }
                getOrdersListFromServer(true);
            }
        }, 100);
    }

    public void updateCurrentFilter(String from,String to) {
        currentFilter.setFrom(from);
        currentFilter.setTo(to);
    }
}
