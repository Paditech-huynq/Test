package com.unza.wipro;

import android.app.Activity;
import android.os.Bundle;

import com.paditech.core.BaseApplication;
import com.paditech.core.helper.FragmentHelper;
import com.unza.wipro.main.views.activities.MainActivity;

public class WiproApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        FragmentHelper.setRoot(R.id.container);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (activity instanceof MainActivity) {
            AppConstans.app.loadFromCache();
        }
        super.onActivityCreated(activity, savedInstanceState);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        AppConstans.app.saveToCache();
//        if (activity instanceof MainActivity) {
//            if (!AppConstans.app.isLogoutPending()) {
//                AppConstans.app.release();
//            }
//        }
        super.onActivityDestroyed(activity);
    }
}
