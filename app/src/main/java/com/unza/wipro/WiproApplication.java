package com.unza.wipro;

import android.app.Activity;

import com.paditech.core.BaseApplication;
import com.paditech.core.helper.FragmentHelper;
import com.unza.wipro.main.views.activities.MainActivity;

public class WiproApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        FragmentHelper.setRoot(R.id.container);
        AppState.getInstance().loadFromCache();
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (activity instanceof MainActivity) {
            AppState.getInstance().release();
        }
        super.onActivityDestroyed(activity);
    }
}
