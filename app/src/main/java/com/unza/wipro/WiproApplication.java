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
    public void onActivityPaused(Activity activity) {
        if (activity instanceof MainActivity) {
            AppConstans.app.saveToCache();
        }
        super.onActivityPaused(activity);
    }
}
