package com.wx.rxretrofitrequestxx.application;

import android.app.Application;

import com.wx.rxretrofitlibrary.application.RxApplication;

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        RxApplication.init(this);
    }

}
