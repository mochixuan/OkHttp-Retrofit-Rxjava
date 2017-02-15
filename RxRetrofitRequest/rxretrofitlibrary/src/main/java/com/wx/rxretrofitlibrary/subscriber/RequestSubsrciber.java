package com.wx.rxretrofitlibrary.subscriber;

import android.content.Context;

import com.wx.rxretrofitlibrary.api.BaseApi;
import com.wx.rxretrofitlibrary.exception.ApiException;
import com.wx.rxretrofitlibrary.exception.ErrorCode;
import com.wx.rxretrofitlibrary.exception.FactoryException;
import com.wx.rxretrofitlibrary.exception.HttpRunException;
import com.wx.rxretrofitlibrary.listener.HttpRequestListener;

import java.lang.ref.SoftReference;

import rx.Subscriber;

public class RequestSubsrciber<T> extends Subscriber<T>{

    private SoftReference<HttpRequestListener> mRequestListener;
    private SoftReference<Context> mActivity;
    private BaseApi mBaseApi;

    public RequestSubsrciber(BaseApi baseApi,SoftReference<HttpRequestListener> requestListener,SoftReference<Context> activity) {
        this.mBaseApi = baseApi;
        this.mRequestListener = requestListener;
        this.mActivity = activity;
    }

    @Override
    public void onStart() {
        super.onStart();
        mRequestListener.get().onStart();
    }

    @Override
    public void onCompleted() {
        unSubscribe();
    }

    @Override
    public void onError(Throwable e) {
        HttpRequestListener requestListener = mRequestListener.get();
        Context activity = mActivity.get();
        if (activity == null || requestListener == null) return;
        ApiException apiException = FactoryException.analyExcetpion(e);
        requestListener.onError(apiException);
        unSubscribe();
    }

    @Override
    public void onNext(T t) {
        //服务器返回数据为错误或者我们参数错，可以在这里过滤errorcode
        if (t==null) {
            HttpRunException exception = new HttpRunException(ErrorCode.DATANULL_ERROR_CODE);
            onError(exception);
            return;
        }
        mRequestListener.get().onComplete((String) t);
    }

    private void unSubscribe() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

}
