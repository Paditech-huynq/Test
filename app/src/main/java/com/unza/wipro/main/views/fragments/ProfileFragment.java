package com.unza.wipro.main.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paditech.core.DisableTouchView;
import com.paditech.core.image.GlideApp;
import com.paditech.core.mvp.MVPFragment;
import com.unza.wipro.AppConstans;
import com.unza.wipro.AppState;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.ProfileContract;
import com.unza.wipro.main.presenters.ProfilePresenter;
import com.unza.wipro.main.views.activities.MainActivity;
import com.unza.wipro.main.views.customs.DegreeView;
import com.unza.wipro.transaction.user.Customer;
import com.unza.wipro.transaction.user.Promoter;
import com.unza.wipro.transaction.user.PromoterLeader;
import com.unza.wipro.utils.DateTimeUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class ProfileFragment extends MVPFragment<ProfilePresenter> implements ProfileContract.ViewImpl, AppConstans {
    @BindView(R.id.ln_degree)
    LinearLayout lnDegree;
    @BindView(R.id.ln_manager_sales)
    LinearLayout lnManagerSales;
    @BindView(R.id.tx_point)
    TextView tvPoint;
    @BindView(R.id.tx_numbers_point)
    TextView tvNumberPoint;
    @BindView(R.id.tx_number_sales)
    TextView tvNumberSales;
    @BindView(R.id.img_avar)
    ImageView imgAvar;
    @BindView(R.id.img_avar_under)
    ImageView imgAvarUnder;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_time_profile)
    TextView tvTime;
    @BindView(R.id.tv_sales_want)
    TextView tvSalesWant;
    @BindView(R.id.tv_sales_have)
    TextView tvSalesHave;
    @BindView(R.id.degree_sale)
    DegreeView degreeSale;
    @BindView(R.id.layoutLoading)
    DisableTouchView layoutLoading;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_profile;
    }

    @Override
    public String getScreenTitle() {
        return getString(R.string.title_profile);
    }

    public static ProfileFragment newInstance() {

        Bundle args = new Bundle();

        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void goToChangePassFragment() {
        switchFragment(UpdatePasswordFragment.newInstance(), true);
    }

    @Override
    public void goToOrderFragment() {
        switchFragment(LookupFragment.newInstance(), true);
    }

    @Override
    public void goToListProfileFragment() {
        switchFragment(ProfilePromoterListFragment.newInstance(), true);
    }

    @Override
    public void goToHomeProfile() {
        switchFragment(HomeFragment.newInstance(), false);
    }

    @Override
    public void updateUI() {
        tvName.setText(AppConstans.app.getCurrentUser().getName());
        tvEmail.setText(AppConstans.app.getCurrentUser().getEmail());
        tvAddress.setText(AppConstans.app.getCurrentUser().getAddress());
        tvPhone.setText(AppConstans.app.getCurrentUser().getName());
        tvNumberSales.setText(AppConstans.app.getCurrentUser().getNumberOrders());
        GlideApp.with(this).load(AppConstans.app.getCurrentUser().getAvatar()).circleCrop().into(imgAvar);
        GlideApp.with(this).load(AppConstans.app.getCurrentUser().getAvatar()).into(imgAvarUnder);
    }

    @Override
    public void updateUIForCustomer() {
        tvPoint.setText(getResources().getString(R.string.point_profile_fragment));
        tvNumberPoint.setText(((Customer) AppConstans.app.getCurrentUser()).getPoint());
    }

    @Override
    public void updateUIForPromoter() {
        Promoter promoter = (Promoter) AppConstans.app.getCurrentUser();
        tvPoint.setText(getResources().getString(R.string.custom_profile_fragment));
        tvNumberPoint.setText(promoter.getNumberCustomers());
        tvSalesWant.setText(Html.fromHtml(getResources().getString(R.string.sales_want_profile_fragment,
                promoter.getSaleWant())));
        tvSalesHave.setText(Html.fromHtml(getResources().getString(R.string.sales_have_profile_fragment,
                promoter.getSaleHave())));
        try {
            degreeSale.setValue(R.color.white, R.color.colorPrimary, Long.parseLong(promoter.getSaleHave()), Long.parseLong(promoter.getSaleWant()));
            tvTime.setText(Html.fromHtml(getResources().getString(R.string.time_profile_fragment,
                    DateTimeUtils.getStringDayMonthYear(DateTimeUtils.getDateFromServerDayMonthYear(promoter.getFrom())),
                    DateTimeUtils.getStringDayMonthYear(DateTimeUtils.getDateFromServerDayMonthYear(promoter.getTo())))));
        } catch (NumberFormatException ignored) {}
    }

    @Override
    public void startUI() {
        if (AppConstans.app.getCurrentUser() instanceof Promoter) {
            lnDegree.setVisibility(View.VISIBLE);
            if (AppConstans.app.getCurrentUser() instanceof PromoterLeader) {
                lnManagerSales.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick(R.id.rlt_logout)
    protected void logout() {
        AppState.getInstance().logout();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @OnClick(R.id.rlt_change_pass)
    public void onChangePassClick() {
        getPresenter().onChangePassClick();
    }

    @OnClick(R.id.rlt_list_order)
    public void onListOrderClick() {
        getPresenter().onListOrderClick();
    }

    @OnClick(R.id.rlt_logout)
    public void onLogoutClick() {
        getPresenter().onLogOutClick();
    }

    @OnClick(R.id.rlt_manager_sales)
    public void onManagerSalesClick() {
        getPresenter().onManagerSalesClick();
    }

    @OnClick(R.id.rlt_policy_permis)
    public void onPolicyPermisClick() {

    }


    @OnClick(R.id.rlt_questions)
    public void onQuestionClick() {

    }

    @Override
    public void showProgressDialog(boolean isShown) {
        layoutLoading.setVisibility(isShown ? View.VISIBLE : View.GONE);
    }
}
