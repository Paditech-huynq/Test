package com.pshop.app.services;

import com.google.common.base.Preconditions;
import com.paditech.core.network.AddCookiesInterceptor;
import com.paditech.core.network.ReceivedCookiesInterceptor;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

class NetworkConfig implements NetworkConfigImpl {
    private OkHttpClient.Builder mBuilder;

    private NetworkConfig() {
        mBuilder = new OkHttpClient.Builder();
        mBuilder.interceptors().add(new AddCookiesInterceptor());
        mBuilder.interceptors().add(new ReceivedCookiesInterceptor());
    }

    static NetworkConfigImpl newInstance() {
        return new NetworkConfig();
    }

    @Override
    public OkHttpClient.Builder getBuilder() {
        return mBuilder;
    }

    @Override
    public NetworkConfigImpl setupProxy(String proxyHost, int proxyPort) {
        Preconditions.checkNotNull(proxyHost, "proxy host cannot be null");
        java.net.Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        mBuilder.proxy(proxy);
        return this;
    }

    @Override
    public NetworkConfigImpl setReadTimeOut(long timeOut, TimeUnit unit) {
        mBuilder.readTimeout(timeOut, TimeUnit.SECONDS);
        return this;
    }

    @Override
    public NetworkConfigImpl setConnectTimeOut(long timeOut, TimeUnit unit) {
        mBuilder.connectTimeout(timeOut, TimeUnit.SECONDS);
        return this;
    }

    @Override
    public NetworkConfigImpl setWriteTimeOut(long timeOut, TimeUnit unit) {
        mBuilder.writeTimeout(timeOut, TimeUnit.SECONDS);
        return this;
    }

    @Override
    public OkHttpClient build() {
        return mBuilder.build();
    }
}