package com.pshop.app.main.views.fragments;


import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paditech.core.BaseFragment;
import com.paditech.core.helper.StringUtil;
import com.paditech.core.image.GlideApp;
import com.pshop.app.R;
import com.pshop.app.main.views.customs.DegreeView;
import com.pshop.app.transaction.user.Promoter;
import com.pshop.app.utils.DateTimeUtils;

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
    @BindView(R.id.tv_sales_expect)
    TextView tvSalesExpect;
    @BindView(R.id.tv_sales_actual)
    TextView tvSalesActual;
    @BindView(R.id.degree_sale)
    DegreeView degreeSale;

    private Promoter mPromoter;

    public static ProfileMemberGroupFragment newInstance(Promoter mPromoter) {

        Bundle args = new Bundle();

        ProfileMemberGroupFragment fragment = new ProfileMemberGroupFragment();
        fragment.mPromoter = mPromoter;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_profile;
    }

    @Override
    public void initView() {
        super.initView();
        updateUI();
    }

    @Override
    public String getScreenTitle() {
        if (mPromoter != null) {
            return getResources().getString(R.string.title_member_profile);
        }
        return "";
    }

    private void updateUI() {
        tvName.setText(mPromoter.getName());
        tvEmail.setText(mPromoter.getEmail());
        tvAddress.setText(mPromoter.getAddress());
        tvPhone.setText(mPromoter.getPhone());
        tvNumberSales.setText(mPromoter.getNumberOrders());
        GlideApp.with(this).load(mPromoter.getAvatar()).
                placeholder(R.drawable.ic_avatar_holder)
                .error(R.drawable.ic_avatar_holder)
                .circleCrop().into(imgAvar);
        GlideApp.with(this).load(mPromoter.getAvatar()).placeholder(R.drawable.ic_avatar_holder)
                .error(R.drawable.ic_avatar_holder)
                .into(imgAvarUnder);
        tvPoint.setText(getResources().getString(R.string.custom_profile_fragment));
        tvNumberPoint.setText(mPromoter.getNumberCustomers());
        if (Long.parseLong(mPromoter.getSalesExpect()) != 0) {
            tvSalesExpect.setText(getResources().getString(R.string.value_sales_profile_fragment, StringUtil.formatMoney(mPromoter.getSalesExpect())));
        } else {
            tvSalesExpect.setText(getResources().getString(R.string.sales_expect_is_zero_profile_fragment));
        }
        tvSalesActual.setText(getResources().getString(R.string.value_sales_profile_fragment,
                StringUtil.formatMoney(mPromoter.getSalesActual())));
        degreeSale.setValue(R.color.white, R.color.colorPrimary, Long.parseLong(mPromoter.getSalesActual()), Long.parseLong(mPromoter.getSalesExpect()));
        tvTime.setText(Html.fromHtml(getResources().getString(R.string.value_time_profile_fragment,
                DateTimeUtils.getStringDayMonthYear(DateTimeUtils.getDateFromServerDayMonthYear(mPromoter.getFrom())),
                DateTimeUtils.getStringDayMonthYear(DateTimeUtils.getDateFromServerDayMonthYear(mPromoter.getTo())))));
        lnDegree.setVisibility(View.VISIBLE);
        lnSetting.setVisibility(View.GONE);
        lnMangerSales.setVisibility(View.GONE);
    }
}
