package com.unza.wipro.services;

import com.paditech.core.network.BaseClient;

public class AppClient extends BaseClient<AppService> {
    public static AppClient newInstance() {
        return new AppClient();
    }

    @Override
    protected Class<AppService> getServiceType() {
        return AppService.class;
    }

    @Override
    protected String getHostAddress() {
        return "http://wipro.crm.admin.paditech.com/api/v1/";
    }
}
