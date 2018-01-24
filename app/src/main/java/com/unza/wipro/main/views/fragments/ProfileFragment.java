package com.unza.wipro.main.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.paditech.core.helper.PrefUtils;
import com.paditech.core.image.GlideApp;
import com.paditech.core.mvp.MVPFragment;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.ProfileContract;
import com.unza.wipro.main.models.LoginClient;
import com.unza.wipro.main.models.User;
import com.unza.wipro.main.presenters.ProfilePresenter;
import com.unza.wipro.main.views.activities.MainActivity;
import com.unza.wipro.main.views.customs.DegreeView;
import com.unza.wipro.utils.DateTimeUtils;

import java.util.Calendar;

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

    public static final int TYPE_USER_CUSTOM = 0;
    public static final int TYPE_USER_EMPLOYEE = 1;
    public static final int TYPE_USER_MANAGER = 2;

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
    public void updateUI(User user) {
        switch (user.getTypeUse()) {
            case TYPE_USER_MANAGER:
                lnManagerSales.setVisibility(View.VISIBLE);
            case TYPE_USER_EMPLOYEE:
                tvPoint.setText(getText(R.string.custom_profile_fragment));
                lnDegree.setVisibility(View.VISIBLE);
                updateUIForEmployee(user);
            default:
                tvNumberPoint.setText(String.valueOf(user.getNumberPoint()));
                break;
        }
        tvNumberSales.setText(String.valueOf(user.getNumberSales()));
        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());
        tvAddress.setText(user.getAddress());
        GlideApp.with(this).load(R.drawable.bg_test).apply(RequestOptions.circleCropTransform()).into(imgAvar);
    }

    @Override
    public void goToChangePassFragment() {
        switchFragment(UpdatePasswordFragment.newInstance(),true);
    }

    @Override
    public void goToOrderFragment() {
        switchFragment(LookupFragment.newInstance(), true);
    }

    @Override
    public void goToListProfileFragment() {
        switchFragment(ProfileListFragment.newInstance(), true);
    }

    @Override
    public void goToHomeProfile() {
        switchFragment(HomeFragment.newInstance(), false);
    }

    private void updateUIForEmployee(User user) {
        tvTime.setText(Html.fromHtml(getResources().getString(R.string.time_profile_fragment,DateTimeUtils.getStringDayMonthYear(user.getDateStart()),
                DateTimeUtils.getStringDayMonthYear(Calendar.getInstance().getTime()))));
        tvSalesHave.setText(Html.fromHtml(getResources().getString(R.string.sales_have_profile_fragment,String.valueOf(user.getSaleHave()))));
        tvSalesWant.setText(Html.fromHtml(getResources().getString(R.string.sales_want_profile_fragment,String.valueOf(user.getSaleWant()))));
        degreeSale.setValue(R.color.white, R.color.colorPrimaryDark, user.getSaleHave(), user.getSaleWant());
    }

    @OnClick(R.id.rlt_logout)
    protected void logout() {
        LoginClient.logout(getActivity());
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @OnClick(R.id.rlt_change_pass)
    public void onChangePassClick(){
        getPresenter().onChangePassClick();
    }

    @OnClick(R.id.rlt_list_order)
    public void onListOrderClick(){
        getPresenter().onListOrderClick();
    }

    @OnClick(R.id.rlt_logout)
    public void onLogoutClick(){
        getPresenter().onLogOutClick();
    }

    @OnClick(R.id.rlt_manager_sales)
    public void onManagerSalesClick(){
        getPresenter().onManagerSalesClick();
    }

    @OnClick(R.id.rlt_policy_permis)
    public void onPolicyPermisClick(){

    }

    @OnClick(R.id.rlt_questions)
    public void onQuestionClick(){

    }
}
