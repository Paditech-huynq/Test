package com.unza.wipro.main.views.fragments;


import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paditech.core.BaseFragment;
import com.paditech.core.image.GlideApp;
import com.unza.wipro.R;
import com.unza.wipro.main.views.customs.DegreeView;
import com.unza.wipro.transaction.user.Promoter;
import com.unza.wipro.utils.DateTimeUtils;

import butterknife.BindView;

public class ProfileMemberGroupFragment extends BaseFragment {
    @BindView(R.id.ln_setting)
    LinearLayout lnSetting;
    @BindView(R.id.ln_degree)
    LinearLayout lnDegree;
    @BindView(R.id.ln_manager_sales)
    LinearLayout lnMangerSales;
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
    
    private Promoter mPromoter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_profile;
    }

    public static ProfileMemberGroupFragment newInstance(Promoter mPromoter) {

        Bundle args = new Bundle();

        ProfileMemberGroupFragment fragment = new ProfileMemberGroupFragment();
        fragment.mPromoter = mPromoter;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        updateUI();
    }
    
    @Override
    public String getScreenTitle() {
        if(mPromoter != null){
            return getResources().getString(R.string.title_member_profile,mPromoter.getName());
        }
        return "";
    }

    private void updateUI() {
        tvName.setText(mPromoter.getName());
        tvEmail.setText(mPromoter.getEmail());
        tvAddress.setText(mPromoter.getAddress());
        tvPhone.setText(mPromoter.getName());
        tvNumberSales.setText(mPromoter.getNumberOrders());
        GlideApp.with(this).load(mPromoter.getAvatar()).circleCrop().into(imgAvar);
        GlideApp.with(this).load(mPromoter.getAvatar()).into(imgAvarUnder);
        tvPoint.setText(getResources().getString(R.string.custom_profile_fragment));
        tvNumberPoint.setText(mPromoter.getNumberCustomers());
        tvSalesWant.setText(Html.fromHtml(getResources().getString(R.string.sales_want_profile_fragment,
                mPromoter.getSaleWant())));
        tvSalesHave.setText(Html.fromHtml(getResources().getString(R.string.sales_have_profile_fragment,
                mPromoter.getSaleHave())));
        try {
            degreeSale.setValue(R.color.white, R.color.colorPrimary, Long.parseLong(mPromoter.getSaleHave()), Long.parseLong(mPromoter.getSaleWant()));
            tvTime.setText(Html.fromHtml(getResources().getString(R.string.time_profile_fragment,
                    DateTimeUtils.getStringDayMonthYear(DateTimeUtils.getDateFromServerDayMonthYear(mPromoter.getFrom())),
                    DateTimeUtils.getStringDayMonthYear(DateTimeUtils.getDateFromServerDayMonthYear(mPromoter.getTo())))));
        } catch (NumberFormatException ignored) {}
        lnDegree.setVisibility(View.VISIBLE);
        lnSetting.setVisibility(View.GONE);
        lnMangerSales.setVisibility(View.GONE);
    }
}
