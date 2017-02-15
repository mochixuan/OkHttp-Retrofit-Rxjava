package com.wx.rxretrofitrequestx.activity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.wx.rxretrofitlibrary.exception.ApiException;
import com.wx.rxretrofitlibrary.listener.HttpRequestListener;
import com.wx.rxretrofitlibrary.manager.HttpRequestManager;
import com.wx.rxretrofitrequestx.R;
import com.wx.rxretrofitrequestx.requestapi.NewsApi;
import com.wx.rxretrofitrequestx.requestapi.WeChatApi;

public class RequestActivity extends RxAppCompatActivity {

    private TextView mTvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        mTvData = (TextView) findViewById(R.id.tv_data);
        mTvData.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    public void request(View view){
        HttpRequestManager requestManager = new HttpRequestManager(mRequestListener,this);
        NewsApi weChatApi = new NewsApi("http://v.juhe.cn/toutiao/index?type=keji&key=418462c99158a07722671d0e54be7a1c");
        requestManager.requestData(weChatApi);
    }

    public void request1(View view){
        HttpRequestManager requestManager = new HttpRequestManager(mRequestListener,this);
        WeChatApi weChatApi = new WeChatApi("US","json","936b288809d0089e2c368568851e7f2e");
        requestManager.requestData(weChatApi);
    }

    private HttpRequestListener mRequestListener = new HttpRequestListener() {
        @Override
        public void onStart() {
            mTvData.setText("开始");
        }

        @Override
        public void onComplete(String result) {
            mTvData.setText("成功\n"+result);
        }

        @Override
        public void onError(ApiException error) {
            mTvData.setText("错误类型: \n"+error.getErrorMessage());
        }
    };

}
