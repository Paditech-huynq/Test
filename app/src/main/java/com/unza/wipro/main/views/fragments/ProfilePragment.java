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
import com.unza.wipro.main.models.UserEmployee;
import com.unza.wipro.main.models.UserManager;
import com.unza.wipro.main.presenters.ProfilePresenter;
import com.unza.wipro.main.views.customs.Degreeview;
import com.unza.wipro.utils.DateTimeUntils;
import com.unza.wipro.utils.Utils;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;

public class ProfilePragment extends MVPFragment<ProfilePresenter> implements ProfileContract.ViewImpl {

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
    TextView tvAdress;
    @BindView(R.id.text_time_profile)
    TextView tvTime;
    @BindView(R.id.text_sales_want)
    TextView tvSalesWant;
    @BindView(R.id.text_sales_have)
    TextView tvSalesHave;
    @BindView(R.id.degree_sale)
    Degreeview degreeSale;
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

    public static ProfilePragment newInstance() {

        Bundle args = new Bundle();

        ProfilePragment fragment = new ProfilePragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void updateUI(User user) {
        switch (user.getTypeUse()){
            case TYPE_USER_CUSTOM:
                tvNumberPoint.setText(user.getNumberPoint());
                break;
            case TYPE_USER_EMPLOYEE:
                lnDegree.setVisibility(View.VISIBLE);
                tvNumberPoint.setText(user.getNumberPoint());
                updateUIForEmployee(user);
                break;
            case TYPE_USER_MANAGER:
                lnDegree.setVisibility(View.VISIBLE);
                tvPoint.setText(getText(R.string.custom_profile_fragment));
                tvNumberPoint.setText(String.valueOf(((UserManager)user).getNumberCustom()));
                lnManagerSales.setVisibility(View.VISIBLE);
                updateUIForEmployee(user);
                break;
        }
        tvNumberSales.setText(String.valueOf(user.getNumberSales()));
        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());
        tvAdress.setText(user.getAddress());
        GlideApp.with(this).load(R.drawable.bg_test).apply(RequestOptions.circleCropTransform()).into(imgAvar);
        GlideApp.with(this).load(R.drawable.bg_test).into(imgAvarUnder);
    }

    private void updateUIForEmployee(User user){
        tvTime.setText(String.valueOf("  "+DateTimeUntils.getStringDayMonthYear(((UserEmployee)user).getDateStart())+ " - " +
                DateTimeUntils.getStringDayMonthYear(Calendar.getInstance().getTime())));
        tvSalesHave.setText(String.valueOf("  "+((UserEmployee)user).getSaleHave()+"  VNĐ"));
        tvSalesWant.setText(String.valueOf("  "+((UserEmployee)user).getSaleWant()+"  VNĐ"));
        degreeSale.setValue(R.color.white,R.color.colorPrimaryDark,((UserEmployee)user).getSaleHave(),((UserEmployee)user).getSaleWant());
    }
}
