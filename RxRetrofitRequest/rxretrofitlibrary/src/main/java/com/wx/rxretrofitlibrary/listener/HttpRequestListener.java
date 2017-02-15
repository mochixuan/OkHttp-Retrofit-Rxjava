package com.wx.rxretrofitlibrary.listener;

import com.wx.rxretrofitlibrary.exception.ApiException;

public interface HttpRequestListener{

    void onStart();

    void onComplete(String result);

    void onError(ApiException error);

}
