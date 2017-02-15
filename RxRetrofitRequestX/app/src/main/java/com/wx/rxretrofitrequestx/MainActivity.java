package com.wx.rxretrofitrequestx;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.wx.rxretrofitlibrary.application.RxApplication;
import com.wx.rxretrofitlibrary.dao.GreenDaoManager;
import com.wx.rxretrofitlibrary.exception.ApiException;
import com.wx.rxretrofitlibrary.listener.HttpRequestListener;
import com.wx.rxretrofitlibrary.manager.HttpRequestManager;
import com.wx.rxretrofitrequestx.activity.DownloadActivity;
import com.wx.rxretrofitrequestx.activity.RequestActivity;
import com.wx.rxretrofitrequestx.requestapi.NewsApi;
import com.wx.rxretrofitrequestx.requestapi.WeChatApi;

public class MainActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void request(View view) {
        Intent intent = new Intent(this, RequestActivity.class);
        startActivity(intent);
    }

    public void download(View view) {
        Intent intent = new Intent(this, DownloadActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxApplication.release();
    }

}
