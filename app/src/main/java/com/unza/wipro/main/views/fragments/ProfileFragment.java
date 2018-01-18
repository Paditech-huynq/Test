package com.unza.wipro.main.views.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.paditech.core.image.GlideApp;
import com.paditech.core.mvp.MVPFragment;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.ProfileContract;
import com.unza.wipro.main.models.User;
import com.unza.wipro.main.presenters.ProfilePresenter;
import com.unza.wipro.main.views.customs.DegreeView;
import com.unza.wipro.utils.DateTimeUntils;

import java.util.Calendar;

import butterknife.BindView;

public class ProfileFragment extends MVPFragment<ProfilePresenter> implements ProfileContract.ViewImpl {
    @BindView(R.id.ln_degree)
    LinearLayout lnDegree;
    @BindView(R.id.ln_manager_sales)
    LinearLayout lnManagerSales;
    @BindView(R.id.tx_point)
    TextView tvPoint;
    @BindView(R.id.tx_number_point)
    TextView tvNumberPoint;
    @BindView(R.id.tx_number_sales)
    TextView tvNumberSales;
    @BindView(R.id.img_avar)
    ImageView imgAvar;
    @BindView(R.id.img_avar_under)
    ImageView imgAvarUnder;
    @BindView(R.id.text_name)
    TextView tvName;
    @BindView(R.id.text_phone)
    TextView tvPhone;
    @BindView(R.id.text_email)
    TextView tvEmail;
    @BindView(R.id.text_address)
    TextView tvAddress;
    @BindView(R.id.text_time_profile)
    TextView tvTime;
    @BindView(R.id.text_sales_want)
    TextView tvSalesWant;
    @BindView(R.id.text_sales_have)
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
                tvNumberPoint.setText(user.getNumberPoint());
                break;
        }
        tvNumberSales.setText(String.valueOf(user.getNumberSales()));
        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());
        tvAddress.setText(user.getAddress());
        GlideApp.with(this).load(R.drawable.bg_test).apply(RequestOptions.circleCropTransform()).into(imgAvar);
        GlideApp.with(this).load(R.drawable.bg_test).into(imgAvarUnder);
    }

    private void updateUIForEmployee(User user) {
        tvTime.setText(String.format("  %s - %s",DateTimeUntils.getStringDayMonthYear(user.getDateStart()),
                DateTimeUntils.getStringDayMonthYear(Calendar.getInstance().getTime())));
        tvSalesHave.setText(String.format("  %s  VNĐ",user.getSaleHave()));
        tvSalesWant.setText(String.format("  %s  VNĐ",user.getSaleWant()));
        degreeSale.setValue(R.color.white, R.color.colorPrimaryDark, user.getSaleHave(), user.getSaleWant());
    }
}
