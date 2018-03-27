package com.pshop.app.services;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Should not modify here
 */
public interface NetworkConfigImpl {
    NetworkConfigImpl setupProxy(String proxyHost, int proxyPort);

    NetworkConfigImpl setReadTimeOut(long timeOut, TimeUnit unit);

    NetworkConfigImpl setConnectTimeOut(long timeOut, TimeUnit unit);

    NetworkConfigImpl setWriteTimeOut(long timeOut, TimeUnit unit);

    OkHttpClient.Builder getBuilder();

    OkHttpClient build();
}
