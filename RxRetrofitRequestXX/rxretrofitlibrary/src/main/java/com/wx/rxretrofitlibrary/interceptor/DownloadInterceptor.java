package com.wx.rxretrofitlibrary.interceptor;

import com.wx.rxretrofitlibrary.listener.HttpDownloadListener;
import com.wx.rxretrofitlibrary.responsebody.DownloadResponseBody;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class DownloadInterceptor implements Interceptor{

    public HttpDownloadListener listener;

    public DownloadInterceptor(HttpDownloadListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder()
                .body(new DownloadResponseBody(response.body(),listener))
                .build();
    }

}
