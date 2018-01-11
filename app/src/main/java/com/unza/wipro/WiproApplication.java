package com.unza.wipro;

import com.paditech.core.BaseApplication;
import com.paditech.core.helper.FragmentHelper;

public class WiproApplication extends BaseApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        FragmentHelper.setRoot(R.id.container);
    }
}
