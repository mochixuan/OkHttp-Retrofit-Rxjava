package com.wx.rxretrofitlibrary.application;

import android.app.Application;
import android.content.Context;

import com.wx.rxretrofitlibrary.dao.GreenDaoManager;

public class RxApplication {

    private static Context context;

    public static void init(Application application) {
        context = application.getApplicationContext();
    }

    public static Context getApplicationContext() {
        return context;
    }

    public static void release() {
        GreenDaoManager.getInstance().close();
    }

}
