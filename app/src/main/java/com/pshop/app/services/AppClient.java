package com.pshop.app.services;

import com.paditech.core.network.BaseClient;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppClient extends BaseClient<AppService> {
    private static final int CACHE_SIZE = 10 * 1024 * 1024;// 10 MiB
    private AppService mService;

    public static AppClient newInstance() {
        return new AppClient();
    }

    @Override
    protected Class<AppService> getServiceType() {
        return AppService.class;
    }

    @Override
    protected String getHostAddress() {
        return "http://admin.pshop.paditech.com/api/v1/";
    }

    public AppService getService() {
        if (mService == null) {
            synchronized (BaseClient.class) {
                //Setup base config
                NetworkConfigImpl config = NetworkConfig.newInstance();
                config
                        .setReadTimeOut(120, TimeUnit.SECONDS)
                        .setConnectTimeOut(120, TimeUnit.SECONDS)
                        .setWriteTimeOut(120, TimeUnit.SECONDS);

                //setup cache
//                File httpCacheDirectory = new File(BaseApplication.getAppContext().getCacheDir(), "responses");
//                Cache cache = new Cache(httpCacheDirectory, CACHE_SIZE);

                //add cache to the client
//                config.getBuilder().cache(cache);

                if (isEnableDebug()) {
                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                    config.getBuilder().addInterceptor(logging);
                }
                //add common Interceptor
                config.getBuilder().addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws java.io.IOException {
                        Request original = chain.request();

                        Request request = original.newBuilder()
                                .header("Content-Type", "application/json")
                                .method(original.method(), original.body())
                                .build();

                        return chain.proceed(request);
                    }
                });
                //Build
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(getHostAddress())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(config.build())
                        .build();

                //Create service
                mService = retrofit.create(getServiceType());
            }
        }
        return mService;
    }

    private boolean isEnableDebug() {
        return false;
    }
}
