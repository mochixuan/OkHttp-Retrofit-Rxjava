package com.wx.rxretrofitlibrary.manager;

import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.wx.rxretrofitlibrary.api.BaseApi;
import com.wx.rxretrofitlibrary.exception.FactoryException;
import com.wx.rxretrofitlibrary.exception.RetryWhenNetworkException;
import com.wx.rxretrofitlibrary.listener.HttpRequestListener;
import com.wx.rxretrofitlibrary.subscriber.RequestSubsrciber;
import com.wx.rxretrofitlibrary.utils.LibraryUtil;

import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class HttpRequestManager {

    private SoftReference<HttpRequestListener> mRequestListener;
    private SoftReference<RxAppCompatActivity> mAppCompatActivity;

    public HttpRequestManager(HttpRequestListener requestListener,RxAppCompatActivity appCompatActivity) {
        this.mRequestListener = new SoftReference<HttpRequestListener>(requestListener);
        this.mAppCompatActivity = new SoftReference<RxAppCompatActivity>(appCompatActivity);
    }

    public void requestData(BaseApi baseApi) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(baseApi.getConnectionTime(), TimeUnit.SECONDS)
                .readTimeout(baseApi.getReadTime(),TimeUnit.SECONDS)
                .writeTimeout(baseApi.getWriteTime(),TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseApi.getBaseUrl())
                .build();

        RequestSubsrciber subsrciber = new RequestSubsrciber(baseApi,mRequestListener,mAppCompatActivity);

        baseApi.getObservable(retrofit)
                .retryWhen(new RetryWhenNetworkException())
                .compose(mAppCompatActivity.get().bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subsrciber);
    }

}
