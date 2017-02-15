package com.wx.rxretrofitrequestxx.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.wx.rxretrofitlibrary.exception.ApiException;
import com.wx.rxretrofitlibrary.listener.HttpRequestListener;
import com.wx.rxretrofitlibrary.listener.UploadProgressListener;
import com.wx.rxretrofitlibrary.manager.HttpRequestManager;
import com.wx.rxretrofitlibrary.responsebody.UploadRequestBody;
import com.wx.rxretrofitrequestxx.R;
import com.wx.rxretrofitrequestxx.requestapi.UploadApi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadActivity extends RxAppCompatActivity {

    private ImageView ivFile;
    private ImageView ivDownload;
    private InputStream inputStream;
    private HttpRequestManager requestManager;
    private UploadApi uploadApi;
    private NumberProgressBar mProgressBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        init();
    }

    private void init() {
        textView = (TextView) findViewById(R.id.tv_msg);
        ivFile = (ImageView) findViewById(R.id.iv_file);
        ivDownload =  (ImageView) findViewById(R.id.iv_download);
        mProgressBar = (NumberProgressBar) findViewById(R.id.progress_bar);

        try {
            inputStream = getAssets().open("m8.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        ivFile.setImageBitmap(bitmap);

        requestManager = new HttpRequestManager(mRequestListener,this);

        uploadApi = new UploadApi();
        File file = new File("/storage/sdcard/RxHttp/baidu.png");
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"),file);
        UploadRequestBody uploadRequestBody = new UploadRequestBody(requestBody,mListener);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file_name",file.getName(),uploadRequestBody);
        uploadApi.setPart(part);
    }

    public void upload(View view) {
        requestManager.requestData(uploadApi);
    }

    private UploadProgressListener mListener = new UploadProgressListener() {
        @Override
        public void onProgress(long upload, long count) {
            mProgressBar.setMax((int) count);
            mProgressBar.setProgress((int) upload);
        }
    };

    private HttpRequestListener mRequestListener = new HttpRequestListener() {
        @Override
        public void onStart() {
            textView.setText("开始上传");
        }

        @Override
        public void onComplete(String result) {
            textView.setText("上传结束");
            System.out.println(""+result);
        }

        @Override
        public void onError(ApiException error) {
            textView.setText("上传失败:"+error.getErrorMessage());
        }
    };

}
