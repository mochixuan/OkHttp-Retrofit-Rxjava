package com.wx.rxretrofitrequestxx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.wx.rxretrofitlibrary.application.RxApplication;
import com.wx.rxretrofitrequestxx.activity.DownloadActivity;
import com.wx.rxretrofitrequestxx.activity.RequestActivity;
import com.wx.rxretrofitrequestxx.activity.UploadActivity;

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

    public void upload(View view) {
        Intent intent = new Intent(this, UploadActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxApplication.release();
    }

}
