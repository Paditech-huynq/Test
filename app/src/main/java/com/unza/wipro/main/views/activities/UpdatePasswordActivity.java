package com.unza.wipro.main.views.activities;

import com.paditech.core.BaseActivity;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.UpdatePasswordContract;

public class UpdatePasswordActivity extends BaseActivity implements UpdatePasswordContract.ViewImpl {
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_update_password;
    }

    @Override
    public String getScreenTitle() {
        return null;
    }
}
