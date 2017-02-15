package com.wx.rxretrofitlibrary.listener;

public abstract class HttpProgressListener<T> {

    public abstract void onStart();

    public abstract void onComplete();

    public abstract void onProgress(long readLength,long countLength);

    public void onError(Throwable throwable){}

    public void onPause(){}

    public void onRemove(){}

}
