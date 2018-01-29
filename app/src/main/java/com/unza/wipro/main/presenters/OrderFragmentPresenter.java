package com.unza.wipro.main.presenters;

import com.paditech.core.helper.StringUtil;
import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.AppConstans;
import com.unza.wipro.main.contracts.OrderListContract;
import com.unza.wipro.main.models.responses.GetOrdersRSP;
import com.unza.wipro.services.AppClient;
import com.unza.wipro.utils.DateTimeUtils;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragmentPresenter extends BasePresenter<OrderListContract.ViewImpl> implements OrderListContract.Presenter, AppConstans {

    private boolean isFull;
    private int mPage = 1;
    private Long fromDate;
    private Long toDate;

    @Override
    public void loadData() {
        getOrders(true);
    }

    @Override
    public void loadMore() {
        getOrders(false);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loadUI();
    }

    private void getOrders(final boolean isRefresh) {
        if (isFull && !isRefresh) return;
        if (!app.isLogin()) return;
        if (isRefresh) getView().showProgressDialog(true);
        mPage = isRefresh ? 1 : mPage;
        AppClient.newInstance().getService().getOrders(app.getToken(),
                app.getAppKey(), fromDate, toDate, mPage, PAGE_SIZE)
                .enqueue(new Callback<GetOrdersRSP>() {
                    @Override
                    public void onResponse(Call<GetOrdersRSP> call, Response<GetOrdersRSP> response) {
                        getView().showProgressDialog(false);
                        try {
                            if (response.body() != null) {
                                if (response.body().getData() != null && response.body().getData().size() > 0)
                                    mPage++;
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
                        getView().showProgressDialog(false);
                        getView().showToast(t.getLocalizedMessage());
                    }
                });
    }

    private void loadUI() {
        getView().updateDayInFilter(DateTimeUtils.getStringFirstDayInCurrentMonth(), DateTimeUtils.getStringDayMonthYear(Calendar.getInstance().getTime()));
        getView().changeColorButtonThisMonth();
    }

    @Override
    public void onFilterClick() {
        getView().updateFilterAppearance();
    }

    @Override
    public void onSearchClick(String from, String to) {
        if (StringUtil.isEmpty(from) && StringUtil.isEmpty(to)) {
            fromDate = null;
            toDate = null;
            loadData();
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
            loadData();
            getView().dismissFilter();
        } else {
            getView().findOrder(false);
        }
    }

    @Override
    public void onBtAllClick() {
        getView().changeColorButtonAll();
        getView().updateDayInFilter("", "");
    }

    @Override
    public void onBtThisWeekClick() {
        getView().changeColorButtonThisWeek();
        getView().updateDayInFilter(DateTimeUtils.getStringFirstDayInCurrentWeek(), DateTimeUtils.getStringDayMonthYear(Calendar.getInstance().getTime()));
    }

    @Override
    public void onBtLastWeekClick() {
        getView().changeColorButtonLastWeek();
        getView().updateDayInFilter(DateTimeUtils.getStringFirstDayInLastWeek(), DateTimeUtils.getStringLastDayInLastWeek());
    }

    @Override
    public void onBtThisMonthClick() {
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

    @Override
    public void onViewAppear() {
        super.onViewAppear();
        loadData();
    }
}
